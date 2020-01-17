package org.mcservernetwork.client.util;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class BlockUtils {

    /*
     * Made by dzikoysk
     * https://github.com/FunnyGuilds/FunnyGuilds/blob/master/src/main/java/net/dzikoysk/funnyguilds/util/commons/bukkit/SpaceUtils.java
     */
    public static List<Location> sphere(Location loc, int radius, int height, boolean hollow, boolean sphere, int plusY) {
        List<Location> circleBlocks = new ArrayList<>();
        int explosionX = loc.getBlockX();
        int explosionY = loc.getBlockY();
        int explosionZ = loc.getBlockZ();

        for (int x = explosionX - radius; x <= explosionX + radius; x++) {
            for (int z = explosionZ - radius; z <= explosionZ + radius; z++) {
                for (int y = (sphere ? explosionY - radius : explosionY); y < (sphere ? explosionY + radius : explosionY + height); y++) {
                    double dist = (explosionX - x) * (explosionX - x) + (explosionZ - z) * (explosionZ - z) + (sphere ? (explosionY - y) * (explosionY - y) : 0);

                    if (dist < radius * radius && !(hollow && dist < (radius - 1) * (radius - 1))) {
                        Location l = new Location(loc.getWorld(), x, y + plusY, z);
                        circleBlocks.add(l);
                    }
                }
            }
        }

        return circleBlocks;
    }


}
