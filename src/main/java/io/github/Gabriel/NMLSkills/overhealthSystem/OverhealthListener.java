package io.github.Gabriel.NMLSkills.overhealthSystem;

import io.github.Gabriel.NMLSkills.NMLAttributes;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class OverhealthListener implements Listener {
    private final NMLAttributes plugin;
    private final OverhealthManager overhealthManager;

    public OverhealthListener(NMLAttributes plugin) {
        this.plugin = plugin;
        overhealthManager = this.plugin.getOverhealthManager();
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            double absorptionBefore = player.getAbsorptionAmount();

            overhealthManager.add2OverhealthMap(player);

            Bukkit.getScheduler().runTask(plugin, () -> {
                double absorptionAfter = player.getAbsorptionAmount();
                OverhealthChangeEvent overhealthChangeEvent = new OverhealthChangeEvent(player, absorptionBefore, absorptionAfter);

                Bukkit.getPluginManager().callEvent(overhealthChangeEvent);
            });
        }
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        overhealthManager.add2OverhealthMap(player);
    }

    // the actual overhealth listeners are in AttributesListener.java
}
