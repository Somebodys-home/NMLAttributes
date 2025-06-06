package io.github.Gabriel.NMLAttributes.profileSystem;

import io.github.Gabriel.NMLAttributes.NMLAttributes;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ResetProfileCommand implements CommandExecutor {
    private ProfileManager profileManager;

    public ResetProfileCommand(NMLAttributes nmlAttributes) {
        profileManager = nmlAttributes.getProfileManager();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            profileManager.createnewProfile(player);
            profileManager.saveAProfileToConfig(player);
            profileManager.updateStatsFromProfile(player);
        }

        return true;
    }
}
