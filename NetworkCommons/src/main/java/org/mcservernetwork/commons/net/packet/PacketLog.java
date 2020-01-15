package org.mcservernetwork.commons.net.packet;

import org.mcservernetwork.commons.net.NetworkLogger;

public class PacketLog extends Packet {

    public String sender;
    public String message;
    public NetworkLogger.LogSeverity severity;

}
