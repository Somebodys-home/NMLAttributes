package io.github.Gabriel.NMLAttributes.levelSystem;

import io.github.Gabriel.NMLAttributes.NMLAttributes;
import io.github.Gabriel.menuSystem.MenuSystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LevelUpCommand implements CommandExecutor {
    private NMLAttributes nmlAttributes;

    public LevelUpCommand(NMLAttributes nmlAttributes) {
        this.nmlAttributes = nmlAttributes.getInstance();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            new LevelUpMenu(MenuSystem.getPlayerMenuUtility(player), nmlAttributes).open();
        }
        return true;
    }
}
