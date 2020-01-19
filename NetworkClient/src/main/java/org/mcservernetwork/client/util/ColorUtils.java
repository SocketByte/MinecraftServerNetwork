package org.mcservernetwork.client.util;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class ColorUtils {

    public static String fixColors(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static List<String> fixColors(List<String> text) {
        List<String> fixed = new ArrayList<>();
        for (String str : text) {
            fixed.add(fixColors(str));
        }
        return fixed;
    }

    public static List<String> fixColors(String... text) {
        List<String> fixed = new ArrayList<>();
        for (String str : text) {
            fixed.add(fixColors(str));
        }
        return fixed;
    }

}
