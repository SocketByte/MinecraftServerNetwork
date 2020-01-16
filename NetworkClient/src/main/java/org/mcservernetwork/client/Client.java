package org.mcservernetwork.client;

import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcservernetwork.client.command.TestCommand;
import org.mcservernetwork.client.listener.StatusListener;
import org.mcservernetwork.client.listener.TransferAcceptListener;
import org.mcservernetwork.client.listener.bukkit.PlayerMoveListener;
import org.mcservernetwork.commons.NetworkAPI;
import org.mcservernetwork.commons.net.Channel;
import org.mcservernetwork.commons.net.NetworkLogger;
import org.mcservernetwork.commons.net.Sector;
import org.mcservernetwork.commons.net.packet.PacketAccept;
import org.mcservernetwork.commons.net.packet.PacketPingPong;
import org.mcservernetwork.commons.net.packet.PacketTransferAccept;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Client extends JavaPlugin {

    private static NetworkLogger logger;

    private static String sectorName;

    public static Sector getCurrentSector() {
        return NetworkAPI.Sectors.getSector(sectorName);
    }

    private ScheduledExecutorService service = Executors.newScheduledThreadPool(4);

    @Override
    public void onEnable() {
        saveDefaultConfig();
        sectorName = getConfig().getString("sectorName");

        NetworkAPI.Internal.createNetwork(getConfig().getString("connectionPattern"));
        NetworkAPI.Net.subscribe(Channel.SECTOR(sectorName));

        accept();

        getCommand("test").setExecutor(new TestCommand());

        getServer().getPluginManager().registerEvents(new PlayerMoveListener(), this);

        NetworkAPI.Net.subscribeAndListen(Channel.PING, PacketPingPong.class, new StatusListener());
        NetworkAPI.Net.listen(Channel.SECTOR(sectorName), PacketTransferAccept.class, new TransferAcceptListener());
    }

    public void accept() {
        CountDownLatch latch = new CountDownLatch(1);
        System.out.println(Channel.SECTOR(sectorName));
        NetworkAPI.Net.listen(Channel.SECTOR(sectorName), PacketAccept.class, packet -> {
            NetworkAPI.Internal.applySectors(packet.sectors);
            System.out.println("Proxy accepted the sector.");
            System.out.println("Connecting to network logger...");
            logger = new NetworkLogger("SECTOR:" + packet.sectorName);
            logger.log("Connected and ready.", NetworkLogger.LogSeverity.INFO);
            sectorName = packet.sectorName;
            latch.countDown();
        });

        PacketAccept accept = new PacketAccept();
        accept.sectorName = sectorName;
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
        }
    }

    @Override
    public void onDisable() {
        service.shutdown();
        NetworkAPI.Net.unsubscribe(Channel.SECTOR(sectorName));
    }
}
