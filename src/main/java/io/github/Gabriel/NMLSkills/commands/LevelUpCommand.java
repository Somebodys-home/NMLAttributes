package io.github.Gabriel.NMLSkills.commands;

import io.github.Gabriel.NMLSkills.NMLAttributes;
import io.github.Gabriel.NMLSkills.menus.AttributesMenu;
import io.github.Gabriel.NMLSkills.menus.LevelUpMenu;
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
            new LevelUpMenu(NMLAttributes.getPlayerMenuUtility(player), nmlAttributes).open();
        }
        return true;
    }
}
