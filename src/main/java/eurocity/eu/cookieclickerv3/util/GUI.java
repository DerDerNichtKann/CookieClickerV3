package eurocity.eu.cookieclickerv3.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GUI {
    private int keksCount = 0;
    private int random = 0;
    public Inventory mainInv;

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public void mainGui(Player player, int page) throws SQLException {
        if (page == 2) {
            this.mainInv = Bukkit.createInventory(player, 54, "§6CookieClicker - Upgrade");
        } else {
            this.mainInv = Bukkit.createInventory(player, 54, "§6CookieClicker");
        }
        // Placeholder
        ItemStack placeholder = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta placeholderMeta = placeholder.getItemMeta();
        assert placeholderMeta != null;
        placeholderMeta.setDisplayName(" ");
        placeholder.setItemMeta(placeholderMeta);

        for (int i : new int[]{1, 10, 19, 28, 37, 46}) {
            this.mainInv.setItem(i, placeholder);
        }

        // Player Head
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        assert headMeta != null;
        headMeta.setDisplayName(player.getName());
        headMeta.setOwningPlayer(player);
        List<String> lore = new ArrayList<>(); //create a List<String> for the lore
        lore.add("§3Kekse: §6" + CookieManager.getCookie(player));
        lore.add("§3CPC: §6" + CookieManager.getCPC(player));
        headMeta.setLore(lore);
        head.setItemMeta(headMeta);
        this.mainInv.setItem(0, head);

        // Klicker
        ItemStack keksMenu = new ItemStack(Material.COOKIE);
        ItemMeta keksMenuMeta = keksMenu.getItemMeta();
        assert keksMenuMeta != null;
        keksMenuMeta.setDisplayName("§6Klicker");
        keksMenu.setItemMeta(keksMenuMeta);
        this.mainInv.setItem(9, keksMenu);

        // Shop
        ItemStack shop = new ItemStack(Material.GOLD_INGOT);
        ItemMeta shopMeta = shop.getItemMeta();
        assert shopMeta != null;
        shopMeta.setDisplayName("§6Shop");
        shop.setItemMeta(shopMeta);
        this.mainInv.setItem(18, shop);


        if (page == 1) {

            // AutoKlick Detection
            ItemStack akDetection = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
            ItemMeta akDetectionMeta = placeholder.getItemMeta();
            assert akDetectionMeta != null;
            akDetectionMeta.setDisplayName(" ");
            akDetection.setItemMeta(placeholderMeta);

            for (int i : new int[]{2, 3, 4, 5, 6, 7, 8, 11, 12, 13, 14, 15, 16, 17, 20, 21, 22, 23, 24, 25, 26, 29, 30, 31, 32, 33, 34, 35, 38, 39, 40, 41, 42, 43, 44, 47, 48, 49, 50, 51, 52, 53}) {
                this.mainInv.setItem(i, akDetection);
            }

            // Cookie
            ArrayList<Object> list = new ArrayList<>();
            for (int i : new int[]{2, 3, 4, 5, 6, 7, 8, 11, 12, 13, 14, 15, 16, 17, 20, 21, 22, 23, 24, 25, 26, 29, 30, 31, 32, 33, 34, 35, 38, 39, 40, 41, 42, 43, 44, 47, 48, 49, 50, 51, 52, 53}) {
                list.add(i);
            }

            ItemStack keks = new ItemStack(Material.COOKIE);
            ItemMeta keksMeta = keks.getItemMeta();
            assert keksMeta != null;
            keksMeta.setDisplayName("§6Cookie");
            List<String> lore1 = new ArrayList<>(); //create a List<String> for the lore
            if (this.keksCount <= 1) {
                this.keksCount = getRandomNumber(2, 5);
                this.random = getRandomNumber(0, 53);
                while (!list.contains(this.random)) {
                    this.random = getRandomNumber(0, 53);
                }
            } else {
                this.keksCount = this.keksCount - 1;
            }
            lore1.add("§3Anzahl: §6" + this.keksCount);
            keksMeta.setLore(lore1);
            keks.setItemMeta(keksMeta);
            this.mainInv.setItem(this.random, keks);

        } else if (page == 2) {
            // 1. Upgrade
            ItemStack upgrade1 = new ItemStack(Material.COAL);
            ItemMeta upgrade1Meta = upgrade1.getItemMeta();
            assert upgrade1Meta != null;
            upgrade1Meta.setDisplayName("§6Upgrade 1");
            List<String> upgrade1Lore = new ArrayList<>(); //create a List<String> for the lore
            upgrade1Lore.add("§3Preis: §6" + CookieManager.getUpgrade(1, player));
            upgrade1Lore.add("§3CPC: §6+0.1");
            upgrade1Lore.add("§3Kontostand: §6" + CookieManager.getCookie(player));
            upgrade1Meta.setLore(upgrade1Lore);
            upgrade1.setItemMeta(upgrade1Meta);
            this.mainInv.setItem(2, upgrade1);

            // 2. Upgrade
            ItemStack upgrade2 = new ItemStack(Material.IRON_INGOT);
            ItemMeta upgrade2Meta = upgrade2.getItemMeta();
            assert upgrade2Meta != null;
            upgrade2Meta.setDisplayName("§6Upgrade 2");
            List<String> upgrade2Lore = new ArrayList<>(); //create a List<String> for the lore
            upgrade2Lore.add("§3Preis: §6" + CookieManager.getUpgrade(2, player));
            upgrade2Lore.add("§3CPC: §6+0.3");
            upgrade2Lore.add("§3Kontostand: §6" + CookieManager.getCookie(player));
            upgrade2Meta.setLore(upgrade2Lore);
            upgrade2.setItemMeta(upgrade2Meta);
            this.mainInv.setItem(3, upgrade2);

            // 3. Upgrade
            ItemStack upgrade3 = new ItemStack(Material.GOLD_INGOT);
            ItemMeta upgrade3Meta = upgrade3.getItemMeta();
            assert upgrade3Meta != null;
            upgrade3Meta.setDisplayName("§6Upgrade 3");
            List<String> upgrade3Lore = new ArrayList<>(); //create a List<String> for the lore
            upgrade3Lore.add("§3Preis: §6" + CookieManager.getUpgrade(3, player));
            upgrade3Lore.add("§3CPC: §6+1.0");
            upgrade3Lore.add("§3Kontostand: §6" + CookieManager.getCookie(player));
            upgrade3Meta.setLore(upgrade3Lore);
            upgrade3.setItemMeta(upgrade3Meta);
            this.mainInv.setItem(4, upgrade3);

            // 4. Upgrade
            ItemStack upgrade4 = new ItemStack(Material.DIAMOND);
            ItemMeta upgrade4Meta = upgrade4.getItemMeta();
            assert upgrade4Meta != null;
            upgrade4Meta.setDisplayName("§6Upgrade 4");
            List<String> upgrade4Lore = new ArrayList<>(); //create a List<String> for the lore
            upgrade4Lore.add("§3Preis: §6" + CookieManager.getUpgrade(4, player));
            upgrade4Lore.add("§3CPC: §6+3.5");
            upgrade4Lore.add("§3Kontostand: §6" + CookieManager.getCookie(player));
            upgrade4Meta.setLore(upgrade4Lore);
            upgrade4.setItemMeta(upgrade4Meta);
            this.mainInv.setItem(5, upgrade4);

            // 5. Upgrade
            ItemStack upgrade5 = new ItemStack(Material.NETHERITE_INGOT);
            ItemMeta upgrade5Meta = upgrade5.getItemMeta();
            assert upgrade5Meta != null;
            upgrade5Meta.setDisplayName("§6Upgrade 5");
            List<String> upgrade5Lore = new ArrayList<>(); //create a List<String> for the lore
            upgrade5Lore.add("§3Preis: §6" + CookieManager.getUpgrade(5, player));
            upgrade5Lore.add("§3CPC: §6+7.0");
            upgrade5Lore.add("§3Kontostand: §6" + CookieManager.getCookie(player));
            upgrade5Meta.setLore(upgrade5Lore);
            upgrade5.setItemMeta(upgrade5Meta);
            this.mainInv.setItem(6, upgrade5);
        }

        player.openInventory(this.mainInv);
    }

    public void updateGui(Player player) {

        ItemStack keks = new ItemStack(Material.COOKIE);
        ItemMeta keksMeta = keks.getItemMeta();
        assert keksMeta != null;
        keksMeta.setDisplayName("§6Cookie");
        List<String> lore1 = new ArrayList<>();

        this.keksCount = this.keksCount - 1;
        lore1.add("§3Anzahl: §6" + this.keksCount);
        keksMeta.setLore(lore1);
        keks.setItemMeta(keksMeta);
        this.mainInv.setItem(this.random, keks);

        player.updateInventory();
    }
}

