package eurocity.eu.cookieclickerv3;

import eurocity.eu.cookieclickerv3.util.DatabaseManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandTopRanking implements CommandExecutor {
    private final DatabaseManager databaseManager;
    public CommandTopRanking(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (!sender.hasPermission("cookie.admin")) {
                sender.sendMessage(ChatColor.RED + "You don't have the permission!");
                return true;
            }
            Player player = (Player) sender;
            Location location = player.getLocation();

            databaseManager.setLeaderboardLocation(location);
            databaseManager.displayTop5Ranking();

            player.sendMessage(ChatColor.GREEN + "Top 5 Ranking was placed!");
            return true;
        } else {
            return false;
        }
    }
}