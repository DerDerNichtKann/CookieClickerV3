package eurocity.eu.cookieclickerv3;

import eurocity.eu.cookieclickerv3.util.DatabaseManager;
import eurocity.eu.cookieclickerv3.util.GUI;
import eurocity.eu.cookieclickerv3.util.GUIListener;
import eurocity.eu.cookieclickerv3.util.TabCompleter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

public final class CookieClickerV3 extends JavaPlugin {
    private static final HashMap<Player, GUI> playerGuiHashMap = new HashMap<>();
    PluginDescriptionFile pdf = this.getDescription();
    public DatabaseManager database = new DatabaseManager(this);

    @Override
    public void onEnable() {

        //jgfdsfdsdgdsgf

        Objects.requireNonNull(getCommand("cookieclicker")).setExecutor(new CookieCMD(this));
        getCommand("cookieclicker").setTabCompleter(new TabCompleter());
        Bukkit.getPluginManager().registerEvents(new GUIListener(), this);

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

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        DatabaseManager.Disconnect();
        Bukkit.getConsoleSender().sendMessage(getConfig().getString("prefix") + "==========================================");
        Bukkit.getConsoleSender().sendMessage(getConfig().getString("prefix") + "| §6CookieClicker " + pdf.getVersion() + " §von DerDerNichtsKann");
        Bukkit.getConsoleSender().sendMessage(getConfig().getString("prefix") + "| §7Has been §cDisabled");
        Bukkit.getConsoleSender().sendMessage(getConfig().getString("prefix") + "==========================================");
    }
    public static HashMap<Player, GUI> getPlayerGuiHashMap() {
        return playerGuiHashMap;
    }
}