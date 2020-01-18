package org.mcservernetwork.proxy.util;

import net.md_5.bungee.api.ChatColor;

public class ColorUtils {

    public static String fixColors(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

}
