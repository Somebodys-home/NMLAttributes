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
    private BukkitRunnable checker;
    private BukkitRunnable overhealthRegen;
    private double overhealthChange = 0; // the fact i have to put this here pisses me off to no end

    public OverhealthManager(NMLAttributes nmlAttributes) {
        this.nmlAttributes = nmlAttributes;
        profileManager = nmlAttributes.getProfileManager();
    }

    public void startOverhealthTracker() {
        new BukkitRunnable() {
            @Override
            public void run() {
                long currentTime = System.currentTimeMillis();

                for (Player player : Bukkit.getOnlinePlayers()) {
                    UUID uuid = player.getUniqueId();
                    long last = overhealthRegenMap.getOrDefault(uuid, 0L);

                    if (currentTime - last >= 3000) { // 3 seconds
                        player.getAttribute(Attribute.GENERIC_MAX_ABSORPTION).setBaseValue(10);
                        player.setAbsorptionAmount(player.getAbsorptionAmount() + 2);
                        player.sendMessage("test");
                    }
                }
            }
        }.runTaskTimer(nmlAttributes, 0L, 20L); // 1 second
    }

//    public void startOverhealthRegen(Player player) {
//        Attributes attributes = profileManager.getPlayerProfile(player.getUniqueId()).getAttributes();
//        double currentOverhealth = attributes.getCurrentOverhealth();
//        double maxOverhealth = attributes.getMaxOverhealth();
//
//        overhealthRegen = new BukkitRunnable() { // runnable method that regenerates overhealth every second up to the max
//            @Override
//            public void run() {
//                if (currentOverhealth < maxOverhealth) {
//                    overhealthChange = maxOverhealth / 15;
//
//                    attributes.setCurrentOverhealth(currentOverhealth + overhealthChange);
//                    profileManager.getPlayerProfile(player.getUniqueId()).setAttributes(attributes);
//                    profileManager.updateStatsFromProfile(player);
//                } else {
//                    this.cancel();
//                    player.sendMessage("You have reached your max overhealth!");
//                    attributes.setCurrentOverhealth(maxOverhealth);
//                }
//
//                setOverhealthAbsorption(player);
//            }
//        };
//        overhealthRegen.runTaskTimer(nmlAttributes, 0L, 20L); // every second
//    }

    public void add2OverhealthMap(Player player) {
        overhealthRegenMap.put(player.getUniqueId(), System.currentTimeMillis());
    }

    public void setOverhealthAbsorption(Player player) {
        Attributes attributes = profileManager.getPlayerProfile(player.getUniqueId()).getAttributes();
        double currentOverhealth = attributes.getCurrentOverhealth();

        player.setAbsorptionAmount(currentOverhealth);
    }
}