package io.github.Gabriel.NMLSkills.player.attributeSystem;

import io.github.Gabriel.NMLSkills.NMLAttributes;
import io.github.Gabriel.NMLSkills.player.profileSystem.ProfileManager;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class AttributesListener implements Listener {
    private ProfileManager profileManager;
    private int vitalityBonus;
    private double strengthBonus;

    public AttributesListener(NMLAttributes nmlAttributes) {
        profileManager = nmlAttributes.getProfileManager();
    }

    @EventHandler
    public void vitality(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        vitalityBonus = profileManager.getPlayerProfile(player.getUniqueId()).getAttributes().getVitalityBonus();
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20D + vitalityBonus);
    }

    @EventHandler
    public void strength(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) {
            strengthBonus = profileManager.getPlayerProfile(player.getUniqueId()).getAttributes().getStrengthBonus();
            event.setDamage(event.getDamage() * (1 + (strengthBonus / 100))); // damage bonus
        }
    }
}
