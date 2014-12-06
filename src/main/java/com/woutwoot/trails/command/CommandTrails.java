package com.woutwoot.trails.command;


import com.woutwoot.trails.Main;
import com.woutwoot.trails.Trail;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 9/2/2014
 */
public class CommandTrails extends AbstractCommand {

    public CommandTrails() {
        super("trail");
    }

    //returns a list of only string that start with the filter
    public static List<String> filterStartsWith(List<String> in, String filter) {
        List<String> out = new ArrayList<String>();
        for (int i = 0; i < in.size(); i++) {
            if (in.get(i).startsWith(filter)) {
                out.add(in.get(i));
            }
        }
        return out;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!player.hasPermission("trails.trail") && !Main.perTrailPerm) {
                player.sendMessage(Main.prefix + "You dont have permission to use trails!");
                return true;
            }
            if (args.length < 1) {
                player.sendMessage(Main.prefix + "The correct use is /trail <trail>");
                return true;
            }
            if (Main.perTrailPerm) {
                String trail = args[0];
                if (Main.registeredTrails.containsKey(trail)) {
                    if (!player.hasPermission("trails." + trail) && Main.perTrailPerm) {
                        player.sendMessage(Main.prefix + "You dont have permission to use the trail " + trail + "!");
                        return true;
                    }
                } else if (!trail.equalsIgnoreCase("clear")) {
                    player.sendMessage(Main.prefix + "No Trail with the name " + args[0]);
                }
            }
            if (args[0].equals("crack")) {
                if (args.length < 2) {
                    player.sendMessage(Main.prefix + "The correct use is /trail crack <block id>");
                    return true;
                }
                int id;
                try {
                    id = Integer.parseInt(args[1]);
                } catch (NumberFormatException exception) {
                    player.sendMessage(Main.prefix + "Id must be an int!");
                    return true;
                }
                if (id == 0) {
                    player.sendMessage(Main.prefix + "Id cant be 0!");
                    return true;
                }
                Main.effects.put(player.getUniqueId(), Trail.crack);
                Main.crackedIds.put(player.getUniqueId(), id);
                player.sendMessage(Main.prefix + "Enabled crack trail with block id " + args[1] + " , do /trail clear to clear");
                return true;
            }
            if (args[0].equalsIgnoreCase("clear")) {
                Main.effects.remove(player.getUniqueId());
                player.sendMessage(Main.prefix + "Removed trail!");
                return true;
            } else if (Main.registeredTrails.containsKey(args[0])) {
                if (Main.effects.containsKey(player.getUniqueId())) {
                    if (Main.effects.get(player.getUniqueId()).getName().equals(args[0])) {
                        Main.effects.remove(player.getUniqueId());
                        player.sendMessage(Main.prefix + "Disabled " + args[0] + " trail!");
                        return true;
                    }
                }
                Main.effects.put(player.getUniqueId(), Main.registeredTrails.get(args[0]));
                player.sendMessage(Main.prefix + "Activated " + args[0] + " trail");
                return true;
            } else {
                return true;
            }
        } else {
            sender.sendMessage(Main.prefix + "Sender must be a player!");
            return true;
        }
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        if (args.length == 1) {
            if (Main.registeredTrails.size() == 0) {
                return null;
            }
            List<String> l = super.toList(Main.registeredTrails.keySet());
            l.add("clear");
            return filterStartsWith(l, args[0]);
        } else {
            return null;
        }
    }


}
