package io.github.Gabriel.NMLAttributes.profileSystem;

import io.github.Gabriel.NMLAttributes.NMLAttributes;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ProfileListener implements Listener {
    private ProfileManager profileManager;

    public ProfileListener(NMLAttributes nmlAttributes) {
        profileManager = nmlAttributes.getProfileManager();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Profile profile = profileManager.getPlayerProfile(player.getUniqueId());

        if (profile == null) {
            profileManager.createnewProfile(player);
        }
    }
}
