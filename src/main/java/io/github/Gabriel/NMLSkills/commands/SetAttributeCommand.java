package io.github.Gabriel.NMLSkills.commands;

import io.github.Gabriel.NMLSkills.NMLAttributes;
import io.github.Gabriel.NMLSkills.player.attributeSystem.Attributes;
import io.github.Gabriel.NMLSkills.player.profileSystem.Profile;
import io.github.Gabriel.NMLSkills.player.profileSystem.ProfileManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SetAttributeCommand implements CommandExecutor, TabCompleter {
    private ProfileManager profileManager;

    public SetAttributeCommand(NMLAttributes nmlAttributes) {
        profileManager = nmlAttributes.getProfileManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            Attributes attributes = profileManager.getPlayerProfile(player.getUniqueId()).getAttributes();
            Profile profile = new Profile(attributes);
            int mod = Integer.parseInt(args[1]);

            if (args[0].equals("vitality")) {
                attributes.setVitality(mod);
                player.sendMessage("Vitality set to " + mod);
            }  else if (args[0].equals("strength")) {
                attributes.setStrength(mod);
                player.sendMessage("Strength set to " + mod);
            } else if (args[0].equals("stamina")) {
                attributes.setStamina(mod);
                player.sendMessage("Stamina set to " + mod);
            }

            profile.setAttributes(attributes);
            profileManager.saveAProfileToConfig(player);
            profileManager.updateStatsFromProfile(player);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            List<String> subCommands = Arrays.asList("vitality", "strength", "stamina");
            for (String subCmd : subCommands) {
                if (subCmd.toLowerCase().startsWith(args[0].toLowerCase())) {
                    completions.add(subCmd);
                }
            }
        }

        return completions;
    }
}
