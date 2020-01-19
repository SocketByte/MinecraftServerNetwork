package pl.socketbyte.opengui;

import org.bukkit.ChatColor;

import java.util.List;
import java.util.stream.Collectors;

public class ColorUtil {

    public static String fixColor(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public static List<String> fixColor(List<String> stringList) {
        return stringList.stream().map(ColorUtil::fixColor)
                .collect(Collectors.toList());
    }

    public static String[] fixColor(String[] strings) {
        String[] stringArray = new String[strings.length];
        for (int i = 0; i < strings.length; i++) {
            stringArray[i] = fixColor(strings[i]);
        }
        return stringArray;
    }

}
