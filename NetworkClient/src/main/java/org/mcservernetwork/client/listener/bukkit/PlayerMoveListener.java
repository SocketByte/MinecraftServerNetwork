package org.mcservernetwork.client.listener.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.mcservernetwork.client.Client;
import org.mcservernetwork.client.util.ColorUtils;
import org.mcservernetwork.client.util.SectorLocationUtils;
import org.mcservernetwork.client.util.StringUtils;
import org.mcservernetwork.commons.NetworkAPI;
import org.mcservernetwork.commons.net.Channel;
import org.mcservernetwork.commons.net.Sector;
import org.mcservernetwork.commons.net.packet.PacketTransferRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

public class PlayerMoveListener implements Listener {

    private final Map<UUID, BossBar> bossBars = new WeakHashMap<>();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location location = event.getTo();
        if (location == null)
            return;

        double distance = SectorLocationUtils.distance(location);
        Sector nearest = SectorLocationUtils.getNearest(location);
        if (distance > SectorLocationUtils.MAX_DISTANCE || nearest == null) {
            BossBar bossBar = bossBars.get(player.getUniqueId());
            if (bossBar != null) {
                bossBar.removeAll();
                bossBars.remove(player.getUniqueId());
            }
            return;
        }

        BossBar bossBar = bossBars.get(player.getUniqueId());
        if (bossBar == null) {
            bossBar = Bukkit.createBossBar("",
                    BarColor.GREEN, BarStyle.SOLID);
            bossBar.setVisible(true);
            bossBar.setProgress(0);
            bossBar.addPlayer(player);
            bossBars.put(player.getUniqueId(), bossBar);
        }
        bossBar.setTitle(ColorUtils.fixColors("&2&l" + StringUtils.capitalizeFirstLetter(nearest.getSectorName())
                + " &2&l(&a&l" + Math.round(distance) + "m&2&l)"));
        bossBar.setProgress(SectorLocationUtils.progress(distance));

        Sector in = SectorLocationUtils.getSectorIn(location);
        if (in == null)
            return;

        bossBar.removeAll();
        bossBars.remove(player.getUniqueId());
        PacketTransferRequest packet = new PacketTransferRequest();

        System.out.println("IN SECTOR " + in.getSectorName());

        //NetworkAPI.Net.publish(Channel.TRANSFER_REQUEST, packet);

    }
}
