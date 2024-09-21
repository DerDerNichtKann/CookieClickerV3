package eurocity.eu.cookieclickerv3;

import eurocity.eu.cookieclickerv3.util.DatabaseManager;
import eurocity.eu.cookieclickerv3.util.GUI;
import eurocity.eu.cookieclickerv3.util.GUIListener;
import eurocity.eu.cookieclickerv3.util.TabCompleter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;


public final class CookieClickerV3 extends JavaPlugin implements Listener {
    private static final HashMap<Player, GUI> playerGuiHashMap = new HashMap<>();
    PluginDescriptionFile pdf = this.getDescription();
    public DatabaseManager database = new DatabaseManager(this);
    private DatabaseManager databaseManager;
    private final Map<Player, BukkitRunnable> cookieSpawnTasks = new HashMap<>();
    public static CookieClickerV3 instance;
    private final Plugin plugin;
    private final NamespacedKey droppableKey;

    public CookieClickerV3() {
        this.plugin = this;
        this.droppableKey = new NamespacedKey(plugin, "droppable");
    }


    @Override
    public void onEnable() {
        instance = this;
        databaseManager = new DatabaseManager(this);
        Objects.requireNonNull(getCommand("cookieclicker")).setExecutor(new CookieCMD(this));
        getCommand("cookieclicker").setTabCompleter(new TabCompleter());
        this.getCommand("placeTopRanking").setExecutor(new CommandTopRanking(databaseManager));
        Bukkit.getPluginManager().registerEvents(new GUIListener(this), this);
        databaseManager.displayTop5Ranking();
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        try {
            DatabaseManager.Connect();
            database.createTable();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(getConfig().getString("prefix") + "§cMySQL-Error - Bitte checke die MySQL Daten!");
            throw new RuntimeException(e);
        }
        Bukkit.getConsoleSender().sendMessage(getConfig().getString("prefix") + "==========================================");
        Bukkit.getConsoleSender().sendMessage(getConfig().getString("prefix") + "| §6Database: " + DatabaseManager.isConnected());
        Bukkit.getConsoleSender().sendMessage(getConfig().getString("prefix") + "------------------------------------------");
        Bukkit.getConsoleSender().sendMessage(getConfig().getString("prefix") + "| §6CookieClicker " + pdf.getVersion() + " von DerDerNichtsKann");
        Bukkit.getConsoleSender().sendMessage(getConfig().getString("prefix") + "| §7Has been §2Enabled");
        Bukkit.getConsoleSender().sendMessage(getConfig().getString("prefix") + "==========================================");
    }


    public void checkPlayersWithCookieBoots() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (isWearingCookieBoots(player)) {
                startCookieSpawning(player);
            } else {
                stopCookieSpawning(player);
            }
        }
    }


    private void startCookieSpawning(Player player) {
        if (cookieSpawnTasks.containsKey(player)) {
            return;
        }

        BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isOnline() || !isWearingCookieBoots(player)) {
                    stopCookieSpawning(player);
                    cancel();
                    return;
                }

                ItemStack cookieItem = new ItemStack(Material.COOKIE);
                ItemMeta meta = cookieItem.getItemMeta();
                if (meta != null) {
                    meta.getPersistentDataContainer().set(droppableKey, PersistentDataType.BYTE, (byte) 1);
                    meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "unique"), PersistentDataType.STRING, UUID.randomUUID().toString());
                    cookieItem.setItemMeta(meta);
                }

                Item droppedItem = player.getWorld().dropItem(player.getLocation().add(0, 0.25, 0), cookieItem);
                droppedItem.setPickupDelay(60);
                droppedItem.setGravity(true);
                Vector velocity = new Vector((Math.random() - 0.5) * 0.5, 0.25, (Math.random() - 0.5) * 0.5);
                droppedItem.setVelocity(velocity);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        droppedItem.remove();
                    }
                }.runTaskLater(plugin, 60L);

            }
        };

        task.runTaskTimer(plugin, 0L, 1L);
        cookieSpawnTasks.put(player, task);
    }

    private void stopCookieSpawning(Player player) {
        BukkitRunnable task = cookieSpawnTasks.remove(player);
        if (task != null) {
            task.cancel();
        }
    }


    private boolean isWearingCookieBoots(Player player) {
        ItemStack boots = player.getInventory().getBoots();
        if (boots != null && boots.getType() == Material.LEATHER_BOOTS && boots.hasItemMeta()) {
            ItemMeta meta = boots.getItemMeta();
            return meta != null && meta.getDisplayName().equals(ChatColor.GOLD + "Cookie Boots");
        }
        return false;
    }


    @Override
    public void onDisable() {
        DatabaseManager.Disconnect();
        Bukkit.getConsoleSender().sendMessage(getConfig().getString("prefix") + "==========================================");
        Bukkit.getConsoleSender().sendMessage(getConfig().getString("prefix") + "| §6CookieClicker " + pdf.getVersion() + " von DerDerNichtsKann");
        Bukkit.getConsoleSender().sendMessage(getConfig().getString("prefix") + "| §7Has been §cDisabled");
        Bukkit.getConsoleSender().sendMessage(getConfig().getString("prefix") + "==========================================");
    }
    public static HashMap<Player, GUI> getPlayerGuiHashMap() {
        return playerGuiHashMap;
    }
}