package io.github.Gabriel.NMLSkills.commands;

import io.github.Gabriel.NMLSkills.NMLAttributes;
import io.github.Gabriel.NMLSkills.menus.AttributesMenu;
import io.github.Gabriel.NMLSkills.menus.MenuSystem.PlayerMenuUtility;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class AttributesCommand implements CommandExecutor {
    private NMLAttributes nmlAttributes;

    public AttributesCommand(NMLAttributes nmlAttributes) {
        this.nmlAttributes = nmlAttributes.getInstance();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            new AttributesMenu(NMLAttributes.getPlayerMenuUtility(player), nmlAttributes).open();
        }
        return true;
    }
}
