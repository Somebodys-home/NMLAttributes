package io.github.Gabriel.NMLAttributes;

import io.github.Gabriel.NMLAttributes.attributeSystem.AttributesCommand;
import io.github.Gabriel.NMLAttributes.energySystem.EnergyListener;
import io.github.Gabriel.NMLAttributes.energySystem.EnergyManager;
import io.github.Gabriel.NMLAttributes.levelSystem.*;
import io.github.Gabriel.NMLAttributes.attributeSystem.AttributesListener;
import io.github.Gabriel.NMLAttributes.overhealthSystem.OverhealthListener;
import io.github.Gabriel.NMLAttributes.overhealthSystem.OverhealthManager;
import io.github.Gabriel.NMLAttributes.profileSystem.ProfileConfig;
import io.github.Gabriel.NMLAttributes.profileSystem.ProfileListener;
import io.github.Gabriel.NMLAttributes.profileSystem.ProfileManager;
import io.github.Gabriel.NMLAttributes.profileSystem.ResetProfileCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class NMLAttributes extends JavaPlugin {
    private NMLAttributes instance;
    private ProfileManager profileManager;
    private ProfileConfig profileConfig;
    private EnergyManager energyManager;
    private PlayerActionBar playerActionBar;
    private LevelManager levelManager;
    private OverhealthManager overhealthManager;

    @Override
    public void onEnable() {
        instance = this;

        profileConfig = new ProfileConfig(this, "profiles");
        profileConfig.loadConfig();

        profileManager = new ProfileManager(this);
        profileManager.loadProfilesFromConfig();

        energyManager = new EnergyManager(this);
        energyManager.energyRegenServerTask();

        playerActionBar = new PlayerActionBar(this);
        playerActionBar.actionBarsTask();

        levelManager = new LevelManager(this);
        levelManager.updateLevelBarTask();

        overhealthManager = new OverhealthManager(this);
        overhealthManager.startOverhealthTracker();

        getCommand("attributes").setExecutor(new AttributesCommand(this));
        getCommand("levelUp").setExecutor(new LevelUpCommand(this));
        getCommand("setLevel").setExecutor(new SetLevelCommand());
        getCommand("resetProfile").setExecutor(new ResetProfileCommand(this));

        getServer().getPluginManager().registerEvents(new ProfileListener(this), this);
        getServer().getPluginManager().registerEvents(new OverhealthListener(this), this);
        getServer().getPluginManager().registerEvents(new AttributesListener(this), this);
        getServer().getPluginManager().registerEvents(new EnergyListener(this), this);
        getServer().getPluginManager().registerEvents(new LevelingListener(this), this);
    }

    @Override
    public void onDisable() {
        // DO NOT CHANGE THE ORDER OF THIS, IT WILL BREAK
        profileManager.saveProfilesToConfig();
        profileConfig.saveConfig();
    }

    public NMLAttributes getInstance() {
        return instance;
    }

    public ProfileManager getProfileManager() {
        return profileManager;
    }

    public ProfileConfig getProfileConfig() {
        return profileConfig;
    }

    public EnergyManager getEnergyManager() {
        return energyManager;
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public OverhealthManager getOverhealthManager() {
        return overhealthManager;
    }
}
