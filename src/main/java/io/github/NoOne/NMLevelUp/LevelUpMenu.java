package io.github.NoOne.NMLevelUp;

import io.github.NoOne.menuSystem.Menu;
import io.github.NoOne.nMLItems.ItemCreator;
import io.github.NoOne.nMLPlayerStats.statSystem.PlayerStat;
import io.github.NoOne.nMLPlayerStats.statSystem.StatChangeEvent;
import io.github.NoOne.nMLPlayerStats.statSystem.Stats;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.math.BigDecimal;
import java.util.List;

public class LevelUpMenu extends Menu {
    private Stats stats;

    public LevelUpMenu(Player player, NMLevelUp nmLevelUp) {
        super(player);

        stats = nmLevelUp.getStatConfigManager().getStats(player.getUniqueId());
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
        int attributePoints = stats.getIntStatValue(PlayerStat.ATTRIBUTE_POINTS);

        if (event.getClick().isLeftClick() && attributePoints > 0) { // adding to attributes
            amount = Math.min(amount, attributePoints);

            switch (event.getSlot()) {
                case 4 -> {
                    stats.removeFromStat(PlayerStat.ATTRIBUTE_POINTS, amount);
                    Bukkit.getPluginManager().callEvent(new StatChangeEvent(player, PlayerStat.CONSTITUTION, amount));
                    setMenuItems();
                }
                case 20 -> {
                    stats.removeFromStat(PlayerStat.ATTRIBUTE_POINTS, amount);
                    Bukkit.getPluginManager().callEvent(new StatChangeEvent(player, PlayerStat.STRENGTH, amount));
                    setMenuItems();
                }
                case 24 -> {
                    stats.removeFromStat(PlayerStat.ATTRIBUTE_POINTS, amount);
                    Bukkit.getPluginManager().callEvent(new StatChangeEvent(player, PlayerStat.INTELLIGENCE, amount));
                    setMenuItems();
                }
                case 39 -> {
                    stats.removeFromStat(PlayerStat.ATTRIBUTE_POINTS, amount);
                    Bukkit.getPluginManager().callEvent(new StatChangeEvent(player, PlayerStat.DEXTERITY, amount));
                    setMenuItems();
                }
                case 41 -> {
                    stats.removeFromStat(PlayerStat.ATTRIBUTE_POINTS, amount);
                    Bukkit.getPluginManager().callEvent(new StatChangeEvent(player, PlayerStat.CHARISMA, amount));
                    setMenuItems();
                }
            }
        } else if (event.getClick().isRightClick()) { // removing from attributes
            switch (event.getSlot()) {
                case 4 -> {
                    if (stats.getIntStatValue(PlayerStat.CONSTITUTION) > 1) {
                        amount = Math.min(amount, stats.getIntStatValue(PlayerStat.CONSTITUTION) - 1);
                        stats.addToStat(PlayerStat.ATTRIBUTE_POINTS, amount);
                        Bukkit.getPluginManager().callEvent(new StatChangeEvent(player, PlayerStat.CONSTITUTION, -amount));
                        setMenuItems();
                    }
                }
                case 20 -> {
                    if (stats.getIntStatValue(PlayerStat.STRENGTH) > 1) {
                        amount = Math.min(amount, stats.getIntStatValue(PlayerStat.STRENGTH) - 1);
                        stats.addToStat(PlayerStat.ATTRIBUTE_POINTS, amount);
                        Bukkit.getPluginManager().callEvent(new StatChangeEvent(player, PlayerStat.STRENGTH, -amount));
                        setMenuItems();
                    }
                }
                case 24 -> {
                    if (stats.getIntStatValue(PlayerStat.INTELLIGENCE) > 1) {
                        amount = Math.min(amount, stats.getIntStatValue(PlayerStat.INTELLIGENCE) - 1);
                        stats.addToStat(PlayerStat.ATTRIBUTE_POINTS, amount);
                        Bukkit.getPluginManager().callEvent(new StatChangeEvent(player, PlayerStat.INTELLIGENCE, -amount));
                        setMenuItems();
                    }
                }
                case 39 -> {
                    if (stats.getIntStatValue(PlayerStat.DEXTERITY) > 1) {
                        amount = Math.min(amount, stats.getIntStatValue(PlayerStat.DEXTERITY) - 1);
                        stats.addToStat(PlayerStat.ATTRIBUTE_POINTS, amount);
                        Bukkit.getPluginManager().callEvent(new StatChangeEvent(player, PlayerStat.INTELLIGENCE, -amount));
                        setMenuItems();
                    }
                }
                case 41 -> {
                    if (stats.getIntStatValue(PlayerStat.CHARISMA) > 1) {
                        amount = Math.min(amount, stats.getIntStatValue(PlayerStat.CHARISMA) - 1);
                        stats.addToStat(PlayerStat.ATTRIBUTE_POINTS, amount);
                        Bukkit.getPluginManager().callEvent(new StatChangeEvent(player, PlayerStat.CHARISMA, -amount));
                        setMenuItems();
                    }
                }
            }
        }
    }

    @Override
    public void handlePlayerMenu(InventoryClickEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void setMenuItems() {
        for (int i : getBorderSlots()) {
            inventory.setItem(i, ItemCreator.createMenuBorder());
        }

        inventory.setItem(22, ItemCreator.createItem( // instructions item
                Material.NETHER_STAR,
                Math.max(1, stats.getIntStatValue(PlayerStat.ATTRIBUTE_POINTS)),
                "§b§l§nAttribute Points:§r§f " + stats.getIntStatValue(PlayerStat.ATTRIBUTE_POINTS),
                List.of(
                        "§7- Left click an attribute to add a level to it",
                        "§7- Right click an attribute to remove a level from it",
                        "§7- Shift click to §n5x§r§7 that change"
                )
        ));
        inventory.setItem(4, ItemCreator.createItem( // CON
                Material.APPLE,
                Math.max(1, stats.getIntStatValue(PlayerStat.CONSTITUTION)),
                "§fLv. " + stats.getIntStatValue(PlayerStat.CONSTITUTION) + " §c§lConstitution",
                List.of(
                        "§7───── ❤ ─────",
                        "§e+ " + (stats.getIntStatValue(PlayerStat.CONSTITUTION) - 1) + " Health ❤",
                        "§e+ " + (stats.getIntStatValue(PlayerStat.CONSTITUTION) - 1) + " Max Energy ⚡"
                )
        ));
        inventory.setItem(20, ItemCreator.createItem( // STR
                Material.OAK_LOG,
                Math.max(1, stats.getIntStatValue(PlayerStat.STRENGTH)),
                "§fLv. " + stats.getIntStatValue(PlayerStat.STRENGTH) + " §2§lStrength",
                List.of(
                        "§7────── ✊ ──────",
                        "§e+ " + (stats.getIntStatValue(PlayerStat.STRENGTH) - 1) + " Physical Resist ⚔",
                        "§e+ " + (stats.getIntStatValue(PlayerStat.STRENGTH) - 1) + " Physical Damage ⚔"
                )
        ));
        inventory.setItem(24, ItemCreator.createItem( // INT
                Material.BOOK,
                Math.max(1, stats.getIntStatValue(PlayerStat.INTELLIGENCE)),
                "§fLv. " + stats.getIntStatValue(PlayerStat.INTELLIGENCE) + " §b§lIntelligence",
                List.of(
                        "§7────── ✦ ──────",
                        "§e+ " + (stats.getIntStatValue(PlayerStat.INTELLIGENCE) - 1) + " Max Overhealth \uD83D\uDC99",
                        "§e+ " + (stats.getIntStatValue(PlayerStat.INTELLIGENCE) - 1) + " Elemental Damage ✰"
                )
        ));
        inventory.setItem(39, ItemCreator.createItem( // DEX
                Material.WIND_CHARGE,
                Math.max(1, stats.getIntStatValue(PlayerStat.DEXTERITY)),
                "§fLv. " + stats.getIntStatValue(PlayerStat.DEXTERITY) + " §6§lDexterity",
                List.of(
                        "§7───── \uD83D\uDCA8 ─────",
                        "§e+ " + (stats.getIntStatValue(PlayerStat.DEXTERITY) - 1) + " Evasion \uD83D\uDCA8",
                        "§e+ " + new BigDecimal(String.valueOf((stats.getIntStatValue(PlayerStat.DEXTERITY) - 1) / 2.0)).stripTrailingZeros().toPlainString() + "% Crit Chance ☠",
                        "§e+ " + (stats.getIntStatValue(PlayerStat.DEXTERITY) - 1) + " Crit Damage ☠"
                )
        ));
        inventory.setItem(41, ItemCreator.createItem( // CHA
                Material.PINK_GLAZED_TERRACOTTA,
                Math.max(1, stats.getIntStatValue(PlayerStat.CHARISMA)),
                "§fLv. " + stats.getIntStatValue(PlayerStat.CHARISMA) + " §d§lCharisma",
                List.of(
                        "§7───── \uD83C\uDFAD ─────",
                        "§e(WIP)"
                )
        ));
        inventory.setItem(3, null);
        inventory.setItem(5, null);
        inventory.setItem(38, null);
        inventory.setItem(40, null);
        inventory.setItem(42, null);
    }
}
