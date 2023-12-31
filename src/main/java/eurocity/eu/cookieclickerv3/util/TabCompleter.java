package eurocity.eu.cookieclickerv3.util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TabCompleter implements org.bukkit.command.TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        Player player = (Player) sender;
        if (args.length == 1) {
            List<String> tab1 = new ArrayList<>();

            if (player.hasPermission("cookieclicker.admin")){
                tab1.add("add");
                tab1.add("take");
                tab1.add("set");
                tab1.add("reset");
            }
            return tab1;
        }

        if (args.length == 2) {
            List<String> tab2 = new ArrayList<>();

            if (player.hasPermission("cookieclicker.admin")){
                for (Player p : Bukkit.getOnlinePlayers()) {
                    tab2.add(p.getName());
                }
            }
            return tab2;
        }
        return null;
    }
}