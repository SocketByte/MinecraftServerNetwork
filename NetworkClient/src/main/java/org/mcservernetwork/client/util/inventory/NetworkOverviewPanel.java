package org.mcservernetwork.client.util.inventory;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.mcservernetwork.client.Client;
import org.mcservernetwork.commons.NetworkAPI;
import pl.socketbyte.opengui.GUI;
import pl.socketbyte.opengui.GUIExtender;
import pl.socketbyte.opengui.Rows;

public class NetworkOverviewPanel extends GUIExtender {

    public static final NetworkOverviewPanel panel = new NetworkOverviewPanel();
    static {
        Bukkit.getScheduler().runTaskTimerAsynchronously(Client.getInstance(),
                panel::update, 20, 20);
    }

    public NetworkOverviewPanel() {
        super(new GUI("&5Network Overview", Rows.TWO));

        update();
    }

    public void update() {
        int i = 0;
        for (String sectorName : NetworkAPI.Sectors.getSectors().keySet()) {
            setExtenderItem(i, new SectorInfoItem(sectorName), null);
            i++;
        }
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
    }

    @Override
    public void onClose(InventoryCloseEvent event) {
    }
}
