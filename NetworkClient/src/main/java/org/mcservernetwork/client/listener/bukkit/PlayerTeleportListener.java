package org.mcservernetwork.client.listener.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.mcservernetwork.client.event.PlayerTransferEvent;
import org.mcservernetwork.client.util.PlayerUtils;
import org.mcservernetwork.client.util.SectorLocationUtils;
import org.mcservernetwork.client.util.manager.PlayerTransferManager;
import org.mcservernetwork.commons.NetworkAPI;
import org.mcservernetwork.commons.net.Channel;
import org.mcservernetwork.commons.net.Sector;
import org.mcservernetwork.commons.net.packet.PacketTransfer;

public class PlayerTeleportListener implements Listener {

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Location location = event.getTo();
        Location fromLocation = event.getFrom();
        Player player = event.getPlayer();
        Sector in = SectorLocationUtils.getSectorIn(location);
        if (in == null)
            return;

        PlayerTransferEvent e = new PlayerTransferEvent(fromLocation, location, in, player);
        Bukkit.getPluginManager().callEvent(e);

        if(e.isCancelled()){
            e.setCancelled(true);
            return;
        }
        PacketTransfer packet = new PacketTransfer();
        packet.targetSectorName = in.getSectorName();
        packet.uniqueId = player.getUniqueId().toString();
        packet.info = PlayerUtils.wrap(player, location);

        PlayerTransferManager.setTransferring(player);

        BossBar bossBar = PlayerMoveListener.BOSSBARS.get(player.getUniqueId());
        if(bossBar != null){
            bossBar.removeAll();
            PlayerMoveListener.BOSSBARS.remove(player.getUniqueId());
        }

        Entity vehicle = player.getVehicle();

        if (vehicle != null) {
            vehicle.remove();
        }
        event.setCancelled(true);
        NetworkAPI.Net.publish(Channel.TRANSFER_REQUEST, packet);
    }
}
