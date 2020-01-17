package org.mcservernetwork.client.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Stack;

public class LocationUtils {

    public static void teleport(Player player, World world, int x, int z, int originalY, float yaw, float pitch) {
        int y = determineSafeY(world, x, z);

        if (originalY > y)
            y = originalY;

        Location location = new Location(world, x, y, z, yaw, pitch);
        player.teleport(location);
    }

    private static int determineSafeY(World world, int x, int z) {
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < 256; i++) {
            Block block = world.getBlockAt(x, i, z);
            Material type = block.getType();

            if (type == Material.AIR) {
                if (stack.isEmpty())
                    stack.push(i);
            }
            else if (!stack.isEmpty()
                    && (type.name().contains("LEAVES")))
                stack.pop();
        }
        return stack.pop();
    }

}
