package org.mcservernetwork.proxy.listener;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import org.mcservernetwork.commons.NetworkAPI;
import org.mcservernetwork.commons.net.Channel;
import org.mcservernetwork.commons.net.Sector;
import org.mcservernetwork.commons.net.packet.PacketTransfer;
import org.mcservernetwork.proxy.Proxy;
import org.mcservernetwork.proxy.util.ColorUtils;

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
        ServerInfo info = Proxy.getInstance().getProxy().getServerInfo(sector.getSectorName());
        player.connect(info, (result, error) -> {
            if (result) {
                player.sendMessage(new TextComponent(
                        ColorUtils.fixColors("&dConnecting to &l" + packet.targetSectorName + "&d...")));
            } else {
                player.sendMessage(new TextComponent(
                        ColorUtils.fixColors("&cCould not connect to &l" + packet.targetSectorName + "&c (Sector might be unavailable)")));
            }
        }, ServerConnectEvent.Reason.PLUGIN);

        NetworkAPI.Net.publish(Channel.sector(packet.targetSectorName), packet);
    }
}
