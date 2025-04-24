package io.github.Gabriel.NMLSkills.overhealthSystem;

import io.github.Gabriel.NMLSkills.NMLAttributes;
import io.github.Gabriel.NMLSkills.attributeSystem.Attributes;
import io.github.Gabriel.NMLSkills.profileSystem.ProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OverhealthManager {
    private final NMLAttributes plugin;
    private final ProfileManager profileManager;
    private final Map<UUID, Long> overhealthRegenMap = new HashMap<>();
    private BukkitRunnable checker;
    private BukkitRunnable overhealthRegen;
    private boolean needsoverhealthRegen = false;

    public OverhealthManager(NMLAttributes plugin) {
        this.plugin = plugin;
        profileManager = plugin.getProfileManager();
    }

    public void startOverhealthTracker() { // idk how this works and im too scared to tamper it
        checker = new BukkitRunnable() {
            @Override
            public void run() {
                long now = System.currentTimeMillis();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    UUID uuid = player.getUniqueId();
                    long last = overhealthRegenMap.getOrDefault(uuid, 0L);
                    if (now - last >= 3000 && last != -1) {
                        startOverhealthRegen(player);
                        overhealthRegenMap.put(uuid, -1L);
                    }
                }
            }
        };
        checker.runTaskTimer(plugin, 0L, 20L); // every second
    }

    public void stopOverhealthTracker() {
        if (checker != null) {
            checker.cancel();
        }
    }

    public void startOverhealthRegen(Player player) {
        Attributes attributes = profileManager.getPlayerProfile(player.getUniqueId()).getAttributes();
        final double[] currentOverhealth = {attributes.getCurrentOverhealth()};
        double maxOverhealth = attributes.getMaxOverhealth();

        if (needsoverhealthRegen) {
            overhealthRegen = new BukkitRunnable() {
                @Override
                public void run() {
                    if (currentOverhealth[0] >= maxOverhealth) { // regens your overhealth over time until it reaches max
                        currentOverhealth[0] += maxOverhealth / 15; // <- how long in seconds it takes to regen to full overhealth

                        attributes.setCurrentOverhealth(currentOverhealth[0]);
                        profileManager.getPlayerProfile(player.getUniqueId()).setAttributes(attributes);
                        profileManager.saveAProfileToConfig(player);
                        profileManager.updateStatsFromProfile(player);
                    } else {
                        needsoverhealthRegen = false;
                        overhealthRegenMap.remove(player.getUniqueId());
                        overhealthRegen.cancel();
                    }
                }
            };
            checker.runTaskTimer(plugin, 0L, 20L); // every second
        }
    }

    public void add2OverhealthMap(Player player) {
        overhealthRegenMap.put(player.getUniqueId(), System.currentTimeMillis());
    }
}