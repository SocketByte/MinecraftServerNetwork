package org.mcservernetwork.client;

import org.mcservernetwork.client.util.ColorUtils;

import java.util.HashMap;
import java.util.Map;

public class Configuration {

    private static Map<String, Object> configuration = new HashMap<>();

    public static void set(Map<String, Object> configurationMap) {
        configuration = configurationMap;
    }

    public static Object get(String key) {
        return configuration.get(key);
    }

    public static int asInt(String key) {
        return (int) get(key);
    }

    public static double asDouble(String key) {
        return (double) get(key);
    }

    public static float asFloat(String key) {
        return (float) get(key);
    }

    public static boolean asBoolean(String key) {
        return Boolean.parseBoolean(get(key).toString());
    }

    public static String asMessage(String key) {
        return ColorUtils.fixColors(get(key).toString());
    }

}
