package com.woutwoot.trails.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created on 10/26/2014
 *
 * @author minecreatr
 */
public abstract class AbstractCommand extends Command implements CommandExecutor {

    private boolean hasPermission;
    private String permission;
    private String noPermMessage = ChatColor.RED + "You do not have permission to run this command";
    private boolean playerComplete = false;

    protected AbstractCommand(String name) {
        super(name);
        hasPermission = false;
    }

    protected AbstractCommand(String name, String perm) {
        super(name);
        this.permission = perm;
        this.hasPermission = true;
    }

    protected AbstractCommand(String name, String perm, String message) {
        super(name);
        this.permission = perm;
        this.hasPermission = true;
        this.noPermMessage = message;
    }


    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!hasPermission) {
            return this.onCommand(sender, this, commandLabel, args);
        } else if (sender.hasPermission(permission)) {
            return this.onCommand(sender, this, commandLabel, args);
        } else {
            sender.sendMessage(noPermMessage);
            return true;
        }
    }

    protected void setPlayerComplete(boolean p) {
        this.playerComplete = p;
    }

    public List toList(Collection collection) {
        if (collection instanceof List) {
            return (List) collection;
        } else {
            return new ArrayList(collection);
        }
    }
}