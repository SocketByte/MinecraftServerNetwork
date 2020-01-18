package org.mcservernetwork.client.listener.bukkit;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.mcservernetwork.client.util.ColorUtils;
import org.mcservernetwork.client.util.SectorLocationUtils;

public class ProtectionListeners implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBurnEvent event) {
        Location location = event.getBlock().getLocation();

        if (SectorLocationUtils.distance(location) <= SectorLocationUtils.MAX_DISTANCE) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Location location = event.getBlock().getLocation();

        if (SectorLocationUtils.distance(location) <= SectorLocationUtils.MAX_DISTANCE) {
            player.sendMessage(ColorUtils.fixColors("&cYou're too close to another sector to break blocks."));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Location location = event.getBlock().getLocation();

        if (SectorLocationUtils.distance(location) <= SectorLocationUtils.MAX_DISTANCE) {
            player.sendMessage(ColorUtils.fixColors("&cYou're too close to another sector to place blocks."));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFire(BlockIgniteEvent event) {
        Player player = event.getPlayer();
        Location location = event.getBlock().getLocation();

        if (SectorLocationUtils.distance(location) <= SectorLocationUtils.MAX_DISTANCE) {
            if (player != null) {
                player.sendMessage(ColorUtils.fixColors("&cYou're too close to another sector to set blocks on fire."));
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation();

        if (SectorLocationUtils.distance(location) <= SectorLocationUtils.MAX_DISTANCE) {
            player.sendMessage(ColorUtils.fixColors("&cYou're too close to another sector to drop items."));
            event.setCancelled(true);
        }
    }

}
