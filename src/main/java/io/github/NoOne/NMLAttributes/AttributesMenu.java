package io.github.NoOne.NMLAttributes;

import io.github.NoOne.menuSystem.Menu;
import io.github.NoOne.menuSystem.PlayerMenuUtility;
import io.github.NoOne.nMLItems.ItemCreator;
import io.github.NoOne.nMLPlayerStats.statSystem.StatChangeEvent;
import io.github.NoOne.nMLPlayerStats.statSystem.Stats;
import io.github.NoOne.nMLSkills.skillSystem.Skills;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.math.BigDecimal;
import java.util.List;

public class AttributesMenu extends Menu {
    private Player player;
    private Stats stats;

    public AttributesMenu(NMLAttributes nmlAttributes, PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);

        player = playerMenuUtility.getOwner();
        stats = nmlAttributes.getProfileManager().getPlayerProfile(player.getUniqueId()).getStats();
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
            amount = Math.min(amount, attributePoints);

            switch (event.getSlot()) {
                case 4 -> {
                    stats.removeFromStat("attributepoints", amount);
                    Bukkit.getPluginManager().callEvent(new StatChangeEvent(player, "constitution", amount));
                    setMenuItems();
                }
                case 20 -> {
                    stats.removeFromStat("attributepoints", amount);
                    Bukkit.getPluginManager().callEvent(new StatChangeEvent(player, "strength", amount));
                    setMenuItems();
                }
                case 24 -> {
                    stats.removeFromStat("attributepoints", amount);
                    Bukkit.getPluginManager().callEvent(new StatChangeEvent(player, "intelligence", amount));
                    setMenuItems();
                }
                case 39 -> {
                    stats.removeFromStat("attributepoints", amount);
                    Bukkit.getPluginManager().callEvent(new StatChangeEvent(player, "dexterity", amount));
                    setMenuItems();
                }
                case 41 -> {
                    stats.removeFromStat("attributepoints", amount);
                    Bukkit.getPluginManager().callEvent(new StatChangeEvent(player, "charisma", amount));
                    setMenuItems();
                }
            }
        } else if (event.getClick().isRightClick()) { // removing from attributes
            switch (event.getSlot()) {
                case 4 -> {
                    if (stats.getConstitution() > 1) {
                        amount = Math.min(amount, stats.getConstitution() - 1);
                        stats.add2Stat("attributepoints", amount);
                        Bukkit.getPluginManager().callEvent(new StatChangeEvent(player, "constitution", -amount));
                        setMenuItems();
                    }
                }
                case 20 -> {
                    if (stats.getStrength() > 1) {
                        amount = Math.min(amount, stats.getStrength() - 1);
                        stats.add2Stat("attributepoints", amount);
                        Bukkit.getPluginManager().callEvent(new StatChangeEvent(player, "strength", -amount));
                        setMenuItems();
                    }
                }
                case 24 -> {
                    if (stats.getIntelligence() > 1) {
                        amount = Math.min(amount, stats.getIntelligence() - 1);
                        stats.add2Stat("attributepoints", amount);
                        Bukkit.getPluginManager().callEvent(new StatChangeEvent(player, "intelligence", -amount));
                        setMenuItems();
                    }
                }
                case 39 -> {
                    if (stats.getDexterity() > 1) {
                        amount = Math.min(amount, stats.getDexterity() - 1);
                        stats.add2Stat("attributepoints", amount);
                        Bukkit.getPluginManager().callEvent(new StatChangeEvent(player, "dexterity", -amount));
                        setMenuItems();
                    }
                }
                case 41 -> {
                    if (stats.getCharisma() > 1) {
                        amount = Math.min(amount, stats.getCharisma() - 1);
                        stats.add2Stat("attributepoints", amount);
                        Bukkit.getPluginManager().callEvent(new StatChangeEvent(player, "charisma", -amount));
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
        inventory.setItem(22, ItemCreator.createItem( // instructions item
                Material.NETHER_STAR,
                Math.max(1, stats.getAttributePoints()),
                "§b§l§nAttribute Points:§r§f " + stats.getAttributePoints(),
                List.of(
                        "§7- Left click an attribute to add a level to it",
                        "§7- Right click an attribute to remove a level from it",
                        "§7- Shift click to §n5x§r§7 that change"
                )
        ));
        inventory.setItem(4, ItemCreator.createItem( // CON
                Material.APPLE,
                Math.max(1, stats.getConstitution()),
                "§fLv. " + stats.getConstitution() + " §c§lConstitution",
                List.of(
                        "§7───── ❤ ─────",
                        "§e+ " + (stats.getConstitution() - 1) + " Health ❤",
                        "§e+ " + (stats.getConstitution() - 1) + " Max. Energy ⚡"
                )
        ));
        inventory.setItem(20, ItemCreator.createItem( // STR
                Material.OAK_LOG,
                Math.max(1, stats.getStrength()),
                "§fLv. " + stats.getStrength() + " §2§lStrength",
                List.of(
                        "§7────── ✊ ──────",
                        "§e+ " + (stats.getStrength() - 1) + " Physical Resist ⚔",
                        "§e+ " + (stats.getStrength() - 1) + " Physical Damage ⚔"
                )
        ));
        inventory.setItem(24, ItemCreator.createItem( // INT
                Material.BOOK,
                Math.max(1, stats.getIntelligence()),
                "§fLv. " + stats.getIntelligence() + " §b§lIntelligence",
                List.of(
                        "§7────── ✦ ──────",
                        "§e+ " + (stats.getIntelligence() - 1) + " Max. Overhealth \uD83D\uDC99",
                        "§e+ " + (stats.getIntelligence() - 1) + " Elemental Damage ✰"
                )
        ));
        inventory.setItem(39, ItemCreator.createItem( // DEX
                Material.WIND_CHARGE,
                Math.max(1, stats.getDexterity()),
                "§fLv. " + stats.getDexterity() + " §6§lDexterity",
                List.of(
                        "§7───── \uD83D\uDCA8 ─────",
                        "§e+ " + (stats.getDexterity() - 1) + " Evasion \uD83D\uDCA8",
                        "§e+ " + new BigDecimal(String.valueOf((stats.getDexterity() - 1) / 2.0)).stripTrailingZeros().toPlainString() + "% Crit Chance ☠",
                        "§e+ " + (stats.getDexterity() - 1) + " Crit Damage ☠"
                )
        ));
        inventory.setItem(41, ItemCreator.createItem( // CHA
                Material.PINK_GLAZED_TERRACOTTA,
                Math.max(1, stats.getCharisma()),
                "§fLv. " + stats.getCharisma() + " §d§lCharisma",
                List.of(
                        "§7───── \uD83C\uDFAD ─────",
                        "§e(WIP)"
                )
        ));
    }
}
