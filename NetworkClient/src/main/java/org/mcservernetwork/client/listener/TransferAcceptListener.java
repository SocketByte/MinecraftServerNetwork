package org.mcservernetwork.client.listener;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.mcservernetwork.client.io.BukkitSerializer;
import org.mcservernetwork.commons.NetworkAPI;
import org.mcservernetwork.commons.net.packet.PacketPlayerInfo;
import org.mcservernetwork.commons.net.packet.PacketTransferAccept;

import java.util.UUID;

public class TransferAcceptListener implements NetworkAPI.Net.Listener<PacketTransferAccept> {
    @Override
    public void receive(PacketTransferAccept packet) {
        PacketPlayerInfo info = packet.info;

        UUID playerId = UUID.fromString(packet.uniqueId);
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerId);

        Player player = offlinePlayer.getPlayer();
        if (player == null || !offlinePlayer.isOnline()) {
            // todo save player info
            return;
        }
        player.getInventory().setContents(BukkitSerializer.deserializeItems(info.inventoryContents));
        player.getInventory().setExtraContents(BukkitSerializer.deserializeItems(info.extraContents));
        player.getInventory().setArmorContents(BukkitSerializer.deserializeItems(info.armorContents));

        EntityType entityType = EntityType.valueOf(info.vehicleEntityType);
        Entity vehicle = player.getWorld().spawnEntity(player.getLocation(), entityType);
        vehicle.addPassenger(player);

        int y = (int)info.y;
        while (!player.getWorld().getBlockAt((int)info.x, y, (int)info.z)
                .getType().equals(Material.AIR)) {
            y++;
        }

        Location location = new Location(Bukkit.getWorld(info.world),
                info.x, y + 2, info.z, info.yaw, info.pitch);
        player.teleport(location);

        player.setAllowFlight(info.allowFlight);
        player.setDisplayName(info.displayName);
        player.setExhaustion(info.exhaustion);
        player.setExp(info.experience);
        player.setLevel(info.levels);
        player.setTotalExperience(info.totalExperience);
        player.setFlying(info.flying);
        player.setFlySpeed(info.flySpeed);
        player.setFoodLevel(info.foodLevel);
        player.setHealth(info.health);
        player.setHealthScale(info.healthScale);
        player.setAbsorptionAmount(info.absorptionAmount);
        player.setSneaking(info.sneaking);
        player.setWalkSpeed(info.walkSpeed);
        player.setFallDistance(info.fallDistance);
        player.setFireTicks(info.fireTicks);
        player.setMaximumAir(info.maximumAir);
        player.setMaximumNoDamageTicks(info.maximumAirNoDamageTicks);
        player.setGameMode(GameMode.valueOf(info.gameMode));
        player.setSprinting(info.sprinting);
        player.setFallDistance(info.fallDistance);
        player.setOp(info.op);
        player.setCanPickupItems(info.canPickupItems);
        player.setNoDamageTicks(info.noDamageTicks);
        player.getInventory().setHeldItemSlot(info.heldItemSlot);
        for (PacketPlayerInfo.SerializablePotionEffect effect : info.potionEffects) {
            PotionEffectType type = PotionEffectType.getByName(effect.type);
            if (type == null)
                continue;
            PotionEffect potionEffect = new PotionEffect(type,
                    effect.duration, effect.amplifier);
        }
    }
}
