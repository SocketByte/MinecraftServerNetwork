package org.mcservernetwork.client.listener;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.mcservernetwork.client.Client;
import org.mcservernetwork.commons.NetworkAPI;
import org.mcservernetwork.commons.net.packet.PacketWeatherSync;

public class WeatherSyncListener implements NetworkAPI.Net.Listener<PacketWeatherSync> {
    @Override
    public void receive(PacketWeatherSync packet) {
        Bukkit.getScheduler().runTask(Client.getInstance(), () -> {
            for (World world : Bukkit.getWorlds()) {
                if (world.getEnvironment() != World.Environment.NORMAL)
                    continue;
                switch (packet.weather) {
                    case CLEAR:
                        world.setThundering(false);
                        world.setStorm(false);
                        break;
                    case STORMING:
                        world.setThundering(false);
                        world.setStorm(true);
                        break;
                    case THUNDERING:
                        world.setThundering(true);
                        world.setStorm(true);
                        break;
                }
            }
        });
    }
}
