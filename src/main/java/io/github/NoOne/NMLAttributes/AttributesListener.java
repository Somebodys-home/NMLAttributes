package io.github.NoOne.NMLAttributes;

import io.github.NoOne.nMLPlayerStats.profileSystem.ProfileManager;
import io.github.NoOne.nMLPlayerStats.statSystem.Stats;
import io.github.NoOne.nMLSkills.skillSetSystem.SkillSetManager;
import io.github.NoOne.nMLSkills.skillSystem.SkillChangeEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Objects;

public class AttributesListener implements Listener {
    private ProfileManager profileManager;
    private SkillSetManager skillSetManager;

    public AttributesListener(NMLAttributes nmlAttributes) {
        profileManager = nmlAttributes.getProfileManager();
        skillSetManager = nmlAttributes.getSkillSetManager();
    }

    @EventHandler
    public void levelingUpCombatSkill(SkillChangeEvent event) {
        if (Objects.equals(event.getStat(), "combat")) {
           Player player = event.getPlayer();
           Stats stats =  profileManager.getPlayerStats(event.getPlayer().getUniqueId());
           int combatLvl = skillSetManager.getSkillSet(player.getUniqueId()).getSkills().getCombatLevel();

           stats.add2Stat("attributepoints", event.getChange());
           player.sendMessage("Congrats, your combat level is now " + combatLvl + "!");
           player.sendMessage("You now have " + stats.getAttributePoints() + " attribute point(s) to use");
        }
    }
}
