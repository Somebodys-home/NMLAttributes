package io.github.Gabriel.NMLSkills.levelSystem;

import io.github.Gabriel.NMLSkills.NMLAttributes;
import io.github.Gabriel.NMLSkills.player.profileSystem.ProfileManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;

public class LevelingListener implements Listener {
    private ProfileManager profileManager;
    private int xpForLevelUp;

    public LevelingListener(NMLAttributes nmlAttributes) {
        profileManager = nmlAttributes.getProfileManager();
    }

    @EventHandler
    public void onEXPChange(PlayerExpChangeEvent event) {
        Player player = event.getPlayer();
        xpForLevelUp = profileManager.getPlayerProfile(player.getUniqueId()).getAttributes().getExp2NextLevel();
        int currentTotalXP = getTotalXP(player);
        int currentLevel = currentTotalXP / xpForLevelUp;
        int remainingXP = currentTotalXP % xpForLevelUp;

        player.setLevel(currentLevel);
        player.setExp((float) remainingXP / xpForLevelUp);
    }

    @EventHandler
    public void onLevelChange(PlayerLevelChangeEvent event) {
        if (event.getNewLevel() != 0) {
            profileManager.getPlayerProfile(event.getPlayer().getUniqueId()).getAttributes().setLevel(event.getNewLevel());
        } else {
            profileManager.getPlayerProfile(event.getPlayer().getUniqueId()).getAttributes().setLevel(1);
        }
    }

    private int getTotalXP(Player player) {
        int level = player.getLevel();
        float progress = player.getExp();
        return (level * xpForLevelUp) + (int) (progress * xpForLevelUp);
    }
}
