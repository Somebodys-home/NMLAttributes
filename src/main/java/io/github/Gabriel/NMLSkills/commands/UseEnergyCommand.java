package io.github.Gabriel.NMLSkills.commands;

import io.github.Gabriel.NMLSkills.NMLAttributes;
import io.github.Gabriel.NMLSkills.energySystem.EnergyManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class UseEnergyCommand implements CommandExecutor {
    private NMLAttributes nmlAttributes;
    private EnergyManager energyManager;

    public UseEnergyCommand(NMLAttributes nmlAttributes) {
        this.nmlAttributes = nmlAttributes;
        energyManager =  new EnergyManager(this.nmlAttributes);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (Integer.parseInt(args[0]) > nmlAttributes.getProfileManager().getPlayerProfile(player.getUniqueId()).getAttributes().getMaxEnergy()) {
                player.sendMessage("ERROR: COST GREATER THAN MAXIMUM ENERGY");
            } else {
                energyManager.useEnergy(player, Integer.parseInt(args[0]));
            }
        }

        return true;
    }
}
