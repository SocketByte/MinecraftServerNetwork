package org.mcservernetwork.proxy;

import net.md_5.bungee.api.plugin.Plugin;
import org.mcservernetwork.commons.NetworkAPI;
import org.mcservernetwork.commons.net.Channel;
import org.mcservernetwork.commons.net.NetworkLogger;
import org.mcservernetwork.commons.net.packet.PacketAccept;

import java.util.HashMap;

public class Proxy extends Plugin {

    private static final NetworkLogger logger = new NetworkLogger("PROXY");

    @Override
    public void onEnable() {
        NetworkAPI.Internal.createNetwork("redis://password@localhost:6379/0");

        logger.listen();

        logger.log("Test message", NetworkLogger.LogSeverity.INFO);

        NetworkAPI.Net.subscribe(Channel.VERIFY, PacketAccept.class, packet -> {
            System.out.println("Received accept request from sector id: " + packet.sectorId);

            packet.configuration = new HashMap<>();
            NetworkAPI.Net.publish(Channel.ACCEPT, packet);

            NetworkAPI.Internal.addSector(packet.sectorId);
        });
    }
}
