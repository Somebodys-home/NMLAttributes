package io.github.Gabriel.NMLAttributes;

import io.github.Gabriel.NMLAttributes.attributeSystem.AttributesCommand;
import io.github.Gabriel.NMLAttributes.attributeSystem.AttributesListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class NMLAttributes extends JavaPlugin {
    private NMLAttributes instance;

    @Override
    public void onEnable() {
        instance = this;

        getCommand("attributes").setExecutor(new AttributesCommand(this));
        getServer().getPluginManager().registerEvents(new AttributesListener(), this);
    }

    public NMLAttributes getInstance() {
        return instance;
    }
}
