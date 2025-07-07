package io.github.Gabriel.NMLAttributes;

import io.github.Gabriel.menuSystem.Menu;
import io.github.Gabriel.menuSystem.PlayerMenuUtility;
import io.github.NoOne.nMLPlayerStats.statSystem.Stats;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class AttributesMenu extends Menu {
    private Player player;
    private Stats stats;
    private ItemStack attributeItem;
    private ItemStack vitalityItem;
    private ItemStack strengthItem;
    private ItemStack arcaneItem;
    private ItemStack deftItem;

    public AttributesMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
        player = playerMenuUtility.getOwner();
        stats = NMLAttributes.getNmlPlayerStats().getProfileManager().getPlayerProfile(player.getUniqueId()).getStats();

        setItems(true);
    }

    @Override
    public String getMenuName() {
        return ChatColor.translateAlternateColorCodes('&', "&d&lLEVEL UP!");
    }

    @Override
    public int getSlots() {
        return 9 * 5;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        event.setCancelled(true);

        if (event.getClick().isLeftClick() && stats.getAttributePoints() > 0 && event.getSlot() != 22) {
            switch (event.getSlot()) {
                case 13 -> {
                    stats.add2Stat("vitality", 1);
                    stats.add2Stat("bonushealth", 1);
                    stats.add2Stat("bonusenergy", 1);
                }
                case 19 -> stats.add2Stat("strength", 1);
                case 25 -> {
                    stats.add2Stat("arcane", 1);
                    stats.add2Stat("bonusoverhealth", 1);
                }
                case 31 -> stats.add2Stat("deft", 1);
            }

            stats.setAttributePoints(stats.getAttributePoints() - 1);
        } else if (event.getClick().isRightClick() && stats.getAttributePoints() != stats.getLevel() - 1 && event.getSlot() != 22) {
            switch (event.getSlot()) {
                case 13 -> {
                    if (stats.getVitality() != 0) {
                        stats.removeFromStat("vitality", 1);
                        stats.removeFromStat("bonushealth", 1);
                        stats.removeFromStat("bonusenergy", 1);
                    }
                }
                case 19 -> {
                    if (stats.getStrength() != 0) {
                        stats.removeFromStat("strength", 1);
                    }
                }
                case 25 -> {
                    if (stats.getArcane() != 0) {
                        stats.removeFromStat("arcane", 1);
                        stats.removeFromStat("bonusoverhealth", 1);
                    }
                }
                case 31 -> {
                    if (stats.getDeft() != 0) {
                        stats.removeFromStat("deft", 1);
                    }
                }
            }

            stats.setAttributePoints(stats.getAttributePoints() + 1);
        }

        setItems(false);
    }

    @Override
    public void setMenuItems() {
        inventory.setItem(22, attributeItem);
        inventory.setItem(13, vitalityItem);
        inventory.setItem(19, strengthItem);
        inventory.setItem(25, arcaneItem);
        inventory.setItem(31, deftItem);
    }

    private void setItems(boolean startingUpMenu) {
        // attribute points item
        attributeItem = new ItemStack(Material.NETHER_STAR, Math.max(1, stats.getAttributePoints()));
        ItemMeta attributeMeta = attributeItem.getItemMeta();
        ArrayList<String> attributeLore = new ArrayList<>();
        assert attributeMeta != null;

        attributeMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&lAttribute Points:&r&f " + stats.getAttributePoints()));
        attributeLore.add(ChatColor.translateAlternateColorCodes('&', "&e&oLeft click an attribute to add a level to it"));
        attributeLore.add(ChatColor.translateAlternateColorCodes('&', "&e&oRight click an attribute to remove a level from it"));

        attributeMeta.setLore(attributeLore);
        attributeItem.setItemMeta(attributeMeta);

        // vitality item
        vitalityItem = new ItemStack(Material.APPLE, Math.max(1, stats.getVitality()));
        ItemMeta vitalityMeta = vitalityItem.getItemMeta();
        ArrayList<String> vitalityLore = new ArrayList<>();
        assert vitalityMeta != null;

        vitalityMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&lVitality&c&f Lv. " + stats.getVitality()));
        vitalityLore.add(ChatColor.translateAlternateColorCodes('&', "&7───── ❤ ─────"));
        vitalityLore.add(ChatColor.translateAlternateColorCodes('&', "&e&o+ " + stats.getVitality() + " Health"));
        vitalityLore.add(ChatColor.translateAlternateColorCodes('&', "&e&o+ " + stats.getVitality() + " Max Energy"));

        vitalityMeta.setLore(vitalityLore);
        vitalityItem.setItemMeta(vitalityMeta);

        // strength item
        strengthItem = new ItemStack(Material.OAK_LOG, Math.max(1, stats.getStrength()));
        ItemMeta strengthMeta = strengthItem.getItemMeta();
        ArrayList<String> strengthLore = new ArrayList<>();
        assert strengthMeta != null;

        strengthMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&2&lStrength&c&f Lv. " + stats.getStrength()));
        strengthLore.add(ChatColor.translateAlternateColorCodes('&', "&7───── ✊ ─────"));
        strengthLore.add(ChatColor.translateAlternateColorCodes('&', "&e&o+ " + stats.getStrength() + " Physical Damage"));
        strengthLore.add(ChatColor.translateAlternateColorCodes('&', "&e&o+ " + stats.getStrength() + " Physical Resist"));

        strengthMeta.setLore(strengthLore);
        strengthItem.setItemMeta(strengthMeta);

        // arcane item
        arcaneItem = new ItemStack(Material.BOOK, Math.max(1, stats.getArcane()));
        ItemMeta arcaneMeta = arcaneItem.getItemMeta();
        ArrayList<String> arcaneLore = new ArrayList<>();
        assert arcaneMeta != null;

        arcaneMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&d&lArcane&c&f Lv. " + stats.getArcane()));
        arcaneLore.add(ChatColor.translateAlternateColorCodes('&', "&7───── ✦ ─────"));
        arcaneLore.add(ChatColor.translateAlternateColorCodes('&', "&e&o+ " + stats.getArcane() + " Elemental Damage"));
        arcaneLore.add(ChatColor.translateAlternateColorCodes('&', "&e&o+ " + stats.getArcane() + " Max Overhealth"));

        arcaneMeta.setLore(arcaneLore);
        arcaneItem.setItemMeta(arcaneMeta);

        // deft item
        deftItem = new ItemStack(Material.WIND_CHARGE, Math.max(1, stats.getDeft()));
        ItemMeta deftMeta = deftItem.getItemMeta();
        ArrayList<String> deftLore = new ArrayList<>();
        assert deftMeta != null;

        deftMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7&lDeft&c&f Lv. " + stats.getDeft()));
        deftLore.add(ChatColor.translateAlternateColorCodes('&', "&7───── \uD83D\uDCA8 ─────"));
        deftLore.add(ChatColor.translateAlternateColorCodes('&', "&e&o+ " + stats.getDeft() + " Ranged Damage"));
        deftLore.add(ChatColor.translateAlternateColorCodes('&', "&e&o+ " + stats.getDeft() + " Evasion"));

        deftMeta.setLore(deftLore);
        deftItem.setItemMeta(deftMeta);

        if (!startingUpMenu) {
            setMenuItems();
        }
    }
}
