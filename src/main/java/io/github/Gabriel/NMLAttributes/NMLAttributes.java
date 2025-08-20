package io.github.Gabriel.NMLAttributes;

import io.github.NoOne.menuSystem.MenuListener;
import io.github.NoOne.nMLPlayerStats.NMLPlayerStats;
import io.github.NoOne.nMLPlayerStats.profileSystem.ProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class NMLAttributes extends JavaPlugin {
    private NMLAttributes instance;
    private static NMLPlayerStats nmlPlayerStats;
    private ProfileManager profileManager;

    @Override
    public void onEnable() {
        instance = this;

        Plugin plugin = Bukkit.getPluginManager().getPlugin("NMLPlayerStats");
        if (plugin instanceof NMLPlayerStats statsPlugin) {
            nmlPlayerStats = statsPlugin;
            profileManager = nmlPlayerStats.getProfileManager();
        } else {
            getLogger().severe("Failed to find NMLPlayerStats! Disabling...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        getCommand("levelUp").setExecutor(new AttributesCommand());
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
    }

    public NMLAttributes getInstance() {
        return instance;
    }

    public static NMLPlayerStats getNmlPlayerStats() {
        return nmlPlayerStats;
    }

    public ProfileManager getProfileManager() {
        return profileManager;
    }
}
