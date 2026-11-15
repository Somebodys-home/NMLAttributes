package io.github.NoOne.NMLAttributes;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AttributesCommand implements CommandExecutor {
    private NMLAttributes nmlAttributes;

    public AttributesCommand(NMLAttributes nmlAttributes) {
        this.nmlAttributes = nmlAttributes;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player player) {
            new AttributesMenu(player, nmlAttributes).open();
        }

        return true;
    }
}
