package org.mcservernetwork.client.listener;

import org.mcservernetwork.client.ClientStatusHandler;
import org.mcservernetwork.commons.NetworkAPI;
import org.mcservernetwork.commons.net.packet.PacketStatus;

public class StatusListener implements NetworkAPI.Net.Listener<PacketStatus> {
    @Override
    public void receive(PacketStatus packet) {
        ClientStatusHandler.set(packet);
    }
}
