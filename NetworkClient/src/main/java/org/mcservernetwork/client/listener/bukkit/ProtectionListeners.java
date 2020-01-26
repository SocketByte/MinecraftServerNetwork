package org.mcservernetwork.client.listener.bukkit;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.mcservernetwork.client.util.ColorUtils;
import org.mcservernetwork.client.util.SectorLocationUtils;
import org.mcservernetwork.client.util.manager.PlayerTransferManager;

public class ProtectionListeners implements Listener {

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (PlayerTransferManager.isTransferring(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEntityDamageByBlock(EntityDamageByBlockEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (PlayerTransferManager.isTransferring(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockBurn(BlockBurnEvent event) {
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
