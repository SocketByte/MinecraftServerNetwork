package org.mcservernetwork.client.util;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.mcservernetwork.client.Client;
import org.mcservernetwork.client.io.BukkitSerializer;
import org.mcservernetwork.commons.net.packet.PacketPlayerInfo;

import java.util.ArrayList;
import java.util.List;

public class PlayerUtils {

    public static PacketPlayerInfo wrap(Player player) {
        return wrap(player, player.getLocation());
    }

    public static PacketPlayerInfo wrap(Player player, Location location) {
        PacketPlayerInfo info = new PacketPlayerInfo();

        info.inventoryContents = BukkitSerializer.serializeItems(player.getInventory().getContents());
        info.extraContents = BukkitSerializer.serializeItems(player.getInventory().getExtraContents());
        info.armorContents = BukkitSerializer.serializeItems(player.getInventory().getArmorContents());
        info.enderContents = BukkitSerializer.serializeItems(player.getEnderChest().getContents());

        info.vehicleEntityType = player.getVehicle() != null ? player.getVehicle().getType().name() : null;

        info.x = location.getBlockX();
        info.y = location.getBlockY();
        info.z = location.getBlockZ();
        info.yaw = location.getYaw();
        info.pitch = location.getPitch();
        info.world = location.getWorld().getName();

        info.allowFlight = player.getAllowFlight();
        info.displayName = player.getDisplayName();
        info.exhaustion = player.getExhaustion();
        info.experience = player.getExp();
        info.levels = player.getLevel();
        info.totalExperience = player.getTotalExperience();
        info.flying = player.isFlying();
        info.flySpeed = player.getFlySpeed();
        info.foodLevel = player.getFoodLevel();
        info.health = player.getHealth();
        info.healthScale = player.getHealthScale();
        info.absorptionAmount = player.getAbsorptionAmount();
        info.sneaking = player.isSneaking();
        info.walkSpeed = player.getWalkSpeed();
        info.fallDistance = player.getFallDistance();
        info.fireTicks = player.getFireTicks();
        info.maximumAir = player.getMaximumAir();
        info.maximumAirNoDamageTicks = player.getMaximumNoDamageTicks();
        info.gameMode = player.getGameMode().name();
        info.sprinting = player.isSprinting();
        info.op = player.isOp();
        info.canPickupItems = player.getCanPickupItems();
        info.noDamageTicks = player.getNoDamageTicks();
        info.heldItemSlot = player.getInventory().getHeldItemSlot();

        PacketPlayerInfo.SerializablePotionEffect[] effects = new PacketPlayerInfo.SerializablePotionEffect
                [player.getActivePotionEffects().size()];
        int i = 0;
        for (PotionEffect effect : player.getActivePotionEffects()) {
            PacketPlayerInfo.SerializablePotionEffect potionEffect =
                    new PacketPlayerInfo.SerializablePotionEffect();

            potionEffect.type = effect.getType().getName();
            potionEffect.amplifier = effect.getAmplifier();
            potionEffect.duration = effect.getDuration();

            effects[i] = potionEffect;
            i++;
        }

        info.potionEffects = effects;
        return info;
    }

    public static void unwrap(Player player, PacketPlayerInfo info) {
        try {
            player.getInventory().setContents(BukkitSerializer.deserializeItems(info.inventoryContents));
            player.getInventory().setExtraContents(BukkitSerializer.deserializeItems(info.extraContents));
            player.getInventory().setArmorContents(BukkitSerializer.deserializeItems(info.armorContents));
            player.getEnderChest().setContents(BukkitSerializer.deserializeItems(info.enderContents));
        } catch (RuntimeException e) {
            System.out.println("Player " + player.getName() + " inventory was corrupted.");
        }

        if (info.vehicleEntityType != null) {
            EntityType entityType = EntityType.valueOf(info.vehicleEntityType);

            Bukkit.getScheduler().runTaskLater(Client.getInstance(), () -> {
                Entity vehicle = player.getWorld().spawnEntity(player.getLocation(), entityType);
                vehicle.addPassenger(player);
            }, 15);
        }

        LocationUtils.teleport(player, Bukkit.getWorld(info.world),
                (int)info.x, (int)info.z, (int)info.y, info.yaw, info.pitch);

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
        player.setOp(info.op);
        player.setCanPickupItems(info.canPickupItems);
        player.setNoDamageTicks(info.noDamageTicks);
        player.getInventory().setHeldItemSlot(info.heldItemSlot);

        for (PotionEffect effect : player.getActivePotionEffects())
            player.removePotionEffect(effect.getType());

        for (PacketPlayerInfo.SerializablePotionEffect effect : info.potionEffects) {
            PotionEffectType type = PotionEffectType.getByName(effect.type);
            if (type == null)
                continue;

            PotionEffect potionEffect = new PotionEffect(type,
                    effect.duration, effect.amplifier);

            player.addPotionEffect(potionEffect);
        }

    }

}
