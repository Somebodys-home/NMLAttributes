package io.github.NoOne.NMLAttributes;

import io.github.NoOne.nMLPlayerStats.profileSystem.ProfileManager;
import io.github.NoOne.nMLPlayerStats.statSystem.StatChangeEvent;
import io.github.NoOne.nMLPlayerStats.statSystem.Stats;
import io.github.NoOne.nMLSkills.skillSetSystem.SkillSetManager;
import io.github.NoOne.nMLSkills.skillSystem.SkillChangeEvent;
import io.github.NoOne.nMLSkills.skillSystem.Skills;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.metadata.FixedMetadataValue;

public class AttributesListener implements Listener {
    private NMLAttributes nmlAttributes;
    private ProfileManager profileManager;
    private SkillSetManager skillSetManager;

    public AttributesListener(NMLAttributes nmlAttributes) {
        this.nmlAttributes = nmlAttributes;
        profileManager = nmlAttributes.getProfileManager();
        skillSetManager = nmlAttributes.getSkillSetManager();
    }

    @EventHandler
    public void onCombatSkillLevelUp(SkillChangeEvent event) {
        if (event.getChange() > 0 && event.getSkill().equals("combat")) {
            Player player = event.getPlayer();
            Skills skills = skillSetManager.getSkillSet(player.getUniqueId()).getSkills();
            Stats stats = profileManager.getPlayerProfile(player.getUniqueId()).getStats();
            int change = (int) event.getChange();
            int prevLevel = Math.max(skills.getCombatLevel() - change, 1);
            int newLevel = skills.getCombatLevel();

            player.playSound(player, Sound.UI_TOAST_CHALLENGE_COMPLETE, 1f, 1f);

            // chat message
            player.sendMessage("§c§l---------------------------");
            player.sendMessage("§c§lCOMBAT SKILL LEVEL UP!");
            player.sendMessage("§fLv. §r§8" + prevLevel + " -> §r§c" + newLevel + " §r§fFighter");
            player.sendMessage("");
            player.sendMessage("§4§lREWARDS:");
            player.sendMessage("§8" + stats.getAttributePoints() + " -> §r§f" + (stats.getAttributePoints() + change) + " §r§cAttribute Points §r§4(+" + change + ")");
            player.sendMessage("");
            player.sendMessage("§7§oUse attribute points in your ID");
            player.sendMessage("§7§oor in the §n/levelup§r§7§o menu");
            player.sendMessage("§c§l---------------------------");

            stats.add2Stat("attributepoints", change);

            // firework
            Firework firework = (Firework) player.getWorld().spawnEntity(player.getLocation().add(0, 2, 0), EntityType.FIREWORK_ROCKET);
            FireworkMeta fireworkMeta = firework.getFireworkMeta();

            fireworkMeta.addEffect(FireworkEffect.builder()
                    .withColor(Color.RED)
                    .withFade(Color.fromRGB(128, 22, 22))
                    .with(FireworkEffect.Type.BALL_LARGE)
                    .flicker(true)
                    .withTrail()
                    .build());
            fireworkMeta.setPower(0);
            firework.setFireworkMeta(fireworkMeta);
            firework.setMetadata("ability_firework", new FixedMetadataValue(nmlAttributes, true));
            firework.detonate();
        }
    }

    @EventHandler
    public void onAttributeChange(StatChangeEvent event) {
        Player player = event.getPlayer();
        Stats stats = profileManager.getPlayerProfile(player.getUniqueId()).getStats();
        double change = event.getChange();

        switch (event.getStat()) {
            case "constitution": {
                stats.add2Stat("maxenergy", change);
                Bukkit.getPluginManager().callEvent(new StatChangeEvent(player, "maxhealth", change)); // to sync health
            }
            case "strength": {
                stats.add2Stat("physicaldamage", change);
                stats.add2Stat("physicalresist", change);
            }
            case "intelligence": {
                Bukkit.getPluginManager().callEvent(new StatChangeEvent(player, "maxoverhealth", change)); // to sync overhealth
                stats.add2Stat("elementaldamage", change);
            }
            case "dexterity": {
                stats.add2Stat("evasion", change);
                stats.add2Stat("critchance", change / 2.0);
                stats.add2Stat("critdamage", change);
            }
        }
    }
}
