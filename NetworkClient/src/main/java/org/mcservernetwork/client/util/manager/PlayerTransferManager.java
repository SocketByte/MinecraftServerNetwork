package org.mcservernetwork.client.util.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.mcservernetwork.client.Client;
import org.mcservernetwork.commons.future.TrackedFutureProvider;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PlayerTransferManager {

    private static final Set<UUID> TRANSFERRING = new HashSet<>();
    private static final TrackedFutureProvider<UUID, Player> TRANSFER_PROVIDER = new TrackedFutureProvider<>();

    public static TrackedFutureProvider<UUID, Player> getProvider() {
        return TRANSFER_PROVIDER;
    }

    public static void setTransferring(Player player) {
        TRANSFERRING.add(player.getUniqueId());

        Bukkit.getScheduler().runTaskLater(Client.getInstance(), () -> {
            TRANSFERRING.remove(player.getUniqueId());
        }, 20);
    }

    public static boolean isTransferring(Player player) {
        return TRANSFERRING.contains(player.getUniqueId());
    }
}
