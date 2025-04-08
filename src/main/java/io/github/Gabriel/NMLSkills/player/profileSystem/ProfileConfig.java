package io.github.Gabriel.NMLSkills.player.profileSystem;

import io.github.Gabriel.NMLSkills.NMLAttributes;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;

public class ProfileConfig {
    private NMLAttributes nmlAttributes;
    private File file;
    private String fileName;
    private FileConfiguration config = new YamlConfiguration();

    public ProfileConfig(NMLAttributes nmlAttributes, String filename) {
        this.nmlAttributes = nmlAttributes;
        this.fileName = filename;
        file = new File(nmlAttributes.getDataFolder(), filename + ".yml");
    }

    public void loadConfig() {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            nmlAttributes.saveResource(fileName + ".yml", false);
        } try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException exception) {
            exception.printStackTrace();
        }
    }

    public void saveConfig() {
        try {
            config.save(file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public FileConfiguration getConfig() {
        return config;
    }
}
