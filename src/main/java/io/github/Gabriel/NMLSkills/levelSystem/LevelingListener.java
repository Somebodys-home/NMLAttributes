package io.github.Gabriel.NMLSkills.levelSystem;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

public class LevelingListener implements Listener {
    private static final int XP_REQUIRED_PER_LEVEL = 100;

    @EventHandler
    public void onEXPGain(PlayerExpChangeEvent event) {
        Player player = event.getPlayer();

        // Get the player's current total XP
        int currentTotalXP = getTotalXP(player);

        // Calculate the player's current level based on the fixed XP requirement
        int currentLevel = currentTotalXP / XP_REQUIRED_PER_LEVEL;

        // Calculate the remaining XP needed for the next level
        int remainingXP = currentTotalXP % XP_REQUIRED_PER_LEVEL;

        // Update the player's level and experience bar
        player.setLevel(currentLevel);
        player.setExp((float) remainingXP / XP_REQUIRED_PER_LEVEL);

        // Optional: Send a message to the player
        player.sendMessage(ChatColor.GREEN + "You need " + (XP_REQUIRED_PER_LEVEL - remainingXP) + " more XP to level up!");
    }

    // Helper method to calculate the player's total XP
    private int getTotalXP(Player player) {
        int level = player.getLevel();
        float progress = player.getExp();
        return (level * XP_REQUIRED_PER_LEVEL) + (int) (progress * XP_REQUIRED_PER_LEVEL);
    }
}
