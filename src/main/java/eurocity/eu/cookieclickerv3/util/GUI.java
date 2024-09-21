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
        lore.add("§3Ranking: §6" + CookieManager.getRank(player));
        headMeta.setLore(lore);
        head.setItemMeta(headMeta);
        this.mainInv.setItem(0, head);

        // Klicker
        ItemStack keksMenu = new ItemStack(Material.COCOA_BEANS);
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
            List<String> lore1 = new ArrayList<>();
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
            ItemStack upgrade1 = new ItemStack(Material.OAK_LOG);
            ItemMeta upgrade1Meta = upgrade1.getItemMeta();
            assert upgrade1Meta != null;
            upgrade1Meta.setDisplayName("§6Holz Upgrade");
            List<String> upgrade1Lore = new ArrayList<>(); //create a List<String> for the lore
            upgrade1Lore.add("§3Preis: §6" + CookieManager.getUpgrade(1, player));
            upgrade1Lore.add("§3CPC: §6+0.1");
            upgrade1Lore.add("§3Kontostand: §6" + CookieManager.getCookie(player));
            upgrade1Meta.setLore(upgrade1Lore);
            upgrade1.setItemMeta(upgrade1Meta);
            this.mainInv.setItem(2, upgrade1);

            // 2. Upgrade
            ItemStack upgrade2 = new ItemStack(Material.STONE);
            ItemMeta upgrade2Meta = upgrade2.getItemMeta();
            assert upgrade2Meta != null;
            upgrade2Meta.setDisplayName("§6Stein Upgrade");
            List<String> upgrade2Lore = new ArrayList<>(); //create a List<String> for the lore
            upgrade2Lore.add("§3Preis: §6" + CookieManager.getUpgrade(2, player));
            upgrade2Lore.add("§3CPC: §6+0.3");
            upgrade2Lore.add("§3Kontostand: §6" + CookieManager.getCookie(player));
            upgrade2Meta.setLore(upgrade2Lore);
            upgrade2.setItemMeta(upgrade2Meta);
            this.mainInv.setItem(3, upgrade2);

            // 3. Upgrade
            ItemStack upgrade3 = new ItemStack(Material.COAL);
            ItemMeta upgrade3Meta = upgrade3.getItemMeta();
            assert upgrade3Meta != null;
            upgrade3Meta.setDisplayName("§6Kohle Upgrade");
            List<String> upgrade3Lore = new ArrayList<>(); //create a List<String> for the lore
            upgrade3Lore.add("§3Preis: §6" + CookieManager.getUpgrade(3, player));
            upgrade3Lore.add("§3CPC: §6+1.0");
            upgrade3Lore.add("§3Kontostand: §6" + CookieManager.getCookie(player));
            upgrade3Meta.setLore(upgrade3Lore);
            upgrade3.setItemMeta(upgrade3Meta);
            this.mainInv.setItem(4, upgrade3);

            // 4. Upgrade
            ItemStack upgrade4 = new ItemStack(Material.IRON_INGOT);
            ItemMeta upgrade4Meta = upgrade4.getItemMeta();
            assert upgrade4Meta != null;
            upgrade4Meta.setDisplayName("§6Eisen Upgrade");
            List<String> upgrade4Lore = new ArrayList<>(); //create a List<String> for the lore
            upgrade4Lore.add("§3Preis: §6" + CookieManager.getUpgrade(4, player));
            upgrade4Lore.add("§3CPC: §6+3.5");
            upgrade4Lore.add("§3Kontostand: §6" + CookieManager.getCookie(player));
            upgrade4Meta.setLore(upgrade4Lore);
            upgrade4.setItemMeta(upgrade4Meta);
            this.mainInv.setItem(5, upgrade4);

            // 5. Upgrade
            ItemStack upgrade5 = new ItemStack(Material.LAPIS_LAZULI);
            ItemMeta upgrade5Meta = upgrade5.getItemMeta();
            assert upgrade5Meta != null;
            upgrade5Meta.setDisplayName("§6Lapis Upgrade");
            List<String> upgrade5Lore = new ArrayList<>(); //create a List<String> for the lore
            upgrade5Lore.add("§3Preis: §6" + CookieManager.getUpgrade(5, player));
            upgrade5Lore.add("§3CPC: §6+7.0");
            upgrade5Lore.add("§3Kontostand: §6" + CookieManager.getCookie(player));
            upgrade5Meta.setLore(upgrade5Lore);
            upgrade5.setItemMeta(upgrade5Meta);
            this.mainInv.setItem(6, upgrade5);

           // 6. Upgrade
            ItemStack upgrade6 = new ItemStack(Material.REDSTONE);
            ItemMeta upgrade6Meta = upgrade6.getItemMeta();
            assert upgrade6Meta != null;
            upgrade6Meta.setDisplayName("§6Redstone Upgrade");
            List<String> upgrade6Lore = new ArrayList<>();
            upgrade6Lore.add("§3Preis: §6" + CookieManager.getUpgrade(6, player));
            upgrade6Lore.add("§3CPC: §6+10.0");
            upgrade6Lore.add("§3Kontostand: §6" + CookieManager.getCookie(player));
            upgrade6Meta.setLore(upgrade6Lore);
            upgrade6.setItemMeta(upgrade6Meta);
            this.mainInv.setItem(7, upgrade6);

            // 7. Upgrade
            ItemStack upgrade7 = new ItemStack(Material.GOLD_INGOT);
            ItemMeta upgrade7Meta = upgrade7.getItemMeta();
            assert upgrade7Meta != null;
            upgrade7Meta.setDisplayName("§6Gold Upgrade");
            List<String> upgrade7Lore = new ArrayList<>();
            upgrade7Lore.add("§3Preis: §6" + CookieManager.getUpgrade(7, player));
            upgrade7Lore.add("§3CPC: §6+12.5");
            upgrade7Lore.add("§3Kontostand: §6" + CookieManager.getCookie(player));
            upgrade7Meta.setLore(upgrade7Lore);
            upgrade7.setItemMeta(upgrade7Meta);
            this.mainInv.setItem(8, upgrade7);

         /*   // 8. Upgrade
            ItemStack upgrade8 = new ItemStack(Material.EMERALD);
            ItemMeta upgrade8Meta = upgrade8.getItemMeta();
            assert upgrade8Meta != null;
            upgrade8Meta.setDisplayName("§6Emerald Upgrade");
            List<String> upgrade8Lore = new ArrayList<>();
            upgrade8Lore.add("§3Preis: §6" + CookieManager.getUpgrade(8, player));
            upgrade8Lore.add("§3CPC: §6+15.0");
            upgrade8Lore.add("§3Kontostand: §6" + CookieManager.getCookie(player));
            upgrade8Meta.setLore(upgrade8Lore);
            upgrade8.setItemMeta(upgrade8Meta);
            this.mainInv.setItem(11, upgrade8);

            // 9. Upgrade
            ItemStack upgrade9 = new ItemStack(Material.DIAMOND);
            ItemMeta upgrade9Meta = upgrade9.getItemMeta();
            assert upgrade9Meta != null;
            upgrade9Meta.setDisplayName("§6Diamant Upgrade");
            List<String> upgrade9Lore = new ArrayList<>();
            upgrade9Lore.add("§3Preis: §6" + CookieManager.getUpgrade(9, player));
            upgrade9Lore.add("§3CPC: §6+17.5");
            upgrade9Lore.add("§3Kontostand: §6" + CookieManager.getCookie(player));
            upgrade9Meta.setLore(upgrade9Lore);
            upgrade9.setItemMeta(upgrade9Meta);
            this.mainInv.setItem(12, upgrade9);

            // 10. Upgrade
            ItemStack upgrade10 = new ItemStack(Material.NETHERITE_INGOT);
            ItemMeta upgrade10Meta = upgrade10.getItemMeta();
            assert upgrade10Meta != null;
            upgrade10Meta.setDisplayName("§6Netherite Upgrade");
            List<String> upgrade10Lore = new ArrayList<>();
            upgrade10Lore.add("§3Preis: §6" + CookieManager.getUpgrade(10, player));
            upgrade10Lore.add("§3CPC: §6+20.0");
            upgrade10Lore.add("§3Kontostand: §6" + CookieManager.getCookie(player));
            upgrade10Meta.setLore(upgrade10Lore);
            upgrade10.setItemMeta(upgrade10Meta);
            this.mainInv.setItem(13, upgrade10);
*/
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

        public void updateGui (Player player){

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

