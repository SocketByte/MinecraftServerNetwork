package org.mcservernetwork.commons.listener;

import org.mcservernetwork.commons.KeepAliveHandler;
import org.mcservernetwork.commons.NetworkAPI;
import org.mcservernetwork.commons.net.packet.PacketStatus;

public class StatusListener implements NetworkAPI.Net.Listener<PacketStatus> {
    @Override
    public void receive(PacketStatus packet) {
        KeepAliveHandler.set(packet);
    }
}
