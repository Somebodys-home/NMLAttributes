package io.github.NoOne.NMLevelUp;

import io.github.NoOne.menuSystem.MenuListener;
import io.github.NoOne.nMLPlayerStats.NMLPlayerStats;
import io.github.NoOne.nMLPlayerStats.StatConfigManager;
import io.github.NoOne.nMLSkills.NMLSkills;
import io.github.NoOne.nMLSkills.SaveSkillsManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class NMLevelUp extends JavaPlugin {
    private StatConfigManager statConfigManager;
    private SaveSkillsManager saveSkillsManager;

    @Override
    public void onEnable() {
        statConfigManager = JavaPlugin.getPlugin(NMLPlayerStats.class).getStatConfigManager();
        saveSkillsManager = JavaPlugin.getPlugin(NMLSkills.class).getSaveSkillsManager();

        getCommand("levelUp").setExecutor(new LevelUpCommand(this));
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        getServer().getPluginManager().registerEvents(new LevelUpListener(this), this);
    }

    public StatConfigManager getStatConfigManager() {
        return statConfigManager;
    }

    public SaveSkillsManager getSaveSkillsManager() {
        return saveSkillsManager;
    }
}
