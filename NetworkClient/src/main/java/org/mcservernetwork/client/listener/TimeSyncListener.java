package org.mcservernetwork.client.listener;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.mcservernetwork.client.Client;
import org.mcservernetwork.commons.NetworkAPI;
import org.mcservernetwork.commons.net.packet.PacketTimeSync;

public class TimeSyncListener implements NetworkAPI.Net.Listener<PacketTimeSync> {
    @Override
    public void receive(PacketTimeSync packet) {
        Bukkit.getScheduler().runTask(Client.getInstance(), () -> {
            for (World world : Bukkit.getWorlds()) {
                if (world.getEnvironment() != World.Environment.NORMAL)
                    continue;
                world.setFullTime(packet.time);
            }
        });
    }
}
