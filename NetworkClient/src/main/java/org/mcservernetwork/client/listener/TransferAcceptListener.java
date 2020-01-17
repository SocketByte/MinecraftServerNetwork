package org.mcservernetwork.client.listener;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.mcservernetwork.client.io.BukkitSerializer;
import org.mcservernetwork.client.util.PlayerUtils;
import org.mcservernetwork.commons.NetworkAPI;
import org.mcservernetwork.commons.net.packet.PacketPlayerInfo;
import org.mcservernetwork.commons.net.packet.PacketTransfer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TransferAcceptListener implements NetworkAPI.Net.Listener<PacketTransfer> {

    public static final Map<UUID, CompletableFuture<Player>> PLAYER_FUTURES = new HashMap<>();
    private static final ScheduledExecutorService service = Executors.newScheduledThreadPool(4);

    @Override
    public void receive(PacketTransfer packet) {
        PacketPlayerInfo info = packet.info;

        UUID playerId = UUID.fromString(packet.uniqueId);

        CompletableFuture<Player> future = new CompletableFuture<>();
        future.thenAccept(player -> {
            PlayerUtils.unwrap(player, info);
            PLAYER_FUTURES.remove(playerId);
        });

        PLAYER_FUTURES.put(playerId, future);
        service.schedule(() -> {
            CompletableFuture<Player> completableFuture = PLAYER_FUTURES.get(playerId);
            if (completableFuture == null)
                return;

            completableFuture.cancel(false);
            PLAYER_FUTURES.remove(playerId);
        }, 8, TimeUnit.SECONDS);

    }
}
