package org.mcservernetwork.client.util;

import org.bukkit.Location;
import org.bukkit.World;
import org.mcservernetwork.client.Client;
import org.mcservernetwork.commons.NetworkAPI;
import org.mcservernetwork.commons.net.Sector;

import java.util.*;

public class SectorLocationUtils {

    public static final double MAX_DISTANCE = 30.0;

    public static Sector getNearest(Location location) {
        int offset = 5;
        double dist = distance(location) + offset;

        World world = location.getWorld();
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        Set<Sector> sectorsInRadius = new HashSet<>();
        sectorsInRadius.add(getSectorIn(new Location(world, x + dist, y, z)));
        sectorsInRadius.add(getSectorIn(new Location(world, x - dist, y, z)));
        sectorsInRadius.add(getSectorIn(new Location(world, x, y, z + dist)));
        sectorsInRadius.add(getSectorIn(new Location(world, x, y, z - dist)));

        for (Sector sector : sectorsInRadius) {
            if (sector != null)
                return sector;
        }
        return null;
    }

    public static double distance(Location location) {
        Sector sector = Client.getCurrentSector();
        return Math.min(
                Math.min(Math.abs(sector.getMinX() - location.getX()),
                        Math.abs(sector.getMaxX() - location.getX())),
                Math.min(Math.abs(sector.getMinZ() - location.getZ()),
                        Math.abs(sector.getMaxZ() - location.getZ()))) + 1.0;
    }

    public static double progress(double distance) {
        return 1 - (distance / MAX_DISTANCE);
    }

    public static Sector getSectorIn(Location location) {
        for (Sector sector : NetworkAPI.Sectors.getSectors().values()) {
            if (isIn(location, sector) && !sector.equals(Client.getCurrentSector()))
                return sector;
        }
        return null;
    }

    public static boolean isIn(Location location, Sector sector) {
        Location lower = new Location(location.getWorld(), sector.getMinX(), 0, sector.getMinZ());
        Location upper = new Location(location.getWorld(), sector.getMaxX(), 256, sector.getMaxZ());

        return location.getBlockX() > lower.getBlockX()
                && location.getBlockX() < upper.getBlockX()
                && location.getBlockY() > lower.getBlockY()
                && location.getBlockY() < upper.getBlockY()
                && location.getBlockZ() > lower.getBlockZ()
                && location.getBlockZ() < upper.getBlockZ();
    }

}
