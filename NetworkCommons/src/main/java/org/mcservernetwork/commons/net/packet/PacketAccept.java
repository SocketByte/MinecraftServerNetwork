package org.mcservernetwork.commons.net.packet;

import org.mcservernetwork.commons.net.Sector;

import java.util.Map;

public class PacketAccept extends Packet {

    public String sectorName;

    public Map<String, Sector> sectors;

}
