package io.github.Gabriel.NMLSkills.levelSystem;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetLevelCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            int mod = Integer.parseInt(args[0]);

            player.setLevel(mod);
            player.sendMessage("Level set to " + mod);
        }

        return true;
    }
}
