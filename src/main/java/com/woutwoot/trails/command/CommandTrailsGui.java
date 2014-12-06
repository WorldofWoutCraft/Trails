package com.woutwoot.trails.command;

import com.woutwoot.trails.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created on 9/5/2014
 *
 * @author minecreatr
 */
public class CommandTrailsGui extends AbstractCommand {

    public CommandTrailsGui() {
        super("trails");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender instanceof Player) {
            Main.openTrailsGui((Player) sender);
            return true;
        } else {
            sender.sendMessage(Main.prefix + "Sender must be player!");
            return true;
        }
    }
}
