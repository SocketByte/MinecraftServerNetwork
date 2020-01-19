package pl.socketbyte.opengui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class GUI {

    private final UUID uniqueId = UUID.randomUUID();
    private Inventory inventory;
    private final Rows rows;
    private final String title;
    private InventoryHolder holder;

    public GUI(InventoryHolder holder, String title) {
        this.rows = Rows.SIX;
        this.holder = holder;
        this.title = title;

        this.inventory = createInventory(holder, Rows.SIX, title);
    }

    public GUI(InventoryHolder holder, String title, Rows rows) {
        this.rows = rows;
        this.holder = holder;
        this.title = title;

        this.inventory = createInventory(holder, rows, title);
    }

    public GUI(String title, Rows rows) {
        this.rows = rows;
        this.title = title;

        this.inventory = createInventory(
                new GUIHolder(this, Bukkit.createInventory(null,
                        rows.getSlots())),
                rows,
                title);
    }

    public GUI(String title) {
        this.rows = Rows.SIX;
        this.title = title;

        this.inventory = createInventory(
                new GUIHolder(this, Bukkit.createInventory(null, Rows.SIX.getSlots())),
                Rows.SIX,
                title);
    }

    public void openInventory(Player player) {
        player.openInventory(this.inventory);
    }

    public int addItem(ItemBuilder itemBuilder) {
        try {
            this.inventory.addItem(itemBuilder.getItem());
        } catch (Exception ignored) { }
        return getPosition(itemBuilder.getItem().getType(), itemBuilder.getItem().getDurability());
    }

    private int getPosition(Material material, int data) {
        try {
            for (int i = 0; i < this.inventory.getContents().length; i++) {
                ItemStack itemStack = this.inventory.getItem(i);

                if (itemStack.getType().equals(material)
                        && itemStack.getDurability() == data)
                    return i;
            }
        } catch (Exception ignored) { }
        return -1;
    }

    public void setItem(int slot, ItemBuilder itemBuilder) {
        try {
            this.inventory.setItem(slot, itemBuilder.getItem());
        } catch (Exception ignored) { }
    }

    public void removeItem(int slot) {
        this.inventory.setItem(slot, new ItemStack(Material.AIR));
    }

    public static Inventory createInventory(InventoryHolder holder, Rows rows, String title) {
        return Bukkit.createInventory(holder,
                rows.getSlots(),
                ColorUtil.fixColor(title));
    }

    public static Inventory createInventory(InventoryHolder holder, int size, String title) {
        return Bukkit.createInventory(holder,
                size,
                ColorUtil.fixColor(title));
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public String toString() {
        return uniqueId.toString();
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Rows getRows() {
        return rows;
    }

    public String getTitle() {
        return title;
    }

    public InventoryHolder getHolder() {
        return holder;
    }
}
