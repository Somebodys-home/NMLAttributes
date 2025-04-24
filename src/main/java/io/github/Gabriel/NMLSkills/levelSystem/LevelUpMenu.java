package io.github.Gabriel.NMLSkills.levelSystem;

import io.github.Gabriel.NMLSkills.NMLAttributes;
import io.github.Gabriel.NMLSkills.attributeSystem.Attributes;
import io.github.Gabriel.NMLSkills.profileSystem.ProfileManager;
import io.github.Gabriel.menuSystem.Menu;
import io.github.Gabriel.menuSystem.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Objects;

public class LevelUpMenu extends Menu {
    private ProfileManager profileManager;
    private Attributes attributes;
    private int points;
    private int vitality;
    private int vitalityBonus;
    private int strength;
    private double strengthBonus;
    private int stamina;
    private int staminaBonus;
    private int arcane;
    private int overhealthBonus;

    private ItemStack pointsItem;
    private ItemStack vitalityItem;
    private ItemStack strengthItem;
    private ItemStack staminaItem;
    private ItemStack arcaneItem;

    public LevelUpMenu(PlayerMenuUtility playerMenuUtility, NMLAttributes nmlAttributes) {
        super(playerMenuUtility);
        profileManager = nmlAttributes.getProfileManager();
        attributes = profileManager.getPlayerProfile(super.playerMenuUtility.getOwner().getUniqueId()).getAttributes();
        updateAttributes();
        updateAttributeItems();
    }

    @Override
    public String getMenuName() {
        return ChatColor.translateAlternateColorCodes('&', "&6&lLEVEL UP TIME!");
    }

    @Override
    public int getSlots() {
        return 9 * 5;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        int points = attributes.getAttributePoints();

        switch (Objects.requireNonNull(event.getCurrentItem()).getType()) {
            case RED_CONCRETE -> {
                player.closeInventory();
                return;
            }

            case APPLE -> {
                if (event.isLeftClick() && points > 0) {
                    attributes.setVitality(attributes.getVitality() + 1);
                    attributes.setAttributePoints(points - 1);
                } else if (event.isRightClick() && attributes.getVitality() > 0) {
                    attributes.setVitality(attributes.getVitality() - 1);
                    attributes.setAttributePoints(points + 1);
                }
            }

            case OAK_LOG -> {
                if (event.isLeftClick() && points > 0) {
                    attributes.setStrength(attributes.getStrength() + 1);
                    attributes.setAttributePoints(points - 1);
                } else if (event.isRightClick() && attributes.getStrength() > 0) {
                    attributes.setStrength(attributes.getStrength() - 1);
                    attributes.setAttributePoints(points + 1);
                }
            }

            case ENCHANTED_BOOK -> {
                if (event.isLeftClick() && points > 0) {
                    attributes.setArcane(attributes.getArcane() + 1);
                    attributes.setAttributePoints(points - 1);
                } else if (event.isRightClick() && attributes.getArcane() > 0) {
                    attributes.setArcane(attributes.getArcane() - 1);
                    attributes.setAttributePoints(points + 1);
                }
            }

            case GOLD_INGOT -> {
                if (event.isLeftClick() && points > 0) {
                    attributes.setStamina(attributes.getStamina() + 1);
                    attributes.setAttributePoints(points - 1);
                } else if (event.isRightClick() && attributes.getStamina() > 0) {
                    attributes.setStamina(attributes.getStamina() - 1);
                    attributes.setAttributePoints(points + 1);
                }
            }

            case NETHER_STAR -> {
                if (event.isShiftClick() && event.isRightClick()) {
                    int totalPoints = attributes.getVitality() + attributes.getStrength() + attributes.getStamina() + attributes.getArcane();
                    attributes.setVitality(0);
                    attributes.setStrength(0);
                    attributes.setStamina(0);
                    attributes.setArcane(0);
                    attributes.setAttributePoints(attributes.getAttributePoints() + totalPoints);
                }
            }
        }

        updateAttributes();
        updateAttributeItems();
        setMenuItems();
        profileManager.getPlayerProfile(player.getUniqueId()).setAttributes(attributes);
        profileManager.updateStatsFromProfile(player);
    }

    @Override
    public void setMenuItems() {
        inventory.setItem(13, vitalityItem);
        inventory.setItem(19, strengthItem);
        inventory.setItem(22, pointsItem);
        inventory.setItem(25, staminaItem);
        inventory.setItem(31, arcaneItem);

        ItemStack exit = new ItemStack(Material.RED_CONCRETE, 1);
        ItemMeta exitMeta = exit.getItemMeta();
        exitMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4&lExit"));
        exit.setItemMeta(exitMeta);
        inventory.setItem(44, exit);
    }

    private void updateAttributes() {
        points = attributes.getAttributePoints();
        vitality = attributes.getVitality();
        vitalityBonus = (int) attributes.getVitalityBonus();
        strength = attributes.getStrength();
        strengthBonus = attributes.getStrengthBonus();
        stamina = attributes.getStamina();
        staminaBonus = (int) attributes.getEnergyBonus();
        arcane = attributes.getArcane();
        overhealthBonus = (int) attributes.getOverhealthBonus();
    }

    public void updateAttributeItems() {
        // vitality item
        vitalityItem = new ItemStack(Material.APPLE, vitality);
        if (vitality == 0) {
            vitalityItem.setAmount(1);
        }
        ItemMeta vMeta = vitalityItem.getItemMeta();
        ArrayList<String> vLore = new ArrayList<>();
        vMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&lVitality"));
        vLore.add(ChatColor.translateAlternateColorCodes('&', "&e&lLevel: &f" + vitality));
        vLore.add(ChatColor.translateAlternateColorCodes('&', "&e&lBonus: &f+" + vitalityBonus + " health"));
        vMeta.setLore(vLore);
        vitalityItem.setItemMeta(vMeta);

        // strength item
        strengthItem = new ItemStack(Material.OAK_LOG, strength);
        if (strength == 0) {
            strengthItem.setAmount(1);
        }
        ItemMeta sMeta = strengthItem.getItemMeta();
        ArrayList<String> sLore = new ArrayList<>();
        sMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&2&lStrength"));
        sLore.add(ChatColor.translateAlternateColorCodes('&', "&e&lLevel: &f" + strength));
        sLore.add(ChatColor.translateAlternateColorCodes('&', "&e&lBonus: &f+" + strengthBonus + "% melee damage"));
        sMeta.setLore(sLore);
        strengthItem.setItemMeta(sMeta);

        // arcane item
        arcaneItem = new ItemStack(Material.ENCHANTED_BOOK, arcane);
        if (arcane == 0) {
            arcaneItem.setAmount(1);
        }
        ItemMeta aMeta = arcaneItem.getItemMeta();
        ArrayList<String> aLore = new ArrayList<>();
        aMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&9&lArcane"));
        aLore.add(ChatColor.translateAlternateColorCodes('&', "&e&lLevel: &f" + arcane));
        aLore.add(ChatColor.translateAlternateColorCodes('&', "&e&lBonus: &f+" + overhealthBonus + " overhealth"));
        aMeta.setLore(aLore);
        arcaneItem.setItemMeta(aMeta);

        // stamina item
        staminaItem = new ItemStack(Material.GOLD_INGOT, stamina);
        if (stamina == 0) {
            staminaItem.setAmount(1);
        }
        ItemMeta saMeta = staminaItem.getItemMeta();
        ArrayList<String> saLore = new ArrayList<>();
        saMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6&lStamina"));
        saLore.add(ChatColor.translateAlternateColorCodes('&', "&e&lLevel: &f" + stamina));
        saLore.add(ChatColor.translateAlternateColorCodes('&', "&e&lBonus: &f+" + staminaBonus + " energy"));
        saMeta.setLore(saLore);
        staminaItem.setItemMeta(saMeta);

        // points item
        pointsItem = new ItemStack(Material.NETHER_STAR, points);
        if (points == 0) {
            pointsItem.setAmount(1);
        }
        ItemMeta pMeta = pointsItem.getItemMeta();
        ArrayList<String> pLore = new ArrayList<>();
        pMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&lAttribute Points: &f" + points));
        pLore.add(ChatColor.translateAlternateColorCodes('&', "&eLeft click to assign points!"));
        pLore.add(ChatColor.translateAlternateColorCodes('&', "&eRight click to remove points!"));
        pLore.add(ChatColor.translateAlternateColorCodes('&', "&eShift + Right click this item to reset all points!"));
        pMeta.setLore(pLore);
        pointsItem.setItemMeta(pMeta);
    }
}