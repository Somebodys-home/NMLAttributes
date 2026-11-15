package io.github.NoOne.NMLAttributes;

import io.github.NoOne.nMLPlayerStats.profileSystem.ProfileManager;
import io.github.NoOne.nMLPlayerStats.statSystem.PlayerStat;
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
            Stats stats = profileManager.getStats(player.getUniqueId());
            int change = (int) event.getChange();
            int prevLevel = Math.max(skills.getCombatLevel() - change, 1);
            int newLevel = skills.getCombatLevel();

            player.playSound(player, Sound.UI_TOAST_CHALLENGE_COMPLETE, 1f, 1f);

            // chat message
            player.sendMessage("§c§l---------------------------");
            player.sendMessage("§c§lCOMBAT SKILL LEVEL UP!");
            player.sendMessage("§fLv. §r§8" + prevLevel + " -> §r§c" + newLevel + " §r§fFighter");
            player.sendMessage("");
            player.sendMessage("§4§lREWARDS ->");
            player.sendMessage("§8" + stats.getStatValue(PlayerStat.ATTRIBUTE_POINTS) + " -> §r§f" + (stats.getStatValue(PlayerStat.ATTRIBUTE_POINTS) + change) + " §r§cAttribute Points §r§4(+" + change + ")");
            player.sendMessage("");
            player.sendMessage("§7§oUse attribute points in your ID");
            player.sendMessage("§7§oor in the §n/levelup§r§7§o menu");
            player.sendMessage("§c§l---------------------------");

            stats.addToStat(PlayerStat.ATTRIBUTE_POINTS, change);

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
            firework.setMetadata("no_damage", new FixedMetadataValue(nmlAttributes, true));
            firework.detonate();
        }
    }

    @EventHandler
    public void onAttributeChange(StatChangeEvent event) {
        Player player = event.getPlayer();
        Stats stats = profileManager.getStats(player.getUniqueId());
        double change = event.getChange();

        switch (event.getStat()) {
            case CONSTITUTION -> {
                Bukkit.getPluginManager().callEvent(new StatChangeEvent(player, PlayerStat.MAX_HEALTH, change)); // to sync
                Bukkit.getPluginManager().callEvent(new StatChangeEvent(player, PlayerStat.MAX_ENERGY, change)); // to sync
            }
            case STRENGTH -> {
                stats.addToStat(PlayerStat.PHYSICAL_DAMAGE, change);
                stats.addToStat(PlayerStat.PHYSICAL_RESIST, change);
            }
            case INTELLIGENCE -> {
                Bukkit.getPluginManager().callEvent(new StatChangeEvent(player, PlayerStat.MAX_OVERHEALTH, change)); // to sync
                stats.addToStat(PlayerStat.ELEMENTAL_DAMAGE, change);
            }
            case DEXTERITY -> {
                stats.addToStat(PlayerStat.EVASION, change);
                stats.addToStat(PlayerStat.CRIT_CHANCE, change / 2.0);
                stats.addToStat(PlayerStat.CRIT_DAMAGE, change);
            }
        }
    }
}
