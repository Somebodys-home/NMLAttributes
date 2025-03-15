package io.github.Gabriel.NMLSkills.player;

import io.github.Gabriel.NMLSkills.NMLAttributes;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Array;

public class PlayerActionBars {
    private NMLAttributes nmlAttributes;
    private ProfileManager profileManager;

    public PlayerActionBars(NMLAttributes nmlAttributes) {
        this.nmlAttributes = nmlAttributes;
        profileManager = this.nmlAttributes.getProfileManager();
    }

    public String[] setPlayerActionBarParts(Player player) {
        double currentEnergy = profileManager.getPlayerProfile(player.getUniqueId()).getAttributes().getCurrentEnergy();
        double maxEnergy = profileManager.getPlayerProfile(player.getUniqueId()).getAttributes().getMaxEnergy();
        String health = (ChatColor.translateAlternateColorCodes('&', "&c❤ " + (int) player.getHealth() + " / " + (int) player.getMaxHealth() + " ❤"));
        String energy = (ChatColor.translateAlternateColorCodes('&', "&6⚡ " +  (int) currentEnergy + " / " + (int) maxEnergy) + " ⚡");

        return new String[]{health, energy};
    }

    public void actionBarsTask() {
        new BukkitRunnable() {
            public void run(){
                for (Player player : Bukkit.getOnlinePlayers()) {
                    String[] actionBar = setPlayerActionBarParts(player);

                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(actionBar[0] + "       " + actionBar[1]));
                }
            }
        }.runTaskTimer(nmlAttributes, 0, 20);
    }
}
