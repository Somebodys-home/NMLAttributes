package io.github.Gabriel.NMLSkills.overhealthSystem;

import io.github.Gabriel.NMLSkills.NMLAttributes;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;

public class StartOverhealthRegenCommand implements CommandExecutor {
    private OverhealthManager overhealthManager;

    public StartOverhealthRegenCommand(NMLAttributes nmlAttributes) {
        overhealthManager = nmlAttributes.getOverhealthManager();
    }
    @Override
    public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command command, String s, String[] strings) {
        if (sender instanceof Player player) {
            overhealthManager.startOverhealthRegen(player);
        }

        return true;
    }
}
