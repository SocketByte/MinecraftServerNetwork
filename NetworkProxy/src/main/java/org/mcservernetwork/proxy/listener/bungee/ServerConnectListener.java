package org.mcservernetwork.proxy.listener.bungee;

import io.lettuce.core.RedisFuture;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.mcservernetwork.commons.KeepAliveHandler;
import org.mcservernetwork.commons.NetworkAPI;
import org.mcservernetwork.commons.net.packet.Packet;
import org.mcservernetwork.commons.net.packet.PacketStatus;
import org.mcservernetwork.commons.net.packet.persist.PlayerSectorData;
import org.mcservernetwork.proxy.util.ColorUtils;

public class ServerConnectListener implements Listener {

    @EventHandler
    public void onServerConnect(ServerConnectEvent event) {
        ProxiedPlayer player = event.getPlayer();

        RedisFuture<Packet> data = NetworkAPI.Net.get(player.getUniqueId().toString());
        if (data == null)
            return;

        data.thenAccept(packet -> {
            PlayerSectorData sectorData = (PlayerSectorData) packet;

            ServerInfo current = player.getServer().getInfo();
            if (current == null)
                current = event.getTarget();

            if (current.getName().equals(sectorData.currentSectorName))
                return;

            PacketStatus status = KeepAliveHandler.get(sectorData.currentSectorName);
            if (status == null) {
                player.disconnect(new TextComponent(ColorUtils.fixColors("&cSector &l" + sectorData.currentSectorName + " &cis not responding.")));
                return;
            }

            player.connect(current);
        });
    }

}
