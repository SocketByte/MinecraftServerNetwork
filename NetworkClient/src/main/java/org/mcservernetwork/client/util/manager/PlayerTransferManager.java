package org.mcservernetwork.client.util.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.mcservernetwork.client.Client;
import org.mcservernetwork.commons.future.TrackedFutureProvider;

import java.util.*;

public class PlayerTransferManager {

    private static final Set<UUID> TRANSFERRING = new HashSet<>();
    private static final Map<UUID, Long> DELAY_CONTAINER = new HashMap<>();
    private static final TrackedFutureProvider<UUID, Player> TRANSFER_PROVIDER = new TrackedFutureProvider<>();

    public static TrackedFutureProvider<UUID, Player> getProvider() {
        return TRANSFER_PROVIDER;
    }

    public static void setTransferring(Player player) {
        TRANSFERRING.add(player.getUniqueId());
        DELAY_CONTAINER.put(player.getUniqueId(), System.currentTimeMillis() + 4000); // 4000ms delay

        Bukkit.getScheduler().runTaskLater(Client.getInstance(),
                () -> TRANSFERRING.remove(player.getUniqueId()), 20);
    }

    public static boolean canTransfer(Player player) {
        if (!DELAY_CONTAINER.containsKey(player.getUniqueId()))
            return true;
        return DELAY_CONTAINER.get(player.getUniqueId()) - System.currentTimeMillis() < 0;
    }

    public static boolean isTransferring(Player player) {
        return TRANSFERRING.contains(player.getUniqueId());
    }
}
