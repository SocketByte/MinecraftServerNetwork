package org.mcservernetwork.client;

import io.lettuce.core.pubsub.RedisPubSubAdapter;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcservernetwork.commons.NetworkAPI;
import org.mcservernetwork.commons.net.Channel;
import org.mcservernetwork.commons.net.NetworkLogger;
import org.mcservernetwork.commons.net.packet.Packet;
import org.mcservernetwork.commons.net.packet.PacketAccept;

import java.util.Map;
import java.util.concurrent.*;

public class Client extends JavaPlugin {

    private static Map<String, Object> receivedConfiguration;
    private static NetworkLogger logger;

    private ScheduledExecutorService service = Executors.newScheduledThreadPool(4);

    @Override
    public void onEnable() {
        saveDefaultConfig();

        NetworkAPI.Internal.createNetwork(getConfig().getString("connectionPattern"));

        CountDownLatch latch = new CountDownLatch(1);
        NetworkAPI.Net.subscribe(Channel.ACCEPT, PacketAccept.class, packet -> {
            receivedConfiguration = packet.configuration;
            System.out.println("Proxy accepted the sector.");
            System.out.println("Connecting to network logger...");
            logger = new NetworkLogger("SECTOR-" + packet.sectorId);
            logger.log("Connected and ready.", NetworkLogger.LogSeverity.INFO);
            latch.countDown();
        });

        PacketAccept accept = new PacketAccept();
        accept.sectorId = getConfig().getInt("sectorId");
        NetworkAPI.Net.publish(Channel.VERIFY, accept);
        System.out.println("Sent acceptation request to proxy. Waiting 10 seconds for response.");
        try {
            latch.await(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException("Latch got interrupted", e);
        }
        if (logger == null) {
            System.out.println("Proxy did not accept the sector. Shutting down.");
            getServer().shutdown();
            return;
        }
        setup();
    }

    public void setup() {
        System.out.println("Next setup here ...");
    }
}
