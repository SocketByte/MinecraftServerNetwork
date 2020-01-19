package pl.socketbyte.opengui.event;

import org.bukkit.event.inventory.InventoryClickEvent;

public interface SerializedElementResponse {

    void onClick(InventoryClickEvent event, String action);

}
