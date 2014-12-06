package com.woutwoot.trails.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

/**
 * Created on 10/26/2014
 *
 * @author minecreatr
 */
public class CommandLoader {

    public JavaPlugin plugin;
    private CommandMap cmap;

    public CommandLoader(JavaPlugin p) {
        this.plugin = p;
        cmap = (CommandMap) getValue(Bukkit.getServer(), "commandMap");
    }

    public static Object getValue(Object obj, String name) {
        try {
            Field f = obj.getClass().getDeclaredField(name);
            f.setAccessible(true);
            return f.get(obj);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public void registerCommand(AbstractCommand command) {
        cmap.register("", command);
    }
}