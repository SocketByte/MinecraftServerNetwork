package com.connorlinfoot.actionbarapi;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.mcservernetwork.client.Client;
import org.mcservernetwork.client.util.ColorUtils;
import org.mcservernetwork.client.util.reflection.ReflectionUtils;
import pl.socketbyte.opengui.ColorUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ActionBarAPI {
    private static String nms = Bukkit.getServer().getClass().getPackage().getName().substring(Bukkit.getServer().getClass().getPackage().getName().lastIndexOf(".") + 1);
    private static boolean useOldMethods = nms.startsWith("v1_8_") || nms.startsWith("v1_7_");

    private static Class<?> craftPlayerClass;
    private static Class<?> packetPlayOutChatClass;
    private static Class<?> packetClass;
    private static Class<?> chatSerializerClass;
    private static Class<?> iChatBaseComponentClass;
    private static Method craftPlayerHandleMethod;
    private static Method m3;

    // 1.8+
    private static Class<?> chatComponentTextClass;
    private static Class<?> chatMessageTypeClass;
    private static Object chatMessageType;

    static {
        try {
            craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + nms + ".entity.CraftPlayer");
            packetPlayOutChatClass = Class.forName("net.minecraft.server." + nms + ".PacketPlayOutChat");
            packetClass = Class.forName("net.minecraft.server." + nms + ".Packet");
            iChatBaseComponentClass = Class.forName("net.minecraft.server." + nms + ".IChatBaseComponent");
            chatSerializerClass = iChatBaseComponentClass.getDeclaredClasses()[0];
            craftPlayerHandleMethod = craftPlayerClass.getDeclaredMethod("getHandle");

            if (useOldMethods) {
                m3 = chatSerializerClass.getDeclaredMethod("a", String.class);
            }
            else {
                chatComponentTextClass = Class.forName("net.minecraft.server." + nms + ".ChatComponentText");
                iChatBaseComponentClass = Class.forName("net.minecraft.server." + nms + ".IChatBaseComponent");

                chatMessageTypeClass = Class.forName("net.minecraft.server." + nms + ".ChatMessageType");
                Object[] chatMessageTypes = chatMessageTypeClass.getEnumConstants();

                for (Object obj : chatMessageTypes) {
                    if (obj.toString().equals("GAME_INFO")) {
                        chatMessageType = obj;
                    }
                }
            }
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            throw new RuntimeException("Could not properly setup ActionBar", e);
        }
    }

    public static void sendActionBar(Player player, String message) {
        if (!player.isOnline()) {
            return;
        }

        try {
            Object craftPlayer = craftPlayerClass.cast(player);
            Object packet;

            if (useOldMethods) {
                Object cbc = iChatBaseComponentClass.cast(m3.invoke(chatSerializerClass, "{\"text\": \"" + ColorUtils.fixColors(message) + "\"}"));
                packet = packetPlayOutChatClass.getConstructor(new Class<?>[]{iChatBaseComponentClass, byte.class}).newInstance(cbc, (byte) 2);
            }
            else {
                Object chatComponentText = chatComponentTextClass.getConstructor(new Class<?>[]{String.class}).newInstance(ColorUtils.fixColors(message));
                packet = packetPlayOutChatClass.getConstructor(new Class<?>[]{iChatBaseComponentClass, chatMessageTypeClass == null ? byte.class : chatMessageTypeClass}).newInstance(chatComponentText, chatMessageType == null ? ((byte) 2) : chatMessageType);
            }

            Object craftPlayerHandle = craftPlayerHandleMethod.invoke(craftPlayer);
            Field playerConnectionField = craftPlayerHandle.getClass().getDeclaredField("playerConnection");
            Object playerConnection = playerConnectionField.get(craftPlayerHandle);
            Method sendPacketMethod = playerConnection.getClass().getDeclaredMethod("sendPacket", packetClass);
            sendPacketMethod.invoke(playerConnection, packet);
        } catch (Exception e) {
            throw new RuntimeException("Could not send ActionBar", e);
        }
    }
}
