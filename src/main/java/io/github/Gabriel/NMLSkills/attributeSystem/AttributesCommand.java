package io.github.Gabriel.NMLSkills.attributeSystem;

import io.github.Gabriel.NMLSkills.NMLAttributes;
import io.github.Gabriel.menuSystem.MenuSystem;
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
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player player) {
            new AttributesMenu(MenuSystem.getPlayerMenuUtility(player), nmlAttributes).open();
        }

        return true;
    }
}
