package org.mcservernetwork.client.listener;

import org.mcservernetwork.client.Client;
import org.mcservernetwork.commons.NetworkAPI;
import org.mcservernetwork.commons.net.Channel;
import org.mcservernetwork.commons.net.packet.PacketPingPong;

public class StatusListener implements NetworkAPI.Net.Listener<PacketPingPong> {
    @Override
    public void receive(PacketPingPong packet) {
        packet.sectorName = Client.getCurrentSector().getSectorName();

        NetworkAPI.Net.publish(Channel.PONG, packet);
    }
}
