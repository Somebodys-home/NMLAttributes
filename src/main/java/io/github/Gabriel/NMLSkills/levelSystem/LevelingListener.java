package io.github.Gabriel.NMLSkills.levelSystem;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

public class LevelingListener implements Listener {
    @EventHandler
    public void onEXPGain(PlayerExpChangeEvent event) {
        event.setAmount(0);
    }
}
