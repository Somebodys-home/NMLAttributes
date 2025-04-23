package io.github.Gabriel.NMLSkills.overhealthSystem;

import io.github.Gabriel.NMLSkills.NMLAttributes;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OverhealthManager {
    private final NMLAttributes plugin;
    private final Map<UUID, Long> overhealthMap = new HashMap<>();
    private BukkitRunnable checker;

    public OverhealthManager(NMLAttributes plugin) {
        this.plugin = plugin;
    }

    public void startOverhealthTracker() {
        checker = new BukkitRunnable() {
            @Override
            public void run() {
                long now = System.currentTimeMillis();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    UUID uuid = player.getUniqueId();
                    long last = overhealthMap.getOrDefault(uuid, 0L);
                    if (now - last >= 3000 && last != -1) {
                        player.sendMessage("§aGood job!");
                        overhealthMap.put(uuid, -1L); // prevent repeat messages
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

    public void add2OverhealthMap(Player player) {
        overhealthMap.put(player.getUniqueId(), System.currentTimeMillis());
    }
}