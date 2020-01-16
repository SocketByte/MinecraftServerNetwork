package org.mcservernetwork.client.io;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class BukkitSerializer {

    public static String serializeItems(ItemStack[] itemStacks) {
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            try (BukkitObjectOutputStream objectStream = new BukkitObjectOutputStream(stream)) {
                objectStream.writeObject(itemStacks);
            }
            return Base64.getEncoder().encodeToString(stream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Could not serialize itemstacks", e);
        }
    }

    public static ItemStack[] deserializeItems(String itemStacks) {
        byte[] decoded = Base64.getDecoder().decode(itemStacks);
        try (ByteArrayInputStream stream = new ByteArrayInputStream(decoded)) {
            try (BukkitObjectInputStream objectStream = new BukkitObjectInputStream(stream)) {
                return (ItemStack[]) objectStream.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Could not serialize itemstacks", e);
        }
    }

}
