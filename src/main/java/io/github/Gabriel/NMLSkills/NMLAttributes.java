package io.github.Gabriel.NMLSkills;

import io.github.Gabriel.NMLSkills.commands.*;
import io.github.Gabriel.NMLSkills.energySystem.EnergyListener;
import io.github.Gabriel.NMLSkills.energySystem.EnergyManager;
import io.github.Gabriel.NMLSkills.menus.MenuSystem.MenuListener;
import io.github.Gabriel.NMLSkills.menus.MenuSystem.PlayerMenuUtility;
import io.github.Gabriel.NMLSkills.player.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class NMLAttributes extends JavaPlugin {
    private NMLAttributes instance;
    private ProfileManager profileManager;
    private ProfileConfig profileConfig;
    private static final HashMap<Player, PlayerMenuUtility> playerMenuUtilityMap = new HashMap<>();
    private EnergyManager energyManager;
    private PlayerActionBars playerActionBars;

    @Override
    public void onEnable() {
        instance = this;
        profileConfig = new ProfileConfig(this, "profiles");
        profileConfig.loadConfig();

        profileManager = new ProfileManager(this);
        profileManager.loadProfilesFromConfig();

        getCommand("attributes").setExecutor(new AttributesCommand(this));
        getCommand("setAttribute").setExecutor(new SetAttributeCommand(this));
        getCommand("useEnergy").setExecutor(new UseEnergyCommand(this));
        getCommand("resetEnergy").setExecutor(new ResetEnergyCommand(this));
        getCommand("levelUp").setExecutor(new LevelUpCommand(this));
        getCommand("setLevel").setExecutor(new SetLevelCommand(this));
        getServer().getPluginManager().registerEvents(new ProfileListener(this), this);
        getServer().getPluginManager().registerEvents(new AttributesListener(this), this);
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        getServer().getPluginManager().registerEvents(new EnergyListener(this), this);

        energyManager = new EnergyManager(this);
        energyManager.energyRegenServerTask();

        playerActionBars = new PlayerActionBars(this);
        playerActionBars.actionBarsTask();
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

    public static PlayerMenuUtility getPlayerMenuUtility(Player player) {
        PlayerMenuUtility playerMenuUtility;

        if (playerMenuUtilityMap.containsKey(player)) {
            return playerMenuUtilityMap.get(player);
        } else {
            playerMenuUtility = new PlayerMenuUtility(player);
            playerMenuUtilityMap.put(player, playerMenuUtility);
            return playerMenuUtility;
        }
    }
}
