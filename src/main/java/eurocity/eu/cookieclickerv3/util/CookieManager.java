package eurocity.eu.cookieclickerv3.util;

import eurocity.eu.cookieclickerv3.CookieClickerV3;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CookieManager {

    //=================================================================================================================
    // Cookies
    //=================================================================================================================
    public static void modifyCookie(double keks, Player player) {
        try {
            DatabaseManager.updateUser(player, keks, 0.0, 0, 0.0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static double getCookie(Player player) throws SQLException {
        DatabaseManager.Connect();
        ResultSet resultSet = DatabaseManager.getConnection().createStatement().executeQuery("SELECT * FROM Cookies WHERE UUID = '" + player.getUniqueId() + "'");
        resultSet.next(); // Jump to next entry. First one is always null
        return Double.parseDouble(String.format("%.2f", resultSet.getDouble("cookies")).replace(",", ".")); // Filter out wanted element
    }

    //=================================================================================================================
    // CPC
    //=================================================================================================================
    public static void modifyCPC(double cpc, Player player) {
        try {
            DatabaseManager.updateUser(player, 0.0, cpc, 0, 0.0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static double getCPC(Player player) throws SQLException {
        DatabaseManager.Connect();
        ResultSet resultSet = DatabaseManager.getConnection().createStatement().executeQuery("SELECT * FROM Cookies WHERE UUID = '" + player.getUniqueId() + "'"); // Get every result with given UUID
        resultSet.next(); // Jump to next entry. First one is always null
        return Double.parseDouble(String.format("%.2f", resultSet.getDouble("cpc")).replace(",", ".")); // Filter out wanted element
    }

    public static int getRank(Player player) throws SQLException {
        DatabaseManager.Connect();
        String playerUUID = player.getUniqueId().toString();

        String getPlayerCookiesQuery = "SELECT cookies FROM Cookies WHERE UUID = '" + playerUUID + "'";
        ResultSet playerResultSet = DatabaseManager.getConnection().createStatement().executeQuery(getPlayerCookiesQuery);

        if (!playerResultSet.next()) {

            return -1;
        }

        double playerCookies = playerResultSet.getDouble("cookies");
        String rankQuery = "SELECT COUNT(*) + 1 AS rank FROM Cookies WHERE cookies > " + playerCookies;
        ResultSet rankResultSet = DatabaseManager.getConnection().createStatement().executeQuery(rankQuery);

        rankResultSet.next();
        int rank = rankResultSet.getInt("rank");

        return rank;
    }


    //=================================================================================================================
    // CPS
    //=================================================================================================================
    public static void modifyCPS(double cps, Player player) {
        try {
            DatabaseManager.updateUser(player, 0.0, 0.0, 0, cps, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0); // Add/Sett values in Database
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static double getCPS(Player player) throws SQLException {
        DatabaseManager.Connect();
        ResultSet resultSet = DatabaseManager.getConnection().createStatement().executeQuery("SELECT * FROM Cookies WHERE UUID = '" + player.getUniqueId() + "'");
        resultSet.next();
        return Double.parseDouble(String.format("%.2f", resultSet.getDouble("cps")).replace(",", "."));
    }

    //=================================================================================================================
    // GoldenCookies
    //=================================================================================================================
    public static void modifyGoldenCookies(double cps, Player player) {
        try {
            DatabaseManager.updateUser(player, 0.0, 0.0, 0, cps, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static double getGoldenCookies(Player player) throws SQLException {
        DatabaseManager.Connect();
        ResultSet resultSet = DatabaseManager.getConnection().createStatement().executeQuery("SELECT * FROM Cookies WHERE UUID = '" + player.getUniqueId() + "'"); // Get every result with given UUID
        resultSet.next(); // Jump to next entry. First one is always null
        return Double.parseDouble(String.format("%.2f", resultSet.getDouble("goldenCookies")).replace(",", ".")); // Filter out wanted element
    }

    //=================================================================================================================
    // Upgrades - 1 bis 10
    //=================================================================================================================
    public static void modifyUpgrade(int upgrade, double value, Player player) {
        try {
            switch (upgrade) {
                case 1:
                    DatabaseManager.updateUser(player, 0.0, 0.0, 0, 0.0, value, 0, 0, 0, 0, 0, 0, 0, 0, 0); // Add/Sett values in Database
                    break;
                case 2:
                    DatabaseManager.updateUser(player, 0.0, 0.0, 0, 0.0, 0, value, 0, 0, 0, 0, 0, 0, 0, 0); // Add/Sett values in Database
                    break;
                case 3:
                    DatabaseManager.updateUser(player, 0.0, 0.0, 0, 0.0, 0, 0, value, 0, 0, 0, 0, 0, 0, 0); // Add/Sett values in Database
                    break;
                case 4:
                    DatabaseManager.updateUser(player, 0.0, 0.0, 0, 0.0, 0, 0, 0, value, 0, 0, 0, 0, 0, 0); // Add/Sett values in Database
                    break;
                case 5:
                    DatabaseManager.updateUser(player, 0.0, 0.0, 0, 0.0, 0, 0, 0, 0, value, 0, 0, 0, 0, 0); // Add/Sett values in Database
                    break;
                case 6:
                    DatabaseManager.updateUser(player, 0.0, 0.0, 0, 0.0, 0, 0, 0, 0, 0, value, 0, 0, 0, 0); // Add/Sett values in Database
                    break;
                case 7:
                    DatabaseManager.updateUser(player, 0.0, 0.0, 0, 0.0, 0, 0, 0, 0, 0, 0, value, 0, 0, 0); // Add/Sett values in Database
                    break;
                case 8:
                    DatabaseManager.updateUser(player, 0.0, 0.0, 0, 0.0, 0, 0, 0, 0, 0, 0, 0, value, 0, 0); // Add/Sett values in Database
                    break;
                case 9:
                    DatabaseManager.updateUser(player, 0.0, 0.0, 0, 0.0, 0, 0, 0, 0, 0, 0, 0, 0, value, 0); // Add/Sett values in Database
                    break;
                case 10:
                    DatabaseManager.updateUser(player, 0.0, 0.0, 0, 0.0, 0, 0, 0, 0, 0, 0, 0, 0, 0, value); // Add/Sett values in Database
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static double getUpgrade(int upgrade, Player player) throws SQLException {
        DatabaseManager.Connect();
        ResultSet resultSet = DatabaseManager.getConnection().createStatement().executeQuery("SELECT * FROM Cookies WHERE UUID = '" + player.getUniqueId() + "'"); // Get every result with given UUID
        resultSet.next();
        return Double.parseDouble(String.format("%.2f", resultSet.getDouble("upgrade" + upgrade)).replace(",", ".")); // Filter out wanted element
    }

}



