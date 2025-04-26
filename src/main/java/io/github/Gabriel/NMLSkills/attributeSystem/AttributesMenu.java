package io.github.Gabriel.NMLSkills.attributeSystem;

import io.github.Gabriel.NMLSkills.NMLAttributes;
import io.github.Gabriel.NMLSkills.profileSystem.ProfileManager;
import io.github.Gabriel.menuSystem.Menu;
import io.github.Gabriel.menuSystem.PlayerMenuUtility;
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
    private double vitalityBonus;
    private int strength;
    private double strengthBonus;
    private int arcane;
    private double overhealthBonus;
    private int stamina;
    private int energyBonus;
    private int level;
    private int exp;
    private int exp4LevelUp;

    private ItemStack vitalityItem;
    private ItemStack strengthItem;
    private ItemStack arcaneItem;
    private ItemStack staminaItem;
    private ItemStack levelItem;

    public AttributesMenu(PlayerMenuUtility playerMenuUtility, NMLAttributes nmlAttributes) {
        super(playerMenuUtility);
        profileManager = nmlAttributes.getProfileManager();
        attributes = profileManager.getPlayerProfile(super.playerMenuUtility.getOwner().getUniqueId()).getAttributes();
        level = attributes.getLevel();
        exp = attributes.getExp();
        exp4LevelUp = attributes.getExp2NextLevel();
        vitality = attributes.getVitality();
        vitalityBonus = attributes.getVitalityBonus();
        strength = attributes.getStrength();
        strengthBonus = attributes.getStrengthBonus();
        stamina = attributes.getStamina();
        energyBonus = (int) attributes.getEnergyBonus();
        arcane = attributes.getArcane();
        overhealthBonus = attributes.getOverhealthBonus();

        // points item
        levelItem = new ItemStack(Material.NETHER_STAR, level);
        ArrayList<String> pLore = new ArrayList<>();
        ItemMeta pMeta = levelItem.getItemMeta();
        pMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&lLevel: &f" + level));
        pLore.add(ChatColor.translateAlternateColorCodes('&', "&b&lProgress: &f" + exp + " / " + exp4LevelUp + " exp"));
        pMeta.setLore(pLore);
        levelItem.setItemMeta(pMeta);
        levelItem.setItemMeta(pMeta);

        // vitality item
        vitalityItem = new ItemStack(Material.APPLE, vitality);
        if (vitality == 0) {
            vitalityItem.setAmount(1);
        }
        ItemMeta vMeta = vitalityItem.getItemMeta();
        ArrayList<String> vLore = new ArrayList<>();
        vMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&lVitality"));
        vLore.add(ChatColor.translateAlternateColorCodes('&', "&e&lLevel: &f" + vitality));
        vLore.add(ChatColor.translateAlternateColorCodes('&', "&e&lBonus: &f+" + (int) vitalityBonus + " health"));
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
        sLore.add(ChatColor.translateAlternateColorCodes('&', "&e&lBonus: &f+" + (int) strengthBonus + "% melee damage"));
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
        aLore.add(ChatColor.translateAlternateColorCodes('&', "&e&lBonus: &f+" + overhealthBonus + " max overhealth"));
        aLore.add(ChatColor.translateAlternateColorCodes('&', "&e&lCurrent Overhealth: &f" + profileManager.getPlayerProfile(playerMenuUtility.getOwner().getUniqueId()).getAttributes().getCurrentOverhealth()));
        aLore.add(ChatColor.translateAlternateColorCodes('&', "&e&lTotal Overhealth: &f" + profileManager.getPlayerProfile(playerMenuUtility.getOwner().getUniqueId()).getAttributes().getMaxOverhealth()));
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
        saLore.add(ChatColor.translateAlternateColorCodes('&', "&e&lBonus: &f+" + energyBonus + " energy"));
        saMeta.setLore(saLore);
        staminaItem.setItemMeta(saMeta);
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
        if (event.getSlot() == 26) {
            event.getWhoClicked().closeInventory();
        }
    }

    @Override
    public void setMenuItems() {
        inventory.setItem(4, levelItem);
        inventory.setItem(10, strengthItem);
        inventory.setItem(12, vitalityItem);
        inventory.setItem(14, staminaItem);
        inventory.setItem(16, arcaneItem);

        ItemStack exit = new ItemStack(Material.RED_CONCRETE, 1);
            ItemMeta exitMeta = exit.getItemMeta();

            exitMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4&lExit"));
            exit.setItemMeta(exitMeta);

        inventory.setItem(26, exit);
    }
}
