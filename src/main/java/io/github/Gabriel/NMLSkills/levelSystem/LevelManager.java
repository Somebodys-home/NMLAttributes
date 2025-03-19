package io.github.Gabriel.NMLSkills.levelSystem;

import io.github.Gabriel.NMLSkills.NMLAttributes;
import io.github.Gabriel.NMLSkills.player.profileSystem.ProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class LevelManager {
    private NMLAttributes nmlAttributes;
    private ProfileManager profileManager;

    public LevelManager(NMLAttributes nmlAttributes) {
        this.nmlAttributes = nmlAttributes;
        profileManager = nmlAttributes.getProfileManager();
    }

    public void addExp(Player player, int amount) {
        profileManager.getPlayerProfile(player.getUniqueId()).getAttributes().setExp(profileManager.getPlayerProfile(player.getUniqueId()).getAttributes().getExp() + amount);
        updateLevelBar(player);
    }

    public void updateLevelBar(Player player) {
        int level = profileManager.getPlayerProfile(player.getUniqueId()).getAttributes().getLevel();
        int exp = profileManager.getPlayerProfile(player.getUniqueId()).getAttributes().getExp();
        double expercent = (double) exp / 100;

        player.setLevel(level);
        player.setExp((float) expercent);
    }

    public void updateLevelBarTask() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            updateLevelBar(player);
        }
    }
}
