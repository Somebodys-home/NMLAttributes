package io.github.NoOne.NMLevelUp;

import io.github.NoOne.menuSystem.MenuListener;
import io.github.NoOne.nMLPlayerStats.NMLPlayerStats;
import io.github.NoOne.nMLPlayerStats.profileSystem.ProfileManager;
import io.github.NoOne.nMLSkills.NMLSkills;
import io.github.NoOne.nMLSkills.skillSetSystem.SkillSetManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class NMLevelUp extends JavaPlugin {
    private ProfileManager profileManager;
    private SkillSetManager skillSetManager;

    @Override
    public void onEnable() {
        profileManager = JavaPlugin.getPlugin(NMLPlayerStats.class).getProfileManager();
        skillSetManager = JavaPlugin.getPlugin(NMLSkills.class).getSkillSetManager();

        getCommand("levelUp").setExecutor(new LevelUpCommand(this));
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        getServer().getPluginManager().registerEvents(new LevelUpListener(this), this);
    }

    public ProfileManager getProfileManager() {
        return profileManager;
    }

    public SkillSetManager getSkillSetManager() {
        return skillSetManager;
    }
}
