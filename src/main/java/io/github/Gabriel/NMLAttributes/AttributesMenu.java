package io.github.Gabriel.NMLAttributes;

import io.github.Gabriel.menuSystem.Menu;
import io.github.Gabriel.menuSystem.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryClickEvent;

public class AttributesMenu extends Menu { // todo: finalize menu when all the stats are finalized

    public AttributesMenu(PlayerMenuUtility playerMenuUtility) {
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

    }
}
