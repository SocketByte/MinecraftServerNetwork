package org.mcservernetwork.client.util.reflection;

import org.bukkit.Bukkit;

public class ReflectionUtils {

    private static String nmsver;

    static {
        nmsver = Bukkit.getServer().getClass().getPackage().getName();
        nmsver = nmsver.substring(nmsver.lastIndexOf(".") + 1);
    }

    public static String getNmsVersion() {
        return nmsver;
    }
}
