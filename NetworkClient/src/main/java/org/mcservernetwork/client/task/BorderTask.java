package org.mcservernetwork.client.task;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.mcservernetwork.client.Client;
import org.mcservernetwork.client.util.BlockUtils;
import org.mcservernetwork.client.util.SectorLocationUtils;
import org.mcservernetwork.commons.net.Sector;

import java.util.List;

public class BorderTask implements Runnable {
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            double distance = SectorLocationUtils.distance(player.getLocation());
            if (distance > SectorLocationUtils.MAX_DISTANCE)
                continue;

            List<Location> sphere = BlockUtils.sphere(player.getLocation(), 7, 0, false, true, 1);
            for (Location loc : sphere) {
                int x = loc.getBlockX();
                int y = loc.getBlockY();
                int z = loc.getBlockZ();
                Sector current = Client.getCurrentSector();

                if (x == current.getMaxX() || x == current.getMinX()
                        || z == current.getMaxZ() || z == current.getMinZ()) {
                    if (loc.getBlock().getType() != Material.AIR)
                        continue;
                    player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, loc,
                            1, 0.3, 0.3, 0.3, 0,
                            null, false);
                }
            }
        }
    }
}
