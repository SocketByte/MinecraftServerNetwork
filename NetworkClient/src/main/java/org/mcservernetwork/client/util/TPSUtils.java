package org.mcservernetwork.client.util;

import org.bukkit.Bukkit;
import org.mcservernetwork.client.util.reflection.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;

public class TPSUtils {

    private static final DecimalFormat FORMAT = new DecimalFormat("##.##");

    private static Object serverInstance;
    private static Field tpsField;

    static {
        String nmsver = ReflectionUtils.getNmsVersion();
        try {
            Class<?> minecraftServerClass = Class.forName("net.minecraft.server." + nmsver + ".MinecraftServer");
            serverInstance = minecraftServerClass.getMethod("getServer").invoke(null);
            tpsField = minecraftServerClass.getDeclaredField("recentTps");
        }
        catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("An error occurred while initializing TPS meter", e);
        }
        catch (NoSuchFieldException ex) {
            tpsField = null;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Could not find NMS class", e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Could not find MinecraftServer#getServer method", e);
        }
    }

    public static String getRecentTPS(int last) {
        try {
            return tpsField != null ? FORMAT.format(Math.min(20.0, ((double[]) tpsField.get(serverInstance))[last])) : "?";
        }
        catch (IllegalAccessException ex) {
            return "?";
        }
    }

}
