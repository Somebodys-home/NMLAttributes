package io.github.Gabriel.NMLSkills.profileSystem;

import io.github.Gabriel.NMLSkills.NMLAttributes;
import io.github.Gabriel.NMLSkills.attributeSystem.Attributes;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProfileManager {
    private NMLAttributes nmlAttributes;
    private static Map<UUID, Profile> profileMap = new HashMap<>(); // hashmap of all the profiles of all the players online atm
    private FileConfiguration config;
    private ProfileConfig profileConfig;

    public ProfileManager(NMLAttributes nmlAttributes) {
        this.nmlAttributes = nmlAttributes;
        profileConfig = nmlAttributes.getProfileConfig();
        config = profileConfig.getConfig();
    }

    public Profile createnewProfile(Player player) {
        Attributes attributes = new Attributes(1, 0, 0,0, 0, 0, 100, 100);
        Profile profile = new Profile(attributes);

        profileMap.put(player.getUniqueId(), profile);

        return profile;
    }

    public Profile getPlayerProfile(UUID uuid) {
        return profileMap.get(uuid);
    }

    public void loadProfilesFromConfig() {
        for (String id : config.getConfigurationSection("").getKeys(false)) {
            UUID uuid = UUID.fromString(id);
            int level = config.getInt(id + ".attributes.level");
            int exp = config.getInt(id + ".attributes.exp");
            int attributePoints = config.getInt(id + ".attributes.attributePoints");
            int vitality = config.getInt(id + ".attributes.vitality");
            int strength = config.getInt(id + ".attributes.strength");
            int arcane = config.getInt(id + ".attributes.arcane");
            int stamina = config.getInt(id + ".attributes.stamina");
            int maxEnergy = config.getInt(id + ".attributes.maxEnergy");
            int currentEnergy = config.getInt(id + ".attributes.currentEnergy");
            Attributes attributes = new Attributes(level, attributePoints, vitality, strength, arcane, stamina, maxEnergy, currentEnergy);
            Profile profile = new Profile(attributes);

            profileMap.put(uuid, profile);
        }
    }

    public void saveProfilesToConfig() {
        for (UUID uuid : profileMap.keySet()) {
            String id = uuid.toString();
            Profile profile = profileMap.get(uuid);
            Attributes attributes = profile.getAttributes();

            config.set(id + ".attributes.level", attributes.getLevel());
            config.set(id + ".attributes.exp", attributes.getExp());
            config.set(id + ".attributes.exp2NextLevel", attributes.getExp2NextLevel());
            config.set(id + ".attributes.attributePoints", attributes.getAttributePoints());
            config.set(id + ".attributes.vitality", attributes.getVitality());
            config.set(id + ".attributes.strength", attributes.getStrength());
            config.set(id + ".attributes.arcane", attributes.getArcane());
            config.set(id + ".attributes.stamina", attributes.getStamina());
            config.set(id + ".attributes.maxEnergy", attributes.getMaxEnergy());
            config.set(id + ".attributes.currentEnergy", attributes.getCurrentEnergy());
        }
    }

    public void saveAProfileToConfig(Player player) {
        String id = player.getUniqueId().toString();
        Profile profile = profileMap.get(player.getUniqueId());
        Attributes attributes = profile.getAttributes();

        config.set(id + ".attributes.level", attributes.getLevel());
        config.set(id + ".attributes.exp", attributes.getExp());
        config.set(id + ".attributes.exp2NextLevel", attributes.getExp2NextLevel());
        config.set(id + ".attributes.attributePoints", attributes.getAttributePoints());
        config.set(id + ".attributes.vitality", attributes.getVitality());
        config.set(id + ".attributes.strength", attributes.getStrength());
        config.set(id + ".attributes.arcane", attributes.getArcane());
        config.set(id + ".attributes.stamina", attributes.getStamina());
        config.set(id + ".attributes.maxEnergy", attributes.getMaxEnergy());
        config.set(id + ".attributes.currentEnergy", attributes.getCurrentEnergy());
    }

    public void updateStatsFromProfile(Player player) { // method given this name for future-proofing
        Profile profile = profileMap.get(player.getUniqueId());
        int vitality = profile.getAttributes().getVitality();

        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20D + vitality);
    }
}
