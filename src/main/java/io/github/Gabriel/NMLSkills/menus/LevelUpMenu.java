package io.github.Gabriel.NMLSkills.menus;

import io.github.Gabriel.NMLSkills.NMLAttributes;
import io.github.Gabriel.NMLSkills.player.attributeSystem.Attributes;
import io.github.Gabriel.NMLSkills.player.profileSystem.ProfileManager;
import io.github.Gabriel.menuSystem.Menu;
import io.github.Gabriel.menuSystem.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class LevelUpMenu extends Menu {
    private ProfileManager profileManager;
    private Attributes attributes;
    private int vitality;
    private int vitalityBonus;
    private int strength;
    private double strengthBonus;
    private int stamina;
    private int staminaBonus;
    private int points;

    private ItemStack pointsItem;
    private ItemStack vitalityItem;
    private ItemStack strengthItem;
    private ItemStack staminaItem;

    public LevelUpMenu(PlayerMenuUtility playerMenuUtility, NMLAttributes nmlAttributes) {
        super(playerMenuUtility);
        profileManager = nmlAttributes.getProfileManager();
        attributes = profileManager.getPlayerProfile(super.playerMenuUtility.getOwner().getUniqueId()).getAttributes();
        updateAttributes();
        setAttributeItems();
    }

    private void updateAttributes() {
        vitality = attributes.getVitality();
        vitalityBonus = attributes.getVitalityBonus();
        strength = attributes.getStrength();
        strengthBonus = attributes.getStrengthBonus();
        stamina = attributes.getStamina();
        staminaBonus = attributes.getStaminaBonus();
        points = attributes.getAttributePoints();
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
        Attributes attributes = profileManager.getPlayerProfile(player.getUniqueId()).getAttributes();
        int points = attributes.getAttributePoints();

        switch (event.getCurrentItem().getType()) {
            case RED_CONCRETE -> {
                event.getWhoClicked().closeInventory();
            }
            case APPLE -> { // vitality
                if (event.isLeftClick() && points > 0) {
                    attributes.setVitality(attributes.getVitality() + 1);
                    attributes.setAttributePoints(attributes.getAttributePoints() - 1);
                } else if (event.isRightClick()) {
                    if (attributes.getVitality() != 0) {
                        attributes.setVitality(attributes.getVitality() - 1);
                        attributes.setAttributePoints(attributes.getAttributePoints() + 1);
                    }
                }

                updateAttributes();
                setAttributeItems();
                setMenuItems();
                player.updateInventory();
            }
            case OAK_LOG -> { // strength
                if (event.isLeftClick() && points > 0) {
                    attributes.setStrength(attributes.getStrength() + 1);
                    attributes.setAttributePoints(attributes.getAttributePoints() - 1);
                } else if (event.isRightClick()) {
                    if (attributes.getStrength() != 0) {
                        attributes.setStrength(attributes.getStrength() - 1);
                        attributes.setAttributePoints(attributes.getAttributePoints() + 1);
                    }
                }

                updateAttributes();
                setAttributeItems();
                setMenuItems();
                player.updateInventory();
            }
            case GOLD_INGOT -> { // stamina
                if (event.isLeftClick() && points > 0) {
                    attributes.setStamina(attributes.getStamina() + 1);
                    attributes.setAttributePoints(attributes.getAttributePoints() - 1);
                } else if (event.isRightClick()) {
                    if (attributes.getStamina() != 0) {
                        attributes.setStamina(attributes.getStamina() - 1);
                        attributes.setAttributePoints(attributes.getAttributePoints() + 1);
                    }
                }

                updateAttributes();
                setAttributeItems();
                setMenuItems();
                player.updateInventory();
            }
            case NETHER_STAR -> {
                if (event.isShiftClick() && event.isRightClick()) {
                    int totalPoints = attributes.getVitality() + attributes.getStrength() + attributes.getStamina();
                    attributes.setVitality(0);
                    attributes.setStrength(0);
                    attributes.setStamina(0);
                    attributes.setAttributePoints(attributes.getAttributePoints() + totalPoints);
                }

                updateAttributes();
                setAttributeItems();
                setMenuItems();
                player.updateInventory();
            }
        }

        // Update the attributes in the player's profile
        profileManager.getPlayerProfile(player.getUniqueId()).setAttributes(attributes);
        profileManager.updateStatsFromProfile(player);
    }

    @Override
    public void setMenuItems() {
        inventory.setItem(13, vitalityItem);
        inventory.setItem(19, strengthItem);
        inventory.setItem(22, pointsItem);
        inventory.setItem(25, staminaItem);

        ItemStack exit = new ItemStack(Material.RED_CONCRETE, 1);
        ItemMeta exitMeta = exit.getItemMeta();
        exitMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4&lExit"));
        exit.setItemMeta(exitMeta);
        inventory.setItem(44, exit);
    }

    public void setAttributeItems() {
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