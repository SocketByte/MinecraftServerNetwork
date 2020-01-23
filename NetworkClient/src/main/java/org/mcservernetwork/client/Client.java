package org.mcservernetwork.client;

import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcservernetwork.client.command.NetworkCommand;
import org.mcservernetwork.commons.listener.StatusListener;
import org.mcservernetwork.client.listener.TransferAcceptListener;
import org.mcservernetwork.client.listener.bukkit.*;
import org.mcservernetwork.client.task.ActionBarTask;
import org.mcservernetwork.client.task.BorderTask;
import org.mcservernetwork.client.util.TPSUtils;
import org.mcservernetwork.commons.ClientStatusHandler;
import org.mcservernetwork.commons.NetworkAPI;
import org.mcservernetwork.commons.net.Channel;
import org.mcservernetwork.commons.net.NetworkLogger;
import org.mcservernetwork.commons.net.Sector;
import org.mcservernetwork.commons.net.packet.PacketAccept;
import org.mcservernetwork.commons.net.packet.PacketStatus;
import org.mcservernetwork.commons.net.packet.PacketTransfer;
import pl.socketbyte.opengui.OpenGUI;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Client extends JavaPlugin {

    private static NetworkLogger logger;

    private static String sectorName;

    private static Client instance;

    public static Client getInstance() {
        return instance;
    }

    public static Sector getCurrentSector() {
        return NetworkAPI.Sectors.getSector(sectorName);
    }

    private ScheduledExecutorService service = Executors.newScheduledThreadPool(4);

    @Override
    public void onEnable() {
        instance = this;
        OpenGUI.INSTANCE.register(this);

        saveDefaultConfig();
        sectorName = getConfig().getString("sectorName");

        NetworkAPI.Internal.createNetwork(getConfig().getString("connectionPattern"));
        NetworkAPI.Net.subscribe(Channel.sector(sectorName));

        accept();

        getCommand("network").setExecutor(new NetworkCommand());

        getServer().getPluginManager().registerEvents(new PlayerMoveListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerTeleportListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
        getServer().getPluginManager().registerEvents(new ProtectionListeners(), this);

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new BorderTask(), 0L, 3L);
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new ActionBarTask(), 20L, 20L);

        NetworkAPI.Net.subscribeAndListen(Channel.STATUS, PacketStatus.class, new StatusListener());
        NetworkAPI.Net.listen(Channel.sector(sectorName), PacketTransfer.class, new TransferAcceptListener());

        ClientStatusHandler.run(() -> {
            PacketStatus packet = new PacketStatus();
            packet.sectorName = Client.getCurrentSector().getSectorName();
            packet.players = Bukkit.getOnlinePlayers().size();
            packet.tps = TPSUtils.getRecentTPS(0);
            packet.timestamp = System.currentTimeMillis();

            NetworkAPI.Net.publish(Channel.STATUS, packet);
        });
    }

    public void accept() {
        CountDownLatch latch = new CountDownLatch(1);
        NetworkAPI.Net.listen(Channel.sector(sectorName), PacketAccept.class, packet -> {
            NetworkAPI.Internal.applySectors(packet.sectors);
            System.out.println("Proxy accepted the sector.");
            System.out.println("Connecting to network logger...");
            logger = new NetworkLogger("SECTOR:" + packet.sectorName);
            logger.log("Connected and ready.", NetworkLogger.LogSeverity.INFO);
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
        for (BossBar bar : PlayerMoveListener.BOSSBARS.values()) {
            bar.removeAll();
        }
        service.shutdown();
        NetworkAPI.Net.unsubscribe(Channel.sector(sectorName));
        NetworkAPI.Net.disconnect();
    }
}
