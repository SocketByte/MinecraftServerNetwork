package org.mcservernetwork.commons;

import io.lettuce.core.pubsub.RedisPubSubAdapter;
import org.mcservernetwork.commons.net.Channel;
import org.mcservernetwork.commons.net.Network;
import org.mcservernetwork.commons.net.Sector;
import org.mcservernetwork.commons.net.packet.Packet;

import java.util.Collection;
import java.util.Map;

public class NetworkAPI {

    public static class Sectors {
        public static Map<Integer, Sector> getSectors() {
            return NetworkAPI.Internal.getNetwork().getSectors();
        }

        public static Sector getSector(int id) {
            return NetworkAPI.Internal.getNetwork().getSectors().get(id);
        }

        public static boolean isSectorAvailable(int id) {
            return NetworkAPI.Internal.getNetwork().getSectors().containsKey(id);
        }
    }

    public static class Net {
        public static void publish(Channel channel, Packet packet) {
            NetworkAPI.Internal.publishAsync(channel, packet);
        }

        @SuppressWarnings("unchecked")
        public static <T extends Packet> void subscribe(Channel channel, Class<T> type, Listener<T> listener) {
            NetworkAPI.Internal.subscribe(channel, new RedisPubSubAdapter<String, Packet>() {
                @Override
                public void message(String ch, Packet message) {
                    if (!ch.equals(channel.toString()))
                        return;
                    if (!type.isAssignableFrom(message.getClass()))
                        return;

                    listener.receive((T) message);
                }
            });
        }

        public interface Listener<T extends Packet> {
            void receive(T packet);
        }
    }

    public static class Internal {
        private static Network network;

        public static Network getNetwork() {
            return network;
        }

        public static void addSector(int sectorId) {
            network.addSector(sectorId);
        }

        public static void publishAsync(Channel channel, Packet packet) {
            network.connection().async().publish(channel.name(), packet);
        }

        public static void publishSync(Channel channel, Packet packet) {
            network.connection().sync().publish(channel.name(), packet);
        }

        public static void publishAsync(String channel, Packet packet) {
            network.connection().async().publish(channel, packet);
        }

        public static void publishSync(String channel, Packet packet) {
            network.connection().sync().publish(channel, packet);
        }

        public static void subscribe(Channel channel, RedisPubSubAdapter<String, Packet> adapter) {
            network.pubSub().addListener(adapter);
            network.pubSub().sync().subscribe(channel.name());
        }

        public static void subscribe(String channel, RedisPubSubAdapter<String, Packet> adapter) {
            network.pubSub().addListener(adapter);
            network.pubSub().sync().subscribe(channel);
        }

        public static void createNetwork(String connectionPattern) {
            network = new Network(connectionPattern);
        }
    }

}
