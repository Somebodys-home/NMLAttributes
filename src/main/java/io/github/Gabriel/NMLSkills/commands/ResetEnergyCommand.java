package io.github.Gabriel.NMLSkills.commands;

import io.github.Gabriel.NMLSkills.NMLAttributes;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ResetEnergyCommand implements CommandExecutor {
    private NMLAttributes nmlAttributes;

    public ResetEnergyCommand(NMLAttributes nmlAttributes) {
        this.nmlAttributes = nmlAttributes;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            player.setFoodLevel(0);
            nmlAttributes.getProfileManager().getPlayerProfile(player.getUniqueId()).getAttributes().setCurrentEnergy(0);
        }
        return true;
    }
}
