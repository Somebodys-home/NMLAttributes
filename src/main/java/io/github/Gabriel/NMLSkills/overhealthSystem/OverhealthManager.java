package io.github.Gabriel.NMLSkills.overhealthSystem;

import io.github.Gabriel.NMLSkills.NMLAttributes;
import io.github.Gabriel.NMLSkills.attributeSystem.Attributes;
import io.github.Gabriel.NMLSkills.profileSystem.ProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OverhealthManager {
    private final NMLAttributes nmlAttributes;
    private final ProfileManager profileManager;
    private final Map<UUID, Long> overhealthRegenMap = new HashMap<>();

    public OverhealthManager(NMLAttributes nmlAttributes) {
        this.nmlAttributes = nmlAttributes;
        profileManager = nmlAttributes.getProfileManager();
    }

    public void add2OverhealthMap(Player player) {
        overhealthRegenMap.put(player.getUniqueId(), System.currentTimeMillis());
    }

    public void startOverhealthTracker() {
        new BukkitRunnable() {
            @Override
            public void run() {
                long currentTime = System.currentTimeMillis();

                for (Player player : Bukkit.getOnlinePlayers()) {
                    UUID uuid = player.getUniqueId();
                    long last = overhealthRegenMap.getOrDefault(uuid, 0L);

                    if (currentTime - last >= 3000) {
                        overhealthRegen(player);
                        overhealthRegenMap.put(uuid, currentTime); // update timestamp
                    }

                }
            }
        }.runTaskTimer(nmlAttributes, 0L, 20L); // 1 second
    }

    public void overhealthRegen(Player player) {
        Attributes attributes = profileManager.getPlayerProfile(player.getUniqueId()).getAttributes();
        double maxOverhealth = attributes.getMaxOverhealth();

        new BukkitRunnable() {
            double currentOverhealth = attributes.getCurrentOverhealth();

            @Override
            public void run() {
                if (currentOverhealth >= maxOverhealth) {
                    this.cancel();
                    return;
                }

                OverhealthChangeEvent overhealthChangeEvent = new OverhealthChangeEvent(player, currentOverhealth, currentOverhealth + (maxOverhealth / 15));
                Bukkit.getPluginManager().callEvent(overhealthChangeEvent);

                currentOverhealth = overhealthChangeEvent.getNewOverhealth();
            }
        }.runTaskTimer(nmlAttributes, 0L, 20L); // 20 ticks = 1 second
    }
}