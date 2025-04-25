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
    private int overhealthChange = 0; // the fact i have to put this here pisses me off to no end

    public OverhealthManager(NMLAttributes plugin) {
        this.plugin = plugin;
        profileManager = plugin.getProfileManager();
    }

//    public void startOverhealthTracker() {
//        checker = new BukkitRunnable() {
//            @Override
//            public void run() {
//                long now = System.currentTimeMillis();
//                for (Player player : Bukkit.getOnlinePlayers()) {
//                    UUID uuid = player.getUniqueId();
//                    long last = overhealthRegenMap.getOrDefault(uuid, 0L);
//                    if (now - last >= 3000 && last != -1) {
//                        startOverhealthRegen(player);
//                        overhealthRegenMap.put(uuid, -1L);
//                    }
//                }
//            }
//        };
//        checker.runTaskTimer(plugin, 0L, 20L); // every second
//    }

    public void startOverhealthRegen(Player player) {
        Attributes attributes = profileManager.getPlayerProfile(player.getUniqueId()).getAttributes();
        int currentOverhealth = attributes.getCurrentOverhealth();
        int maxOverhealth = attributes.getMaxOverhealth();

        overhealthRegen = new BukkitRunnable() { // runnable method that regenerates overhealth every second up to the max
            @Override
            public void run() {
                if (currentOverhealth < maxOverhealth) {
                    overhealthChange = maxOverhealth / 15;

                    attributes.setCurrentOverhealth(currentOverhealth + overhealthChange);
                    profileManager.getPlayerProfile(player.getUniqueId()).setAttributes(attributes);
                    profileManager.updateStatsFromProfile(player);
                } else {
                    this.cancel();
                    player.sendMessage("You have reached your max overhealth!");
                    attributes.setCurrentOverhealth(maxOverhealth);
                }

                setOverhealthAbsorption(player);
            }
        };
        overhealthRegen.runTaskTimer(plugin, 0L, 20L); // every second
    }

//    public void add2OverhealthMap(Player player) {
//        overhealthRegenMap.put(player.getUniqueId(), System.currentTimeMillis());
//    }

    public void setOverhealthAbsorption(Player player) {
        Attributes attributes = profileManager.getPlayerProfile(player.getUniqueId()).getAttributes();
        int currentOverhealth = attributes.getCurrentOverhealth();

        player.setAbsorptionAmount(currentOverhealth);
    }
}