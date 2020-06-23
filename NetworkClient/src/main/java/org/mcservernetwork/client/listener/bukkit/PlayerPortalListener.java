package org.mcservernetwork.client.listener.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerPortalListener implements Listener {

    @EventHandler
    public void onPortalUse(PlayerPortalEvent event) {
        Bukkit.getPluginManager().callEvent(new PlayerTeleportEvent(event.getPlayer(),
                event.getFrom(), event.getTo()));
    }
}
