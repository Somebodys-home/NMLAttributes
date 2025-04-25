package io.github.Gabriel.NMLSkills.energySystem;

import io.github.Gabriel.NMLSkills.NMLAttributes;
import io.github.Gabriel.NMLSkills.profileSystem.ProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class EnergyManager {
    private NMLAttributes nmlAttributes;
    private ProfileManager profileManager;

    public EnergyManager(NMLAttributes nmlAttributes) {
        this.nmlAttributes = nmlAttributes;
        profileManager = this.nmlAttributes.getProfileManager();
    }

    public void addEnergy(Player player, int amount) {
        int currentEnergy = profileManager.getPlayerProfile(player.getUniqueId()).getAttributes().getCurrentEnergy();
        int maxEnergy = profileManager.getPlayerProfile(player.getUniqueId()).getAttributes().getMaxEnergy();

        if ((amount + currentEnergy) > maxEnergy) {
            amount = maxEnergy - currentEnergy;
        }

        int edited = currentEnergy + amount;

        profileManager.getPlayerProfile(player.getUniqueId()).getAttributes().setCurrentEnergy(edited);
        updateEnergyBar(player);
    }

    public void useEnergy(Player player, int amount){
        int currentEnergy = profileManager.getPlayerProfile(player.getUniqueId()).getAttributes().getCurrentEnergy();
        int maxEnergy = profileManager.getPlayerProfile(player.getUniqueId()).getAttributes().getMaxEnergy();

        if (amount > maxEnergy) {
            amount = maxEnergy;
        }

        int edited = currentEnergy - amount;

        profileManager.getPlayerProfile(player.getUniqueId()).getAttributes().setCurrentEnergy(edited);
        updateEnergyBar(player);
    }

    public void updateEnergyBar(Player player) {
        int currentEnergy = profileManager.getPlayerProfile(player.getUniqueId()).getAttributes().getCurrentEnergy();
        int maxEnergy = profileManager.getPlayerProfile(player.getUniqueId()).getAttributes().getMaxEnergy();
        int currentEnergyPercent = currentEnergy / maxEnergy;

        if (currentEnergyPercent > 1) {
            currentEnergyPercent = 1;
        }

        int hungerLevel = (int) (currentEnergyPercent * 20);  // 20 is the max hunger level

        player.setFoodLevel(hungerLevel);
    }

    public void energyRegenServerTask() {
        new BukkitRunnable() {
            public void run(){
                for (Player player : Bukkit.getOnlinePlayers()) {
                    int currentEnergy = profileManager.getPlayerProfile(player.getUniqueId()).getAttributes().getCurrentEnergy();
                    int maxEnergy = profileManager.getPlayerProfile(player.getUniqueId()).getAttributes().getMaxEnergy();

                    if (currentEnergy < maxEnergy) {
                        addEnergy(player, (maxEnergy / 15)); // how long in seconds it takes to regen to full energy
                    }
                }
            }
        }.runTaskTimer(nmlAttributes, 0, 20);
    }
}