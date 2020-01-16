package org.mcservernetwork.proxy.listener;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.mcservernetwork.commons.NetworkAPI;
import org.mcservernetwork.commons.net.Sector;
import org.mcservernetwork.commons.net.packet.PacketTransferRequest;
import org.mcservernetwork.commons.net.packet.PacketTransferAccept;
import org.mcservernetwork.proxy.Proxy;

import java.util.UUID;

public class TransferRequestListener implements NetworkAPI.Net.Listener<PacketTransferRequest> {
    @Override
    public void receive(PacketTransferRequest packet) {
        ProxiedPlayer player = Proxy.getInstance().getProxy()
                .getPlayer(UUID.fromString(packet.uniqueId));

        Sector sector = NetworkAPI.Sectors.getSector(packet.targetSectorName);
        if (sector == null) {
            System.out.println("Sector " + packet.targetSectorName + " not found. Bypassing request.");
            return;
        }
        player.connect(Proxy.getInstance().getProxy().getServerInfo(sector.getSectorName()));

        PacketTransferAccept accept = new PacketTransferAccept();
        accept.info = packet.info;
        accept.uniqueId = packet.uniqueId;
    }
}
