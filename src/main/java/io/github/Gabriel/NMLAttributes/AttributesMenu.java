package io.github.Gabriel.NMLAttributes;

import io.github.NoOne.menuSystem.Menu;
import io.github.NoOne.menuSystem.PlayerMenuUtility;
import io.github.NoOne.nMLPlayerStats.statSystem.Stats;
import io.github.NoOne.nMLSkills.skillSystem.Skills;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;

public class AttributesMenu extends Menu {
    private Player player;
    private Stats stats;
    private Skills skills;
    private ItemStack attributePoints;
    private ItemStack vitality;
    private ItemStack strength;
    private ItemStack arcane;
    private ItemStack deft;

    public AttributesMenu(NMLAttributes nmlAttributes, PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);

        player = playerMenuUtility.getOwner();
        stats = nmlAttributes.getProfileManager().getPlayerProfile(player.getUniqueId()).getStats();
        skills = nmlAttributes.getSkillSetManager().getSkillSet(player.getUniqueId()).getSkills();

        setItems(true);
    }

    @Override
    public String getMenuName() {
        return "§d§lLEVEL UP!";
    }

    @Override
    public int getSlots() {
        return 9 * 5;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        event.setCancelled(true);

        int amount = event.isShiftClick() ? 5 : 1;
        int attributePoints = stats.getAttributePoints();

        if (event.getClick().isLeftClick() && attributePoints > 0) { // adding to attributes
            if (attributePoints < amount) amount = attributePoints;

            stats.removeFromStat("attributepoints", amount);

            switch (event.getSlot()) {
                case 13 -> {
                    stats.add2Stat("vitality", amount);
                    stats.changeMaxHealth(player, amount);
                    stats.add2Stat("maxenergy", amount);
                }
                case 19 -> {
                    stats.add2Stat("strength", amount);
                }
                case 25 -> {
                    stats.add2Stat("arcane", amount);
                    stats.add2Stat("maxoverhealth", amount);
                }
                case 31 -> {
                    stats.add2Stat("deft", amount);
                    stats.add2Stat("evasion", amount);
                }
            }
        }
        // removing from attributes
        else if (event.getClick().isRightClick() && stats.getAttributePoints() <= (skills.getCombatLevel() - 1 - amount)) {
            switch (event.getSlot()) {
                case 13 -> {
                    if (stats.getVitality() < amount) amount = stats.getVitality();

                    stats.removeFromStat("vitality", amount);
                    stats.changeMaxHealth(player, -amount);
                    stats.removeFromStat("maxenergy", amount);
                }
                case 19 -> {
                    if (stats.getStrength() < amount) amount = stats.getStrength();

                    stats.removeFromStat("strength", amount);
                }
                case 25 -> {
                    if (stats.getArcane() < amount) amount = stats.getArcane();

                    stats.removeFromStat("arcane", amount);
                    stats.removeFromStat("maxoverhealth", amount);
                }
                case 31 -> {
                    if (stats.getDeft() < amount) amount = stats.getDeft();

                    stats.removeFromStat("deft", amount);
                    stats.removeFromStat("evasion", amount);
                }
            }

            stats.add2Stat("attributepoints", amount);
        }

        setItems(false);
    }

    @Override
    public void handlePlayerMenu(InventoryClickEvent event) {
        event.setCancelled(true);
    }


    @Override
    public void setMenuItems() {
        inventory.setItem(22, attributePoints);
        inventory.setItem(13, vitality);
        inventory.setItem(19, strength);
        inventory.setItem(25, arcane);
        inventory.setItem(31, deft);
    }

    private void setItems(boolean startingUpMenu) {
        attributePoints = new ItemStack(Material.NETHER_STAR, Math.max(1, stats.getAttributePoints()));
        ItemMeta attributeMeta = attributePoints.getItemMeta();
        ArrayList<String> attributeLore = new ArrayList<>();
        attributeMeta.setDisplayName("§b§lAttribute Points:§r§f " + stats.getAttributePoints());
        attributeLore.add("§e§oLeft click an attribute to add a level to it");
        attributeLore.add("§e§oRight click an attribute to remove a level from it");
        attributeLore.add("§e§oShift click to 5x that change");
        attributeMeta.setLore(attributeLore);
        attributePoints.setItemMeta(attributeMeta);

        vitality = new ItemStack(Material.APPLE, Math.max(1, stats.getVitality()));
        ItemMeta vitalityMeta = vitality.getItemMeta();
        ArrayList<String> vitalityLore = new ArrayList<>();
        vitalityMeta.setDisplayName("§c§lVitality§c§f Lv. " + stats.getVitality());
        vitalityLore.add("§7───── ❤ ─────");
        vitalityLore.add("§e§o+ " + stats.getVitality() + " Health");
        vitalityLore.add("§e§o+ " + stats.getVitality() + " Max Energy");
        vitalityMeta.setLore(vitalityLore);
        vitality.setItemMeta(vitalityMeta);

        strength = new ItemStack(Material.OAK_LOG, Math.max(1, stats.getStrength()));
        ItemMeta strengthMeta = strength.getItemMeta();
        ArrayList<String> strengthLore = new ArrayList<>();
        strengthMeta.setDisplayName("§2§lStrength§c§f Lv. " + stats.getStrength());
        strengthLore.add("§7───── ✊ ─────");
        strengthLore.add("§e§o+ " + stats.getStrength() + " Physical Damage");
        strengthLore.add("§e§o+ " + stats.getStrength() + " Physical Resist");
        strengthMeta.setLore(strengthLore);
        strength.setItemMeta(strengthMeta);

        arcane = new ItemStack(Material.BOOK, Math.max(1, stats.getArcane()));
        ItemMeta arcaneMeta = arcane.getItemMeta();
        ArrayList<String> arcaneLore = new ArrayList<>();
        arcaneMeta.setDisplayName("§d§lArcane§c§f Lv. " + stats.getArcane());
        arcaneLore.add("§7───── ✦ ─────");
        arcaneLore.add("§e§o+ " + stats.getArcane() + " Elemental Damage");
        arcaneLore.add("§e§o+ " + stats.getArcane() + " Max Overhealth");
        arcaneMeta.setLore(arcaneLore);
        arcane.setItemMeta(arcaneMeta);

        deft = new ItemStack(Material.WIND_CHARGE, Math.max(1, stats.getDeft()));
        ItemMeta deftMeta = deft.getItemMeta();
        ArrayList<String> deftLore = new ArrayList<>();
        deftMeta.setDisplayName("§7§lDeft§c§f Lv. " + stats.getDeft());
        deftLore.add("§7───── \uD83D\uDCA8 ─────");
        deftLore.add("§e§o+ " + stats.getDeft() + " Ranged Damage");
        deftLore.add("§e§o+ " + stats.getDeft() + " Evasion");
        deftMeta.setLore(deftLore);
        deft.setItemMeta(deftMeta);

        if (!startingUpMenu) setMenuItems();
    }
}
