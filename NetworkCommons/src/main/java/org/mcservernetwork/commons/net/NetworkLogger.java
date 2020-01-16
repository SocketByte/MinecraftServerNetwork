package org.mcservernetwork.commons.net;

import io.lettuce.core.pubsub.RedisPubSubAdapter;
import org.mcservernetwork.commons.NetworkAPI;
import org.mcservernetwork.commons.net.packet.Packet;
import org.mcservernetwork.commons.net.packet.PacketLog;

public class NetworkLogger {

    private final String prefix;

    private boolean debugging;

    public NetworkLogger(String prefix) {
        this.prefix = prefix;
    }

    public void listen() {
        NetworkAPI.Net.subscribeAndListen(Channel.LOGGER, PacketLog.class, packet -> {
            System.out.println("[" + packet.sender + "] (" + packet.severity + ") " + packet.message);
        });
    }

    public void enableDebugging() {
        this.debugging = true;
    }

    public void log(String message, LogSeverity severity) {
        if (severity == LogSeverity.DEBUG) {
            if (!debugging)
                return;
        }
        PacketLog packet = new PacketLog();
        packet.message = message;
        packet.severity = severity;
        packet.sender = this.prefix;

        NetworkAPI.Net.publish(Channel.LOGGER, packet);
    }

    public enum LogSeverity {
        INFO, WARN, ERROR, FATAL, DEBUG
    }

}
