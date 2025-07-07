package io.github.Gabriel.NMLAttributes;

import org.bukkit.plugin.java.JavaPlugin;

public final class NMLAttributes extends JavaPlugin {
    private NMLAttributes instance;

    @Override
    public void onEnable() {
        instance = this;

        getCommand("levelUp").setExecutor(new AttributesCommand());
        getServer().getPluginManager().registerEvents(new AttributesListener(), this);
    }

    public NMLAttributes getInstance() {
        return instance;
    }
}
