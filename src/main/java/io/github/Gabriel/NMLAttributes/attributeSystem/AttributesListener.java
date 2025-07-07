package io.github.Gabriel.NMLAttributes.attributeSystem;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class AttributesListener implements Listener {
    @EventHandler
    public void vitality(PlayerJoinEvent event) {}

    // todo: only have this damage apply to certain types of melee weapons
    @EventHandler
    public void strength(EntityDamageByEntityEvent event) {}
}
