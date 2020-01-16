package org.mcservernetwork.commons.net.packet;

import java.io.Serializable;
import java.util.List;

public class PacketPlayerInfo extends Packet {

    public String inventoryContents;
    public String extraContents;
    public String armorContents;

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

    public int fallDistance;

    public int foodLevel;
    public double health;
    public float healthScale;

    public boolean sneaking;
    public boolean sprinting;

    public float experience;
    public int levels;
    public int totalExperience;

    public int exhaustion;
    public float saturation;
    public int absorptionAmount;

    public boolean allowFlight;
    public boolean flying;
    public boolean canPickupItems;
    public float flySpeed;
    public float walkSpeed;

    public String vehicleEntityType;

    public List<SerializablePotionEffect> potionEffects;

    public static class SerializablePotionEffect implements Serializable {
        public String type;
        public int duration;
        public int amplifier;
    }
}
