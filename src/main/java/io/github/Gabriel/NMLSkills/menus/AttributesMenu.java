package io.github.Gabriel.NMLSkills.menus;

import io.github.Gabriel.NMLSkills.NMLAttributes;
import io.github.Gabriel.NMLSkills.menus.MenuSystem.Menu;
import io.github.Gabriel.NMLSkills.menus.MenuSystem.PlayerMenuUtility;
import io.github.Gabriel.NMLSkills.player.attributeSystem.Attributes;
import io.github.Gabriel.NMLSkills.player.profileSystem.ProfileManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class AttributesMenu extends Menu {
    private ProfileManager profileManager;
    private Attributes attributes;
    private int vitality;
    private int vitalityBonus;
    private int strength;
    private double strengthBonus;
    private int stamina;
    private int staminaBonus;
    private int level;

    private ItemStack vitalityItem;
    private ItemStack strengthItem;
    private ItemStack staminaItem;
    private ItemStack levelItem;

    public AttributesMenu(PlayerMenuUtility playerMenuUtility, NMLAttributes nmlAttributes) {
        super(playerMenuUtility);
        profileManager = nmlAttributes.getProfileManager();
        attributes = profileManager.getPlayerProfile(super.playerMenuUtility.getOwner().getUniqueId()).getAttributes();
        vitality = attributes.getVitality();
        vitalityBonus = attributes.getVitalityBonus();
        strength = attributes.getStrength();
        strengthBonus = attributes.getStrengthBonus();
        stamina = attributes.getStamina();
        staminaBonus = attributes.getStaminaBonus();
        level = attributes.getLevel();

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
        levelItem = new ItemStack(Material.NETHER_STAR, level);
        ItemMeta lMeta = levelItem.getItemMeta();
        lMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&lLevel: &f" + level));
        levelItem.setItemMeta(lMeta);
    }

    @Override
    public String getMenuName() {
        return ChatColor.translateAlternateColorCodes('&', "&4&lYOUR ATTRIBUTES");
    }

    @Override
    public int getSlots() {
        return 9 * 3;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        switch (event.getCurrentItem().getType()) {
            case RED_CONCRETE -> {
                event.getWhoClicked().closeInventory();
                break;
            }
        }
    }

    @Override
    public void setMenuItems() {
        inventory.setItem(4, levelItem);
        inventory.setItem(11, strengthItem);
        inventory.setItem(13, vitalityItem);
        inventory.setItem(15, staminaItem);

        ItemStack exit = new ItemStack(Material.RED_CONCRETE, 1);
            ItemMeta exitMeta = exit.getItemMeta();

            exitMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4&lExit"));
            exit.setItemMeta(exitMeta);

        inventory.setItem(26, exit);
    }
}
