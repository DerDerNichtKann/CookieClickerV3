package eurocity.eu.cookieclickerv3.util;

import eurocity.eu.cookieclickerv3.CookieClickerV3;

import org.bukkit.entity.Player;

import java.sql.*;
import java.util.UUID;

public class DatabaseManager {


    private static CookieClickerV3 main = null;
    public DatabaseManager(CookieClickerV3 main) {
        DatabaseManager.main = main;
    }
    private static Connection connection;

    public void executeUpdate(String cmd) {
        try {
            connection.createStatement().executeUpdate(cmd);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createTable() {
        this.executeUpdate("CREATE TABLE IF NOT EXISTS Cookies (uuid VARCHAR(100), cookies DECIMAL(30, 2), cpc DECIMAL(20, 2), cps DECIMAL(20, 2), goldenCookies DECIMAL(30, 0), upgrade1 DECIMAL(30, 0), upgrade2 DECIMAL(30, 0), upgrade3 DECIMAL(30, 0), upgrade4 DECIMAL(30, 0), upgrade5 DECIMAL(30, 0), upgrade6 DECIMAL(30, 0), upgrade7 DECIMAL(30, 0), upgrade8 DECIMAL(30, 0), upgrade9 DECIMAL(30, 0), upgrade10 DECIMAL(30, 0))");
    }

    public static void Connect() throws SQLException {
        if (isConnected()) {
            return;
        }
        String type = main.getConfig().getString("database.type");
        if (type.equalsIgnoreCase("mysql")){
            String HOST = main.getConfig().getString("database.address");
            int PORT = main.getConfig().getInt("database.port");
            String DATABASE = main.getConfig().getString("database.database");
            String USER = main.getConfig().getString("database.user");
            String PASSWORD = main.getConfig().getString("database.password");

            connection = DriverManager.getConnection("jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE + "?useSSL=false", USER, PASSWORD);
        }

        if (type.equalsIgnoreCase("sqlite")){
            connection = DriverManager.getConnection("jdbc:sqlite:plugins/CookieClicker/CookieClicker.db" );
        }
    }

    public static void updateUser(Player player, double cookies, double cpc, int golden_cookies, double cps, double upgrade1, double upgrade2, double upgrade3, double upgrade4, double upgrade5, double upgrade6, double upgrade7, double upgrade8, double upgrade9, double upgrade10) throws SQLException {
        Connect();

        // Init values
        double cookies_new = 0;
        double cpc_new = 1;
        double cps_new = 0;
        int golden_cookies_new = 0;
        double upgrade1_new = 0;
        double upgrade2_new = 0;
        double upgrade3_new = 0;
        double upgrade4_new = 0;
        double upgrade5_new = 0;
        double upgrade6_new = 0;
        double upgrade7_new = 0;
        double upgrade8_new = 0;
        double upgrade9_new = 0;
        double upgrade10_new = 0;


        try {
            ResultSet resultSet = DatabaseManager.getConnection().createStatement().executeQuery("SELECT * FROM Cookies WHERE UUID = '" + player.getUniqueId() + "'");
            if(resultSet.next()){
                double cookies_old = Double.parseDouble(String.format("%.2f", resultSet.getDouble("cookies")).replace(",", "."));
                double cpc_old = Double.parseDouble(String.format("%.2f", resultSet.getDouble("cpc")).replace(",", "."));
                double cps_old = Double.parseDouble(String.format("%.2f", resultSet.getDouble("cps")).replace(",", "."));
                int golden_cookies_old = Integer.parseInt(String.valueOf(resultSet.getInt("goldenCookies")));
                double upgrade1_old = Double.parseDouble(String.format("%.2f", resultSet.getDouble("upgrade1")).replace(",", "."));
                double upgrade2_old = Double.parseDouble(String.format("%.2f", resultSet.getDouble("upgrade2")).replace(",", "."));
                double upgrade3_old = Double.parseDouble(String.format("%.2f", resultSet.getDouble("upgrade3")).replace(",", "."));
                double upgrade4_old = Double.parseDouble(String.format("%.2f", resultSet.getDouble("upgrade4")).replace(",", "."));
                double upgrade5_old = Double.parseDouble(String.format("%.2f", resultSet.getDouble("upgrade5")).replace(",", "."));
                double upgrade6_old = Double.parseDouble(String.format("%.2f", resultSet.getDouble("upgrade6")).replace(",", "."));
                double upgrade7_old = Double.parseDouble(String.format("%.2f", resultSet.getDouble("upgrade7")).replace(",", "."));
                double upgrade8_old = Double.parseDouble(String.format("%.2f", resultSet.getDouble("upgrade8")).replace(",", "."));
                double upgrade9_old = Double.parseDouble(String.format("%.2f", resultSet.getDouble("upgrade9")).replace(",", "."));
                double upgrade10_old = Double.parseDouble(String.format("%.2f", resultSet.getDouble("upgrade10")).replace(",", "."));

                cookies_new = cookies + cookies_old;
                cpc_new = cpc + cpc_old;
                cps_new = cps + cps_old;
                golden_cookies_new = golden_cookies + golden_cookies_old;
                upgrade1_new = upgrade1 + upgrade1_old;
                upgrade2_new = upgrade2 + upgrade2_old;
                upgrade3_new = upgrade3 + upgrade3_old;
                upgrade4_new = upgrade4 + upgrade4_old;
                upgrade5_new = upgrade5 + upgrade5_old;
                upgrade6_new = upgrade6 + upgrade6_old;
                upgrade7_new = upgrade7 + upgrade7_old;
                upgrade8_new = upgrade8 + upgrade8_old;
                upgrade9_new = upgrade9 + upgrade9_old;
                upgrade10_new = upgrade10 + upgrade10_old;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        DatabaseManager.getConnection().createStatement().execute("DELETE FROM Cookies WHERE uuid = '" + player.getUniqueId() + "'");

        PreparedStatement ps = getConnection().prepareStatement("INSERT INTO Cookies (uuid,cookies,cpc,cps,goldenCookies,upgrade1,upgrade2,upgrade3,upgrade4,upgrade5,upgrade6,upgrade7,upgrade8,upgrade9,upgrade10) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        UUID uuid = player.getUniqueId();
        ps.setString(1, String.valueOf(uuid));
        ps.setDouble(2, cookies_new);
        ps.setDouble(3, cpc_new);
        ps.setDouble(4, cps_new);
        ps.setInt(5, golden_cookies_new);
        ps.setDouble(6, upgrade1_new);
        ps.setDouble(7, upgrade2_new);
        ps.setDouble(8, upgrade3_new);
        ps.setDouble(9, upgrade4_new);
        ps.setDouble(10, upgrade5_new);
        ps.setDouble(11, upgrade6_new);
        ps.setDouble(12, upgrade7_new);
        ps.setDouble(13, upgrade8_new);
        ps.setDouble(14, upgrade9_new);
        ps.setDouble(15, upgrade10_new);
        ps.execute();
        ps.close();
    }

    public static Connection getConnection() {return connection;}

    public static boolean isConnected() {
        return connection != null;
    }

    public static void Disconnect() {
        if (isConnected()) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

}