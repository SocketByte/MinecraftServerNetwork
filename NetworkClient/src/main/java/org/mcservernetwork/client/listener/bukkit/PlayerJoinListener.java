package org.mcservernetwork.client.listener.bukkit;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.mcservernetwork.client.util.manager.PlayerTransferManager;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (PlayerTransferManager.getProvider()
                .complete(player.getUniqueId(), player)) {
            event.setJoinMessage(null);
        }
    }

}
