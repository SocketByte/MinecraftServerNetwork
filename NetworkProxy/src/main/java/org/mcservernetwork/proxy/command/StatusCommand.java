package org.mcservernetwork.proxy.command;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Command;
import org.mcservernetwork.commons.NetworkAPI;
import org.mcservernetwork.commons.net.Channel;
import org.mcservernetwork.commons.net.packet.Packet;
import org.mcservernetwork.commons.net.packet.PacketPingPong;

public class StatusCommand extends Command {
    public StatusCommand() {
        super("status", "mcservernetwork.proxy.status",
                "st", "sectors", "sectorstatus", "sst");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        PacketPingPong packet = new PacketPingPong();
        packet.sender = sender.getName();
        NetworkAPI.Net.publish(Channel.PING, packet);
    }
}
