package eurocity.eu.cookieclickerv3.util;

import eurocity.eu.cookieclickerv3.ColorGradient;
import eurocity.eu.cookieclickerv3.CookieClickerV3;
import org.bukkit.*;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;


import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;



public class GUIListener implements Listener {
    HashMap<Player, GUI> gui = CookieClickerV3.getPlayerGuiHashMap();
    GUI Gui = new GUI();
    private final CookieClickerV3 plugin;
    private final NamespacedKey droppableKey;
    private final NamespacedKey itemPhysicsKey;
    private final String PHYSICS_GUI_TITLE = "Item Physics Settings";
    private final Map<Player, Inventory> cosmeticsGUIs = new HashMap<>();
    private final Map<Player, BukkitRunnable> itemSpawnTasks = new HashMap<>();
    int autoklicker = 0;
    int autoklickerReset = 0;

    public GUIListener(CookieClickerV3 plugin){
        this.plugin = plugin;
        this.droppableKey = new NamespacedKey(plugin, "droppable");
        this.itemPhysicsKey = new NamespacedKey(plugin, "itemphysics");
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e) {
        if (e.getView().getTitle().equals("§6CookieClicker") || e.getView().getTitle().equals("§6CookieClicker - Upgrade")) {
            for (int i : e.getRawSlots()) {
                if (i <= 53) {
                    e.setCancelled(true);
                    break;
                }
            }
        }
    }

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            Item item = event.getItem();
            ItemStack itemStack = item.getItemStack();
            if (itemStack.hasItemMeta() && itemStack.getItemMeta().getPersistentDataContainer().has(droppableKey, PersistentDataType.BYTE)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onCookieClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack mainHandItem = player.getInventory().getItemInMainHand();
        ItemStack offHandItem = player.getInventory().getItemInOffHand();
        ColorGradient gradient = new ColorGradient();
        String gradientText = gradient.applyCookieGradient("Cookie Clicker");

        if (mainHandItem.getType() == Material.COOKIE && mainHandItem.hasItemMeta()) {
            ItemMeta mainHandMeta = mainHandItem.getItemMeta();
            if (mainHandMeta != null && mainHandMeta.hasDisplayName() && mainHandMeta.getDisplayName().equals(gradientText)
                    && event.getAction().name().contains("RIGHT_CLICK")) {
                try {
                    DatabaseManager.updateUser(player, 0.0, 0.0, 0, 0.0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
                    this.Gui.mainGui(player, 1);
                    this.plugin.getPlayerGuiHashMap().put(player, this.Gui);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                event.setCancelled(true);
            }
        }

        if (offHandItem.getType() == Material.COOKIE && offHandItem.hasItemMeta()) {
            ItemMeta offHandMeta = offHandItem.getItemMeta();
            if (offHandMeta != null && offHandMeta.hasDisplayName() && offHandMeta.getDisplayName().equals(gradientText)
                    && event.getAction().name().contains("RIGHT_CLICK")) {
                try {
                    DatabaseManager.updateUser(player, 0.0, 0.0, 0, 0.0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
                    this.Gui.mainGui(player, 1);
                    this.plugin.getPlayerGuiHashMap().put(player, this.Gui);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) throws SQLException {
        Player player = (Player) e.getWhoClicked();

        if ((e.getView().getTitle().equals("§6CookieClicker") || e.getView().getTitle().equals("§6CookieClicker - Upgrade") || e.getView().getTitle().equals("§6Cookie Boots")) || e.getView().getTitle().equals("§6Cookie Ring") && e.getRawSlot() <= 53) {
            e.setCancelled(true);

            if (e.getView().getTitle().equals("§6Cookie Boots")) {
                ItemStack clickedItem = e.getCurrentItem();
                if (clickedItem == null || clickedItem.getType() == Material.AIR) {
                    return;
                }

                if (clickedItem.getItemMeta().getDisplayName().equals("§aAnziehen")) {
                    ItemStack cookieBoots = createCookieBoots();
                    player.getInventory().setBoots(cookieBoots);
                    plugin.checkPlayersWithCookieBoots();
                    player.closeInventory();
                } else if (clickedItem.getItemMeta().getDisplayName().equals("§cAusziehen")) {
                    player.getInventory().setBoots(null);
                    plugin.checkPlayersWithCookieBoots();
                    player.closeInventory();
                }
                return;
            }




            switch (e.getRawSlot()) {
                case 0:

                    break;

                case 9: // Klicker
                    gui.get(player).mainGui(player, 1);
                    break;

                case 18: // Shop
                    gui.get(player).mainGui(player, 2);
                    break;

                case 27:
                    break;

                case 36:
                    break;

                case 45: //Cookieboots
                    openBootsGUI(player);
                    break;

                default:
            }


            if (e.getView().getTitle().equals("§6CookieClicker") && e.getRawSlot() <= 53) {
                // Cookie
                try {
                    if (Objects.requireNonNull(e.getCurrentItem()).getType().equals(Material.COOKIE)) {
                        double cpc = CookieManager.getCPC(player);
                        CookieManager.modifyCookie(cpc, player);
                        this.autoklickerReset = this.autoklickerReset + 1;
                        if (this.autoklickerReset >= 1000000000) {
                            this.autoklicker = 0;
                        }
                        gui.get(player).mainGui(player, 1);
                        //Gui.updateGui(player);

                        // AutoKlicker Detection
                    } else if (e.getCurrentItem().getType().equals(Material.LIGHT_GRAY_STAINED_GLASS_PANE)) {
                        this.autoklicker = this.autoklicker + 1;
                        this.autoklickerReset = 0;
                        if (this.autoklicker >= 1000000000) {
                            player.closeInventory();
                            this.autoklicker = 0;
                        }
                    }

                } catch (NullPointerException ignored) {
                }
            }
            if (e.getView().getTitle().equals("§6CookieClicker - Upgrade") && e.getRawSlot() <= 53) {
                switch (e.getRawSlot()) {
                    case 2: // Upgrade 1
                        double price = CookieManager.getUpgrade(1, player);
                        if (price < 100) {
                            price = 100.0;
                            CookieManager.modifyUpgrade(1, price, player);
                            gui.get(player).mainGui(player, 2);
                        }
                        if (CookieManager.getCookie(player) >= price) {
                            CookieManager.modifyCookie(-price, player);
                            CookieManager.modifyCPC(0.1, player);
                            double price_new = (price * 1.15) - price;
                            CookieManager.modifyUpgrade(1, price_new, player);

                            gui.get(player).mainGui(player, 2);
                        }
                        break;

                    case 3: // Upgrade 2
                        double price2 = CookieManager.getUpgrade(2, player);
                        if (price2 < 500) {
                            price2 = 500.0;
                            CookieManager.modifyUpgrade(2, price2, player);
                            gui.get(player).mainGui(player, 2);
                        }
                        if (CookieManager.getCookie(player) >= price2) {
                            CookieManager.modifyCookie(-price2, player);
                            CookieManager.modifyCPC(0.3, player);
                            double price2_new = (price2 * 1.15) - price2;
                            CookieManager.modifyUpgrade(2, price2_new, player);

                            gui.get(player).mainGui(player, 2);
                        }
                        break;

                    case 4: // Upgrade 3
                        double price3 = CookieManager.getUpgrade(3, player);
                        if (price3 < 1500) {
                            price3 = 1500.0;
                            CookieManager.modifyUpgrade(3, price3, player);
                            gui.get(player).mainGui(player, 2);
                        }
                        if (CookieManager.getCookie(player) >= price3) {
                            CookieManager.modifyCookie(-price3, player);
                            CookieManager.modifyCPC(1.0, player);
                            double price3_new = (price3 * 1.15) - price3;
                            CookieManager.modifyUpgrade(3, price3_new, player);

                            gui.get(player).mainGui(player, 2);
                        }
                        break;

                    case 5: // Upgrade 4
                        double price4 = CookieManager.getUpgrade(4, player);
                        if (price4 < 5000) {
                            price4 = 5000.0;
                            CookieManager.modifyUpgrade(4, price4, player);
                            gui.get(player).mainGui(player, 2);
                        }
                        if (CookieManager.getCookie(player) >= price4) {
                            CookieManager.modifyCookie(-price4, player);
                            CookieManager.modifyCPC(3.5, player);
                            double price4_new = (price4 * 1.15) - price4;
                            CookieManager.modifyUpgrade(4, price4_new, player);

                            gui.get(player).mainGui(player, 2);
                        }
                        break;

                    case 6: // Upgrade 5
                        double price5 = CookieManager.getUpgrade(5, player);
                        if (price5 < 10000) {
                            price5 = 10000.0;
                            CookieManager.modifyUpgrade(5, price5, player);
                            gui.get(player).mainGui(player, 2);
                        }
                        if (CookieManager.getCookie(player) >= price5) {
                            CookieManager.modifyCookie(-price5, player);
                            CookieManager.modifyCPC(7.0, player);
                            double price5_new = (price5 * 1.15) - price5;
                            CookieManager.modifyUpgrade(5, price5_new, player);

                            gui.get(player).mainGui(player, 2);
                        }
                        break;

                    case 7: // Upgrade 6
                        double price6 = CookieManager.getUpgrade(6, player);
                        if (price6 < 20000) {
                            price6 = 20000.0;
                            CookieManager.modifyUpgrade(6, price6, player);
                            gui.get(player).mainGui(player, 2);
                        }
                        if (CookieManager.getCookie(player) >= price6) {
                            CookieManager.modifyCookie(-price6, player);
                            CookieManager.modifyCPC(10.0, player);
                            double price6_new = (price6 * 1.15) - price6;
                            CookieManager.modifyUpgrade(6, price6_new, player);

                            gui.get(player).mainGui(player, 2);
                        }
                        break;
                    case 8: // Upgrade 7
                        double price7 = CookieManager.getUpgrade(7, player);
                        if (price7 < 50000) {
                            price7 = 50000.0;
                            CookieManager.modifyUpgrade(7, price7, player);
                            gui.get(player).mainGui(player, 2);
                        }
                        if (CookieManager.getCookie(player) >= price7) {
                            CookieManager.modifyCookie(-price7, player);
                            CookieManager.modifyCPC(12.5, player);
                            double price7_new = (price7 * 1.15) - price7;
                            CookieManager.modifyUpgrade(7, price7_new, player);

                            gui.get(player).mainGui(player, 2);
                        }
                        break;

                 /*   case 9: // Upgrade 8
                        double price8 = CookieManager.getUpgrade(8, player);
                        if (price8 < 75000) {
                            price8 = 75000.0;
                            CookieManager.modifyUpgrade(8, price8, player);
                            gui.get(player).mainGui(player, 2);
                        }
                        if (CookieManager.getCookie(player) >= price8) {
                            CookieManager.modifyCookie(-price8, player);
                            CookieManager.modifyCPC(15.0, player);
                            double price8_new = (price8 * 1.15) - price8;
                            CookieManager.modifyUpgrade(8, price8_new, player);

                            gui.get(player).mainGui(player, 2);
                        }
                        break;

                    case 10: // Upgrade 9
                        double price9 = CookieManager.getUpgrade(9, player);
                        if (price9 < 100000) {
                            price9 = 100000.0;
                            CookieManager.modifyUpgrade(9, price9, player);
                            gui.get(player).mainGui(player, 2);
                        }
                        if (CookieManager.getCookie(player) >= price9) {
                            CookieManager.modifyCookie(-price9, player);
                            CookieManager.modifyCPC(17.5, player);
                            double price9_new = (price9 * 1.15) - price9;
                            CookieManager.modifyUpgrade(9, price9_new, player);

                            gui.get(player).mainGui(player, 2);
                        }
                        break;

                    case 11: // Upgrade 10
                        double price10 = CookieManager.getUpgrade(10, player);
                        if (price10 < 250000) {
                            price10 = 250000.0;
                            CookieManager.modifyUpgrade(10, price10, player);
                            gui.get(player).mainGui(player, 2);
                        }
                        if (CookieManager.getCookie(player) >= price10) {
                            CookieManager.modifyCookie(-price10, player);
                            CookieManager.modifyCPC(20.0, player);
                            double price10_new = (price10 * 1.15) - price10;
                            CookieManager.modifyUpgrade(10, price10_new, player);

                            gui.get(player).mainGui(player, 2);
                        }
                        break; */
                    case 12:
                        break;
                    default:
                }
            }
        }
    }

    private void openBootsGUI(Player player) {
        Inventory bootsGUI = Bukkit.createInventory(player, 9, "§6Cookie Boots");

        ItemStack anziehen = new ItemStack(Material.GREEN_WOOL);
        ItemMeta anziehenMeta = anziehen.getItemMeta();
        assert anziehenMeta != null;
        anziehenMeta.setDisplayName("§aAnziehen");
        anziehen.setItemMeta(anziehenMeta);

        ItemStack ausziehen = new ItemStack(Material.RED_WOOL);
        ItemMeta ausziehenMeta = ausziehen.getItemMeta();
        assert ausziehenMeta != null;
        ausziehenMeta.setDisplayName("§cAusziehen");
        ausziehen.setItemMeta(ausziehenMeta);

        bootsGUI.setItem(3, anziehen);
        bootsGUI.setItem(5, ausziehen);

        player.openInventory(bootsGUI);
    }

    private ItemStack createCookieBoots() {
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        ItemMeta meta = boots.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§6Cookie Boots");
            ((LeatherArmorMeta) meta).setColor(Color.ORANGE);
            boots.setItemMeta(meta);
        }
        return boots;
    }
}



