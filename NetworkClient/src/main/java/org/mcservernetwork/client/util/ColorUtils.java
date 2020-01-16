package org.mcservernetwork.client.util;

import org.bukkit.ChatColor;

public class ColorUtils {

    public static String fixColors(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

}
