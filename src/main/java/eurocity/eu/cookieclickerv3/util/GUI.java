package eurocity.eu.cookieclickerv3.util;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GUI {
    private int cookieCount = 0;
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
        List<String> lore = new ArrayList<>();
        lore.add("§3Cookies: §6" + CookieManager.getCookie(player));
        lore.add("§3CPC: §6" + CookieManager.getCPC(player));
        lore.add("§3Ranking: §6" + CookieManager.getRank(player));
        headMeta.setLore(lore);
        head.setItemMeta(headMeta);
        this.mainInv.setItem(0, head);

        // Clicker
        ItemStack cookieMenu = new ItemStack(Material.COCOA_BEANS);
        ItemMeta cookieMenuMeta = cookieMenu.getItemMeta();
        assert cookieMenuMeta != null;
        cookieMenuMeta.setDisplayName("§6Clicker");
        cookieMenu.setItemMeta(cookieMenuMeta);
        this.mainInv.setItem(9, cookieMenu);

        // Shop
        ItemStack shop = new ItemStack(Material.GOLD_INGOT);
        ItemMeta shopMeta = shop.getItemMeta();
        assert shopMeta != null;
        shopMeta.setDisplayName("§6Shop");
        shop.setItemMeta(shopMeta);
        this.mainInv.setItem(18, shop);

        if (page == 1) {

            // AutoClick Detection
            ItemStack acDetection = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
            ItemMeta acDetectionMeta = placeholder.getItemMeta();
            assert acDetectionMeta != null;
            acDetectionMeta.setDisplayName(" ");
            acDetection.setItemMeta(placeholderMeta);

            for (int i : new int[]{2, 3, 4, 5, 6, 7, 8, 11, 12, 13, 14, 15, 16, 17, 20, 21, 22, 23, 24, 25, 26, 29, 30, 31, 32, 33, 34, 35, 38, 39, 40, 41, 42, 43, 44, 47, 48, 49, 50, 51, 52, 53}) {
                this.mainInv.setItem(i, acDetection);
            }

            // Cookie
            ArrayList<Object> list = new ArrayList<>();
            for (int i : new int[]{2, 3, 4, 5, 6, 7, 8, 11, 12, 13, 14, 15, 16, 17, 20, 21, 22, 23, 24, 25, 26, 29, 30, 31, 32, 33, 34, 35, 38, 39, 40, 41, 42, 43, 44, 47, 48, 49, 50, 51, 52, 53}) {
                list.add(i);
            }

            ItemStack cookie = new ItemStack(Material.COOKIE);
            ItemMeta cookieMeta = cookie.getItemMeta();
            assert cookieMeta != null;
            cookieMeta.setDisplayName("§6Cookie");
            List<String> lore1 = new ArrayList<>();
            if (this.cookieCount <= 1) {
                this.cookieCount = getRandomNumber(2, 5);
                this.random = getRandomNumber(0, 53);
                while (!list.contains(this.random)) {
                    this.random = getRandomNumber(0, 53);
                }
            } else {
                this.cookieCount = this.cookieCount - 1;
            }
            lore1.add("§3Amount: §6" + this.cookieCount);
            cookieMeta.setLore(lore1);
            cookie.setItemMeta(cookieMeta);
            this.mainInv.setItem(this.random, cookie);

        } else if (page == 2) {
            // Upgrade 1
            ItemStack upgrade1 = new ItemStack(Material.OAK_LOG);
            ItemMeta upgrade1Meta = upgrade1.getItemMeta();
            assert upgrade1Meta != null;
            upgrade1Meta.setDisplayName("§6Wood Upgrade");
            List<String> upgrade1Lore = new ArrayList<>();
            upgrade1Lore.add("§3Price: §6" + CookieManager.getUpgrade(1, player));
            upgrade1Lore.add("§3CPC: §6+0.1");
            upgrade1Lore.add("§3Balance: §6" + CookieManager.getCookie(player));
            upgrade1Meta.setLore(upgrade1Lore);
            upgrade1.setItemMeta(upgrade1Meta);
            this.mainInv.setItem(2, upgrade1);

            // Upgrade 2
            ItemStack upgrade2 = new ItemStack(Material.STONE);
            ItemMeta upgrade2Meta = upgrade2.getItemMeta();
            assert upgrade2Meta != null;
            upgrade2Meta.setDisplayName("§6Stone Upgrade");
            List<String> upgrade2Lore = new ArrayList<>();
            upgrade2Lore.add("§3Price: §6" + CookieManager.getUpgrade(2, player));
            upgrade2Lore.add("§3CPC: §6+0.3");
            upgrade2Lore.add("§3Balance: §6" + CookieManager.getCookie(player));
            upgrade2Meta.setLore(upgrade2Lore);
            upgrade2.setItemMeta(upgrade2Meta);
            this.mainInv.setItem(3, upgrade2);

            // Upgrade 3
            ItemStack upgrade3 = new ItemStack(Material.COAL);
            ItemMeta upgrade3Meta = upgrade3.getItemMeta();
            assert upgrade3Meta != null;
            upgrade3Meta.setDisplayName("§6Coal Upgrade");
            List<String> upgrade3Lore = new ArrayList<>();
            upgrade3Lore.add("§3Price: §6" + CookieManager.getUpgrade(3, player));
            upgrade3Lore.add("§3CPC: §6+1.0");
            upgrade3Lore.add("§3Balance: §6" + CookieManager.getCookie(player));
            upgrade3Meta.setLore(upgrade3Lore);
            upgrade3.setItemMeta(upgrade3Meta);
            this.mainInv.setItem(4, upgrade3);

            // Upgrade 4
            ItemStack upgrade4 = new ItemStack(Material.IRON_INGOT);
            ItemMeta upgrade4Meta = upgrade4.getItemMeta();
            assert upgrade4Meta != null;
            upgrade4Meta.setDisplayName("§6Iron Upgrade");
            List<String> upgrade4Lore = new ArrayList<>();
            upgrade4Lore.add("§3Price: §6" + CookieManager.getUpgrade(4, player));
            upgrade4Lore.add("§3CPC: §6+3.5");
            upgrade4Lore.add("§3Balance: §6" + CookieManager.getCookie(player));
            upgrade4Meta.setLore(upgrade4Lore);
            upgrade4.setItemMeta(upgrade4Meta);
            this.mainInv.setItem(5, upgrade4);

            // Upgrade 5
            ItemStack upgrade5 = new ItemStack(Material.LAPIS_LAZULI);
            ItemMeta upgrade5Meta = upgrade5.getItemMeta();
            assert upgrade5Meta != null;
            upgrade5Meta.setDisplayName("§6Lapis Upgrade");
            List<String> upgrade5Lore = new ArrayList<>();
            upgrade5Lore.add("§3Price: §6" + CookieManager.getUpgrade(5, player));
            upgrade5Lore.add("§3CPC: §6+7.0");
            upgrade5Lore.add("§3Balance: §6" + CookieManager.getCookie(player));
            upgrade5Meta.setLore(upgrade5Lore);
            upgrade5.setItemMeta(upgrade5Meta);
            this.mainInv.setItem(6, upgrade5);

            // Upgrade 6
            ItemStack upgrade6 = new ItemStack(Material.REDSTONE);
            ItemMeta upgrade6Meta = upgrade6.getItemMeta();
            assert upgrade6Meta != null;
            upgrade6Meta.setDisplayName("§6Redstone Upgrade");
            List<String> upgrade6Lore = new ArrayList<>();
            upgrade6Lore.add("§3Price: §6" + CookieManager.getUpgrade(6, player));
            upgrade6Lore.add("§3CPC: §6+10.0");
            upgrade6Lore.add("§3Balance: §6" + CookieManager.getCookie(player));
            upgrade6Meta.setLore(upgrade6Lore);
            upgrade6.setItemMeta(upgrade6Meta);
            this.mainInv.setItem(7, upgrade6);

            // Upgrade 7
            ItemStack upgrade7 = new ItemStack(Material.GOLD_INGOT);
            ItemMeta upgrade7Meta = upgrade7.getItemMeta();
            assert upgrade7Meta != null;
            upgrade7Meta.setDisplayName("§6Gold Upgrade");
            List<String> upgrade7Lore = new ArrayList<>();
            upgrade7Lore.add("§3Price: §6" + CookieManager.getUpgrade(7, player));
            upgrade7Lore.add("§3CPC: §6+12.5");
            upgrade7Lore.add("§3Balance: §6" + CookieManager.getCookie(player));
            upgrade7Meta.setLore(upgrade7Lore);
            upgrade7.setItemMeta(upgrade7Meta);
            this.mainInv.setItem(8, upgrade7);
        }

        ItemStack cookieBoots = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta bootsMeta = (LeatherArmorMeta) cookieBoots.getItemMeta();
        assert bootsMeta != null;
        bootsMeta.setDisplayName("§6Cookie Boots");
        bootsMeta.setColor(Color.ORANGE);
        cookieBoots.setItemMeta(bootsMeta);
        this.mainInv.setItem(45, cookieBoots);

        player.openInventory(this.mainInv);
    }

    public void updateGui(Player player) {
        ItemStack cookie = new ItemStack(Material.COOKIE);
        ItemMeta cookieMeta = cookie.getItemMeta();
        assert cookieMeta != null;
        cookieMeta.setDisplayName("§6Cookie");
        List<String> lore1 = new ArrayList<>();

        this.cookieCount = this.cookieCount - 1;
        lore1.add("§3Amount: §6" + this.cookieCount);
        cookieMeta.setLore(lore1);
        cookie.setItemMeta(cookieMeta);
        this.mainInv.setItem(this.random, cookie);

        player.updateInventory();
    }
}
