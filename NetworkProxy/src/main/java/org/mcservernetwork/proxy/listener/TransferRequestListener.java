package org.mcservernetwork.proxy.listener;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.mcservernetwork.commons.NetworkAPI;
import org.mcservernetwork.commons.net.Channel;
import org.mcservernetwork.commons.net.Sector;
import org.mcservernetwork.commons.net.packet.PacketTransfer;
import org.mcservernetwork.proxy.Proxy;

import java.util.UUID;

public class TransferRequestListener implements NetworkAPI.Net.Listener<PacketTransfer> {
    @Override
    public void receive(PacketTransfer packet) {
        ProxiedPlayer player = Proxy.getInstance().getProxy()
                .getPlayer(UUID.fromString(packet.uniqueId));

        Sector sector = NetworkAPI.Sectors.getSector(packet.targetSectorName);
        if (sector == null) {
            System.out.println("Sector " + packet.targetSectorName + " not found. Bypassing request.");
            return;
        }
        player.connect(Proxy.getInstance().getProxy().getServerInfo(sector.getSectorName()));

        NetworkAPI.Net.publish(Channel.SECTOR(packet.targetSectorName), packet);
    }
}
