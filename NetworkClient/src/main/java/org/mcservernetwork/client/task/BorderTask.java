package org.mcservernetwork.client.task;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.mcservernetwork.client.Client;
import org.mcservernetwork.client.util.BlockUtils;
import org.mcservernetwork.client.util.SectorLocationUtils;
import org.mcservernetwork.commons.net.Sector;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BorderTask implements Runnable {

    public void spawnParticles(Player player, List<Location> locations, Particle particle, int count) {
        for (Location loc : locations) {
            int x = loc.getBlockX();
            int y = loc.getBlockY();
            int z = loc.getBlockZ();
            Sector current = Client.getCurrentSector();

            if (x == current.getMaxX() || x == current.getMinX()
                    || z == current.getMaxZ() || z == current.getMinZ()) {
                if (loc.getBlock().getType() != Material.AIR)
                    continue;
                player.getWorld().spawnParticle(particle, loc,
                        count, 0.3, 0.3, 0.3, 0,
                        null, false);
            }
        }
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            double distance = SectorLocationUtils.distance(player.getLocation());
            if (distance > SectorLocationUtils.MAX_DISTANCE)
                continue;

            List<Location> sphere = BlockUtils.sphere(player.getLocation(), 5, 0, false, true, 1);
            List<Location> hollow = BlockUtils.sphere(player.getLocation(), 6, 0, true, true, 1);

            spawnParticles(player, sphere, Particle.ENCHANTMENT_TABLE, 2);
            spawnParticles(player, sphere, Particle.PORTAL, 6);
            spawnParticles(player, hollow, Particle.CLOUD, 1);
        }
    }
}
