package org.mcservernetwork.commons.net.packet;

import java.io.Serializable;

public class PacketWeatherSync extends Packet {

    public Weather weather;

    public enum Weather implements Serializable {
        CLEAR,
        THUNDERING,
        STORMING
    }

}
