package org.mcservernetwork.client.listener.bukkit;

import io.lettuce.core.RedisFuture;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.mcservernetwork.client.Client;
import org.mcservernetwork.client.ClientStatusHandler;
import org.mcservernetwork.client.listener.TransferAcceptListener;
import org.mcservernetwork.client.util.ColorUtils;
import org.mcservernetwork.commons.NetworkAPI;
import org.mcservernetwork.commons.net.Channel;
import org.mcservernetwork.commons.net.packet.Packet;
import org.mcservernetwork.commons.net.packet.PacketStatus;
import org.mcservernetwork.commons.net.packet.PacketTransfer;
import org.mcservernetwork.commons.net.packet.persist.PlayerSectorData;

import java.util.concurrent.CompletableFuture;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        CompletableFuture<Player> future = TransferAcceptListener.PLAYER_FUTURES.get(player.getUniqueId());
        if (future != null) {
            event.setJoinMessage(null);
            future.complete(player);
            return;
        }

        RedisFuture<Packet> data = NetworkAPI.Net.get(player.getUniqueId().toString());
        if (data == null)
            return;

        data.thenAccept(packet -> {
            PlayerSectorData sectorData = (PlayerSectorData) packet;

            if (Client.getCurrentSector().getSectorName().equals(sectorData.currentSectorName))
                return;

            PacketStatus status = ClientStatusHandler.get(sectorData.currentSectorName);
            if (status == null) {
                Bukkit.getScheduler().runTask(Client.getInstance(), () -> {
                    event.getPlayer().kickPlayer(
                            ColorUtils.fixColors("&cSector &l" + sectorData.currentSectorName + " &cis not responding."));
                });
                return;
            }

            PacketTransfer transfer = new PacketTransfer();
            transfer.targetSectorName = sectorData.currentSectorName;
            transfer.uniqueId = player.getUniqueId().toString();
            NetworkAPI.Net.publish(Channel.TRANSFER_REQUEST, transfer);
        });
    }

}
