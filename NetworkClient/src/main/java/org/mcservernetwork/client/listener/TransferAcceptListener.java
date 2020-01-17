package org.mcservernetwork.client.listener;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.mcservernetwork.client.io.BukkitSerializer;
import org.mcservernetwork.client.util.PlayerUtils;
import org.mcservernetwork.commons.NetworkAPI;
import org.mcservernetwork.commons.net.packet.PacketPlayerInfo;
import org.mcservernetwork.commons.net.packet.PacketTransfer;

import java.util.UUID;

public class TransferAcceptListener implements NetworkAPI.Net.Listener<PacketTransfer> {
    @Override
    public void receive(PacketTransfer packet) {
        PacketPlayerInfo info = packet.info;

        UUID playerId = UUID.fromString(packet.uniqueId);
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerId);

        Player player = offlinePlayer.getPlayer();
        if (player == null || !offlinePlayer.isOnline()) {
            // todo save player info
            return;
        }

        PlayerUtils.unwrap(player, info);
    }
}
