package org.mcservernetwork.proxy.listener;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.mcservernetwork.commons.NetworkAPI;
import org.mcservernetwork.commons.net.packet.PacketPingPong;
import org.mcservernetwork.proxy.Proxy;

public class StatusListener implements NetworkAPI.Net.Listener<PacketPingPong> {
    @Override
    public void receive(PacketPingPong packet) {
        ProxiedPlayer player = Proxy.getInstance().getProxy().getPlayer(packet.sender);
        if (player == null) {
            CommandSender sender = Proxy.getInstance().getProxy().getConsole();
            sender.sendMessage(new ComponentBuilder(packet.sectorName + " is alive!")
                    .color(ChatColor.GREEN).create());
            return;
        }
        player.sendMessage(new ComponentBuilder(packet.sectorName + " is alive!")
                .color(ChatColor.GREEN).create());
    }
}
