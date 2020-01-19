package pl.socketbyte.opengui;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class GUIHolder implements InventoryHolder {

    private final Inventory inventory;
    private final GUI gui;

    public GUIHolder(GUI gui, Inventory inventory) {
        this.inventory = inventory;
        this.gui = gui;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public GUI getGui() {
        return gui;
    }
}
