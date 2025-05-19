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

public class AttributesMenu extends Menu { // todo: finalize menu when all the stats are finalized

    public AttributesMenu(PlayerMenuUtility playerMenuUtility, NMLAttributes nmlAttributes) {
        super(playerMenuUtility);
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
    }

    @Override
    public void setMenuItems() {
        underConstruction();
    }
}
