package com.woutwoot.trails;

import com.woutwoot.trails.command.CommandLoader;
import com.woutwoot.trails.command.CommandTrails;
import com.woutwoot.trails.command.CommandTrailsGui;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created on 9/2/2014
 */
public class Main extends JavaPlugin implements Listener {

    public static final String prefix = "[" + ChatColor.RED + "Trails" + ChatColor.RESET + "] " + ChatColor.BLUE;
    public static final String trailInventoryName = ChatColor.AQUA + "Trails";
    public static final String trailsKey = ChatColor.BLUE + "" + ChatColor.ITALIC + "Trail";
    public static final int max_distance = 160;
    public static HashMap<UUID, Trail> effects = new HashMap<UUID, Trail>();
    public static HashMap<String, Trail> registeredTrails = new HashMap<String, Trail>();
    public static HashMap<UUID, Integer> crackedIds = new HashMap<UUID, Integer>();
    public static boolean perTrailPerm;
    Random random = new Random();
    private CommandLoader loader;

    public static int getDistance(Location loc1, Location loc2) {
        return (int) Math.round(Math.sqrt(sqr(loc1.getBlockX() - loc2.getBlockX()) + sqr(loc1.getBlockY() - loc2.getBlockY()) + sqr(loc1.getBlockZ() - loc2.getBlockZ())));
    }

    private static int sqr(int num) {
        return num * num;
    }

    public static void openTrailsGui(Player player) {
        player.sendMessage(prefix + "Opening trails gui...");
        Inventory inv = Bukkit.createInventory(null, 27, trailInventoryName);

        inv.addItem(gti(Trail.star, Material.NETHER_STAR));
        inv.addItem(gti(Trail.spark, Material.FIREWORK));
        inv.addItem(gti(Trail.music, Material.NOTE_BLOCK));
        inv.addItem(gti(Trail.happy, Material.EMERALD));
        inv.addItem(gti(Trail.lava, Material.LAVA_BUCKET));
        inv.addItem(gti(Trail.ash, Material.INK_SACK, (short) 8));
        inv.addItem(gti(Trail.water, Material.WATER_BUCKET));
        inv.addItem(gti(Trail.magic, Material.BREWING_STAND_ITEM));
        inv.addItem(gti(Trail.love, Material.INK_SACK, (short) 1));
        inv.addItem(gti(Trail.dust, Material.REDSTONE));
        inv.addItem(gti(Trail.slime, Material.SLIME_BALL));
        inv.addItem(gti(Trail.ender, Material.EYE_OF_ENDER));
        inv.addItem(gti(Trail.knowledge, Material.ENCHANTMENT_TABLE));
        inv.addItem(gti(Trail.step, Material.GRAVEL));
        inv.addItem(gti(Trail.voidTrail, Material.ENDER_PORTAL));
        inv.addItem(gti(Trail.potion, Material.GLOWSTONE_DUST));
        inv.addItem(gti(Trail.pop, Material.TNT));
        inv.addItem(gti(Trail.splash, Material.WATER_LILY));
        inv.addItem(gti(Trail.smoke, Material.INK_SACK, (short) 7));
        inv.addItem(gti(Trail.snow, Material.SNOW_BALL));
        inv.addItem(gti(Trail.flame, Material.FIRE));
        inv.addItem(gti(Trail.angry, Material.REDSTONE_TORCH_ON));


        player.openInventory(inv);
    }

    //Generate Trail Item
    public static ItemStack gti(Trail trail, Material material) {
        ItemStack stack = new ItemStack(material);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.LIGHT_PURPLE + StringUtils.capitalize(trail.getName()));
        List<String> lore = new ArrayList<String>();
        lore.add(trailsKey);
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return stack;
    }

    //Generate Trail Item
    public static ItemStack gti(Trail trail, Material material, short d) {
        ItemStack stack = new ItemStack(material, 1, d);
        stack.setAmount(0);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.LIGHT_PURPLE + StringUtils.capitalize(trail.getName()));
        List<String> lore = new ArrayList<String>();
        lore.add(trailsKey);
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return stack;
    }

    @Override
    public void onEnable() {
        reloadConfig();
        getServer().getPluginManager().registerEvents(this, this);
        loader = new CommandLoader(this);
        loader.registerCommand(new CommandTrails());
        loader.registerCommand(new CommandTrailsGui());
        Trail.registerTrails(registeredTrails);
        if (!getConfigFile().exists()) {
            getConfig().set("perTrailPerm", false);
            saveConfig();
        }
        if (getConfig().contains("perTrailPerm")) {
            perTrailPerm = getConfig().getBoolean("perTrailPerm");
        } else {
            perTrailPerm = false;
        }
    }

    public File getConfigFile() {
        try {
            Field field = JavaPlugin.class.getDeclaredField("configFile");
            field.setAccessible(true);
            return (File) field.get(this);
        } catch (Exception exception) {
            exception.printStackTrace();
            getLogger().severe("Error getting config file!");
            return null;
        }
    }

    @Override
    public void onDisable() {
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onMove(PlayerMoveEvent event) {
        if (effects.containsKey(event.getPlayer().getUniqueId())) {
            Player player = event.getPlayer();
            Location loc = player.getLocation();
            for (Player curPlayer : getServer().getOnlinePlayers()) {
                if (player.getWorld().equals(curPlayer.getWorld())) {
                    if (getDistance(loc, curPlayer.getLocation()) <= max_distance) {
                        if (effects.get(player.getUniqueId()) == Trail.crack) {
                            /*if (crackedIds.containsKey(player.getUniqueId())) {
                                if (crackedIds.get(player.getUniqueId()) != 0) {
                                    ParticleEffect.sendBlockBreakToPlayer(curPlayer, loc, 0, 0, 0, random.nextInt(16), 5, crackedIds.get(player.getUniqueId()), 0);
                                }
                            }*/
                            //TODO
                        } else {
                            ParticleEffect effect = new ParticleEffect(effects.get(player.getUniqueId()).getEffect(), 0, 5, 0.2);
                            effect.sendToLocation(loc);
                        }
                    }
                }
            }
//            while (players.hasNext()){
//                Player curPlayer = players.next();
//                if (curPlayer.getWorld().getName().equals(player.getWorld().getName())){
//                    if (effects.get(player.getUniqueId())==Trail.crack){
//                        if (crackedIds.containsKey(player.getUniqueId())){
//                            if (crackedIds.get(player.getUniqueId())!=0){
//                                ParticleEffects.sendBlockBreakToPlayer(curPlayer, loc, 0, 0, 0, random.nextInt(16), 5, crackedIds.get(player.getUniqueId()));
//                                return;
//                            }
//                        }
//                    }
//                    effects.get(player.getUniqueId()).getEffect().sendToPlayer(curPlayer, loc, 0, 0, 0, random.nextInt(16), 5);
//                    return;
//                }
//            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        effects.remove(event.getPlayer().getUniqueId());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getName().equals(trailInventoryName)) {
            if (event.getCurrentItem().getItemMeta() != null) {
                if (event.getCurrentItem().getItemMeta().getLore().contains(trailsKey)) {
                    HumanEntity entity = event.getWhoClicked();
                    if (entity instanceof Player) {
                        Bukkit.getServer().dispatchCommand((Player) entity, "trail " + ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName().toLowerCase()));
                        event.getWhoClicked().closeInventory();
                    }
                }
            }
            event.setCancelled(true);
        }
    }
}
