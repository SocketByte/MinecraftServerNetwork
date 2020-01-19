package org.mcservernetwork.client;

import org.bukkit.Bukkit;
import org.mcservernetwork.client.util.TPSUtils;
import org.mcservernetwork.commons.NetworkAPI;
import org.mcservernetwork.commons.net.Channel;
import org.mcservernetwork.commons.net.packet.PacketStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ClientStatusHandler {

    private static final ScheduledExecutorService service = Executors.newScheduledThreadPool(4);
    private static Map<String, PacketStatus> status = new HashMap<>();

    public static void set(PacketStatus packet) {
        status.put(packet.sectorName, packet);
    }

    public static void run() {
        service.scheduleAtFixedRate(() -> {
            PacketStatus packet = new PacketStatus();
            packet.sectorName = Client.getCurrentSector().getSectorName();
            packet.players = Bukkit.getOnlinePlayers().size();
            packet.tps = TPSUtils.getRecentTPS(0);
            packet.timestamp = System.currentTimeMillis();

            NetworkAPI.Net.publish(Channel.STATUS, packet);
        }, 1, 1, TimeUnit.SECONDS);
    }

    public static PacketStatus get(String sectorName) {
        PacketStatus packet = status.get(sectorName);
        if (packet == null)
            return null;
        long diff = System.currentTimeMillis() - packet.timestamp;
        if (diff >= 3000)
            return null;
        return packet;
    }

    public static long getDurationSinceLastPacket(String sectorName) {
        PacketStatus packet = status.get(sectorName);
        if (packet == null)
            return -1;
        return System.currentTimeMillis() - packet.timestamp;
    }

}
