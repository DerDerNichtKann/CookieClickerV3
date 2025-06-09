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

                if (clickedItem.getItemMeta().getDisplayName().equals("§aEquip")) {
                    ItemStack cookieBoots = createCookieBoots();
                    player.getInventory().setBoots(cookieBoots);
                    plugin.checkPlayersWithCookieBoots();
                    player.closeInventory();
                } else if (clickedItem.getItemMeta().getDisplayName().equals("§cUnequip")) {
                    player.getInventory().setBoots(null);
                    plugin.checkPlayersWithCookieBoots();
                    player.closeInventory();
                }
                return;
            }

            switch (e.getRawSlot()) {
                case 9:
                    gui.get(player).mainGui(player, 1);
                    break;
                case 18:
                    gui.get(player).mainGui(player, 2);
                    break;
                case 45:
                    openBootsGUI(player);
                    break;
                default:
            }

            if (e.getView().getTitle().equals("§6CookieClicker") && e.getRawSlot() <= 53) {
                try {
                    if (Objects.requireNonNull(e.getCurrentItem()).getType().equals(Material.COOKIE)) {
                        double cpc = CookieManager.getCPC(player);
                        CookieManager.modifyCookie(cpc, player);
                        this.autoklickerReset++;
                        if (this.autoklickerReset >= 1000000000) {
                            this.autoklicker = 0;
                        }
                        gui.get(player).mainGui(player, 1);
                    } else if (e.getCurrentItem().getType().equals(Material.LIGHT_GRAY_STAINED_GLASS_PANE)) {
                        this.autoklicker++;
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
                    case 2:
                        handleUpgrade(player, 1, 0.1, 100.0);
                        break;
                    case 3:
                        handleUpgrade(player, 2, 0.3, 500.0);
                        break;
                    case 4:
                        handleUpgrade(player, 3, 1.0, 1500.0);
                        break;
                    case 5:
                        handleUpgrade(player, 4, 3.5, 5000.0);
                        break;
                    case 6:
                        handleUpgrade(player, 5, 7.0, 10000.0);
                        break;
                    case 7:
                        handleUpgrade(player, 6, 10.0, 20000.0);
                        break;
                    case 8:
                        handleUpgrade(player, 7, 12.5, 50000.0);
                        break;
                    default:
                }
            }
        }
    }

    private void handleUpgrade(Player player, int upgradeId, double cpcIncrease, double minPrice) throws SQLException {
        double price = CookieManager.getUpgrade(upgradeId, player);
        if (price < minPrice) {
            price = minPrice;
            CookieManager.modifyUpgrade(upgradeId, price, player);
            gui.get(player).mainGui(player, 2);
        }
        if (CookieManager.getCookie(player) >= price) {
            CookieManager.modifyCookie(-price, player);
            CookieManager.modifyCPC(cpcIncrease, player);
            double newPrice = (price * 1.15) - price;
            CookieManager.modifyUpgrade(upgradeId, newPrice, player);
            gui.get(player).mainGui(player, 2);
        }
    }

    private void openBootsGUI(Player player) {
        Inventory bootsGUI = Bukkit.createInventory(player, 9, "§6Cookie Boots");

        ItemStack equip = new ItemStack(Material.GREEN_WOOL);
        ItemMeta equipMeta = equip.getItemMeta();
        assert equipMeta != null;
        equipMeta.setDisplayName("§aEquip");
        equip.setItemMeta(equipMeta);

        ItemStack unequip = new ItemStack(Material.RED_WOOL);
        ItemMeta unequipMeta = unequip.getItemMeta();
        assert unequipMeta != null;
        unequipMeta.setDisplayName("§cUnequip");
        unequip.setItemMeta(unequipMeta);

        bootsGUI.setItem(3, equip);
        bootsGUI.setItem(5, unequip);

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
