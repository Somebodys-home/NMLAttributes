package io.github.Gabriel.NMLSkills.commands;

import io.github.Gabriel.NMLSkills.NMLAttributes;
import io.github.Gabriel.NMLSkills.levelSystem.LevelManager;
import io.github.Gabriel.NMLSkills.player.attributeSystem.Attributes;
import io.github.Gabriel.NMLSkills.player.profileSystem.Profile;
import io.github.Gabriel.NMLSkills.player.profileSystem.ProfileManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetExpCommand implements CommandExecutor {
    private ProfileManager profileManager;
    private LevelManager levelManager;

    public SetExpCommand(NMLAttributes nmlAttributes) {
        profileManager = nmlAttributes.getProfileManager();
        levelManager = nmlAttributes.getLevelManager();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            Attributes attributes = profileManager.getPlayerProfile(player.getUniqueId()).getAttributes();
            Profile profile = new Profile(attributes);
            int mod = Integer.parseInt(args[0]);

            attributes.setExp(mod);
            levelManager.updateLevelBar(player);
            player.sendMessage("Exp set to " + mod);
            profile.setAttributes(attributes);
            profileManager.saveAProfileToConfig(player);
            profileManager.updateStatsFromProfile(player);
        }

        return true;
    }
}
