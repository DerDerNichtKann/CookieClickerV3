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
            Player player = (Player) sender;
            Location location = player.getLocation();

            databaseManager.setLeaderboardLocation(location);
            databaseManager.displayTop5Ranking();

            player.sendMessage(ChatColor.GREEN + "Top 5 Ranking wurde an deiner aktuellen Position platziert und wird alle 10 Sekunden aktualisiert!");
            return true;
        } else {
            sender.sendMessage(ChatColor.RED + "Dieser Befehl kann nur von einem Spieler ausgeführt werden.");
            return false;
        }
    }
}