package io.github.Gabriel.NMLSkills;

import io.github.Gabriel.NMLSkills.commands.*;
import io.github.Gabriel.NMLSkills.energySystem.EnergyListener;
import io.github.Gabriel.NMLSkills.energySystem.EnergyManager;
import io.github.Gabriel.NMLSkills.levelSystem.LevelManager;
import io.github.Gabriel.NMLSkills.levelSystem.LevelingListener;
import io.github.Gabriel.NMLSkills.player.attributeSystem.AttributesListener;
import io.github.Gabriel.NMLSkills.player.attributeSystem.PlayerActionBar;
import io.github.Gabriel.NMLSkills.player.profileSystem.ProfileConfig;
import io.github.Gabriel.NMLSkills.player.profileSystem.ProfileListener;
import io.github.Gabriel.NMLSkills.player.profileSystem.ProfileManager;
import io.github.Gabriel.menuSystem.MenuListener;
import io.github.Gabriel.menuSystem.MenuSystem;
import org.bukkit.plugin.java.JavaPlugin;

public final class NMLAttributes extends JavaPlugin {
    private NMLAttributes instance;
    private ProfileManager profileManager;
    private ProfileConfig profileConfig;
    private EnergyManager energyManager;
    private PlayerActionBar playerActionBar;
    private LevelManager levelManager;

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

        getCommand("attributes").setExecutor(new AttributesCommand(this));
        getCommand("setAttribute").setExecutor(new SetAttributeCommand(this));
        getCommand("useEnergy").setExecutor(new UseEnergyCommand(this));
        getCommand("resetEnergy").setExecutor(new ResetEnergyCommand(this));
        getCommand("levelUp").setExecutor(new LevelUpCommand(this));
        getCommand("setLevel").setExecutor(new SetLevelCommand());
        getCommand("resetProfile").setExecutor(new ResetProfileCommand(this));
        getCommand("setExp").setExecutor(new SetExpCommand(this));

        getServer().getPluginManager().registerEvents(new ProfileListener(this), this);
        getServer().getPluginManager().registerEvents(new AttributesListener(this), this);
        getServer().getPluginManager().registerEvents(new EnergyListener(this), this);
        getServer().getPluginManager().registerEvents(new LevelingListener(this), this);
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
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
}
