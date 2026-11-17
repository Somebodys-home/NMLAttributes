package io.github.NoOne.NMLevelUp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LevelUpCommand implements CommandExecutor {
    private NMLevelUp nmLevelUp;

    public LevelUpCommand(NMLevelUp nmLevelUp) {
        this.nmLevelUp = nmLevelUp;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player player) {
            new LevelUpMenu(player, nmLevelUp).open();
        }

        return true;
    }
}
