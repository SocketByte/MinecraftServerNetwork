package org.mcservernetwork.client.listener.bukkit;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.mcservernetwork.client.listener.TransferAcceptListener;

import java.util.concurrent.CompletableFuture;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        event.setJoinMessage(null);

        CompletableFuture<Player> future = TransferAcceptListener.PLAYER_FUTURES.get(player.getUniqueId());
        if (future != null) {
            future.complete(player);
        }
    }

}
