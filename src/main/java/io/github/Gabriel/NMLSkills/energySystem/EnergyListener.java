package io.github.Gabriel.NMLSkills.energySystem;

import io.github.Gabriel.NMLSkills.NMLAttributes;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class EnergyListener implements Listener {
    private NMLAttributes nmlAttributes;

    public EnergyListener(NMLAttributes nmlAttributes) {
        this.nmlAttributes = nmlAttributes;
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player player) {
            int foodLevelChange = event.getFoodLevel() - player.getFoodLevel();
            double energyChange = foodLevelChange * (nmlAttributes.getProfileManager().getPlayerProfile(player.getUniqueId()).getAttributes().getMaxEnergy() / 20);

            nmlAttributes.getProfileManager().getPlayerProfile(player.getUniqueId()).getAttributes().setCurrentEnergy(nmlAttributes.getProfileManager().getPlayerProfile(player.getUniqueId()).getAttributes().getCurrentEnergy() + energyChange);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        double maxEnergy = nmlAttributes.getProfileManager().getPlayerProfile(player.getUniqueId()).getAttributes().getMaxEnergy();

        if (player.isSprinting()) {
            nmlAttributes.getEnergyManager().useEnergy(player, maxEnergy / 200);
        }
    }

    @EventHandler
    public void onFoodLevelChangeFromSprinting(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (player.isSprinting()) {
                event.setCancelled(true);
            }
        }
    }
}
