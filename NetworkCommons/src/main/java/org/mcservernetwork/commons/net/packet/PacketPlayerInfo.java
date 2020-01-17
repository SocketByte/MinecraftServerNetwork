package org.mcservernetwork.commons.net.packet;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class PacketPlayerInfo extends Packet {

    public byte[] inventoryContents;
    public byte[] extraContents;
    public byte[] armorContents;

    public int heldItemSlot;

    public String world;
    public double x;
    public double y;
    public double z;
    public float yaw;
    public float pitch;

    public String displayName;

    public int fireTicks;
    public int noDamageTicks;

    public String gameMode;

    public int maximumAir;
    public int maximumAirNoDamageTicks;
    public int remainingAir;

    public boolean op;

    public float fallDistance;

    public int foodLevel;
    public double health;
    public double healthScale;

    public boolean sneaking;
    public boolean sprinting;

    public float experience;
    public int levels;
    public int totalExperience;

    public float exhaustion;
    public float saturation;
    public double absorptionAmount;

    public boolean allowFlight;
    public boolean flying;
    public boolean canPickupItems;
    public float flySpeed;
    public float walkSpeed;

    public String vehicleEntityType;

    public SerializablePotionEffect[] potionEffects;

    public static class SerializablePotionEffect implements Serializable {
        public String type;
        public int duration;
        public int amplifier;

        @Override
        public String toString() {
            return "SerializablePotionEffect{" +
                    "type='" + type + '\'' +
                    ", duration=" + duration +
                    ", amplifier=" + amplifier +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "PacketPlayerInfo{" +
                "inventoryContents=" + Arrays.toString(inventoryContents) +
                ", extraContents=" + Arrays.toString(extraContents) +
                ", armorContents=" + Arrays.toString(armorContents) +
                ", heldItemSlot=" + heldItemSlot +
                ", world='" + world + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", yaw=" + yaw +
                ", pitch=" + pitch +
                ", displayName='" + displayName + '\'' +
                ", fireTicks=" + fireTicks +
                ", noDamageTicks=" + noDamageTicks +
                ", gameMode='" + gameMode + '\'' +
                ", maximumAir=" + maximumAir +
                ", maximumAirNoDamageTicks=" + maximumAirNoDamageTicks +
                ", remainingAir=" + remainingAir +
                ", op=" + op +
                ", fallDistance=" + fallDistance +
                ", foodLevel=" + foodLevel +
                ", health=" + health +
                ", healthScale=" + healthScale +
                ", sneaking=" + sneaking +
                ", sprinting=" + sprinting +
                ", experience=" + experience +
                ", levels=" + levels +
                ", totalExperience=" + totalExperience +
                ", exhaustion=" + exhaustion +
                ", saturation=" + saturation +
                ", absorptionAmount=" + absorptionAmount +
                ", allowFlight=" + allowFlight +
                ", flying=" + flying +
                ", canPickupItems=" + canPickupItems +
                ", flySpeed=" + flySpeed +
                ", walkSpeed=" + walkSpeed +
                ", vehicleEntityType='" + vehicleEntityType + '\'' +
                ", potionEffects=" + Arrays.toString(potionEffects) +
                '}';
    }
}
