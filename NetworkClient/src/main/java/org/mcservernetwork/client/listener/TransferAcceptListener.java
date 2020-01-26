package org.mcservernetwork.client.listener;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.mcservernetwork.client.Client;
import org.mcservernetwork.client.util.PlayerUtils;
import org.mcservernetwork.client.util.manager.PlayerTransferManager;
import org.mcservernetwork.commons.NetworkAPI;
import org.mcservernetwork.commons.future.TrackedFutureProvider;
import org.mcservernetwork.commons.net.packet.PacketPlayerInfo;
import org.mcservernetwork.commons.net.packet.PacketTransfer;
import org.mcservernetwork.commons.net.packet.persist.PlayerSectorData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TransferAcceptListener implements NetworkAPI.Net.Listener<PacketTransfer> {

    @Override
    public void receive(PacketTransfer packet) {
        PacketPlayerInfo info = packet.info;

        UUID playerId = UUID.fromString(packet.uniqueId);
        if (info == null)
            return;

        PlayerTransferManager.getProvider()
                .create(playerId)
                .withTimeout(8, TimeUnit.SECONDS)
                .completed(player -> {
            PlayerUtils.unwrap(player, info);

            PlayerSectorData data = new PlayerSectorData();
            data.currentSectorName = Client.getCurrentSector().getSectorName();
            NetworkAPI.Net.set(player.getUniqueId().toString(), data);
        });

        Player player = Bukkit.getPlayer(playerId);
        if (player != null)
            PlayerTransferManager.getProvider()
                    .complete(playerId, player);
    }
}
