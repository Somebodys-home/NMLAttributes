package io.github.Gabriel.NMLSkills.overhealthSystem;

import io.github.Gabriel.NMLSkills.NMLAttributes;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class OverhealthListener implements Listener {
    private final NMLAttributes plugin;

    public OverhealthListener(NMLAttributes plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            plugin.getOverhealthManager().add2OverhealthMap(player);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        plugin.getOverhealthManager().add2OverhealthMap(player);
    }
}
