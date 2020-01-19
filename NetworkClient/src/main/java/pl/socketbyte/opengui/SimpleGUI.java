package pl.socketbyte.opengui;

import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class SimpleGUI extends GUIExtender {
    public SimpleGUI(GUI gui) {
        super(gui);
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
    }

    @Override
    public void onClose(InventoryCloseEvent event) {
    }
}
