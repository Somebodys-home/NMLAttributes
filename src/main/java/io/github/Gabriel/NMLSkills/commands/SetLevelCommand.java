package io.github.Gabriel.NMLSkills.commands;

import io.github.Gabriel.NMLSkills.NMLAttributes;
import io.github.Gabriel.NMLSkills.player.Attributes;
import io.github.Gabriel.NMLSkills.player.Profile;
import io.github.Gabriel.NMLSkills.player.ProfileManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetLevelCommand implements CommandExecutor {
    private ProfileManager profileManager;

    public SetLevelCommand(NMLAttributes nmlAttributes) {
        profileManager = nmlAttributes.getProfileManager();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            Attributes attributes = profileManager.getPlayerProfile(player.getUniqueId()).getAttributes();
            Profile profile = new Profile(attributes);
            int mod = Integer.parseInt(args[0]);

            attributes.setLevel(mod);
            player.sendMessage("Level set to " + mod);
            profile.setAttributes(attributes);
            profileManager.saveAProfileToConfig(player);
            profileManager.updateStatsFromProfile(player);
        }
        return true;
    }
}
