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
        public static Map<String, Sector> getSectors() {
            return NetworkAPI.Internal.getNetwork().getSectors();
        }

        public static Sector getSector(String name) {
            return NetworkAPI.Internal.getNetwork().getSectors().get(name);
        }

        public static boolean isSectorAvailable(String name) {
            return NetworkAPI.Internal.getNetwork().getSectors().containsKey(name);
        }
    }

    public static class Net {
        public static void publish(Channel channel, Packet packet) {
            NetworkAPI.Internal.publishAsync(channel, packet);
        }

        public static void publish(String channel, Packet packet) {
            NetworkAPI.Internal.publishAsync(channel, packet);
        }

        public static void unsubscribe(Channel channel) {
            NetworkAPI.Internal.getNetwork().pubSub().sync().unsubscribe(channel.name());
        }

        public static void unsubscribe(String channel) {
            NetworkAPI.Internal.getNetwork().pubSub().sync().unsubscribe(channel);
        }

        public static void disconnect() {
            NetworkAPI.Internal.getNetwork().pubSub().close();
            NetworkAPI.Internal.getNetwork().connection().close();
        }

        @SuppressWarnings("unchecked")
        public static <T extends Packet> void subscribeAndListen(Channel channel, Class<T> type, Listener<T> listener) {
            NetworkAPI.Internal.subscribeAndListen(channel, new RedisPubSubAdapter<String, Packet>() {
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

        @SuppressWarnings("unchecked")
        public static <T extends Packet> void listen(String channel, Class<T> type, Listener<T> listener) {
            NetworkAPI.Internal.listen(new RedisPubSubAdapter<String, Packet>() {
                @Override
                public void message(String ch, Packet message) {
                    if (!ch.equals(channel))
                        return;
                    if (!type.isAssignableFrom(message.getClass()))
                        return;

                    listener.receive((T) message);
                }
            });
        }

        public static <T extends Packet> void listen(Channel channel, Class<T> type, Listener<T> listener) {
            listen(channel.name(), type, listener);
        }

        public static void subscribe(Channel channel) {
            NetworkAPI.Internal.getNetwork().pubSub().sync().subscribe(channel.name());
        }

        public static void subscribe(String channel) {
            NetworkAPI.Internal.getNetwork().pubSub().sync().subscribe(channel);
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

        public static void addSector(Sector sector) {
            network.addSector(sector);
        }

        public static void applySectors(Map<String, Sector> sectors) {
            network.applySectors(sectors);
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

        public static void listen(RedisPubSubAdapter<String, Packet> adapter) {
            network.pubSub().addListener(adapter);
        }

        public static void subscribeAndListen(Channel channel, RedisPubSubAdapter<String, Packet> adapter) {
            network.pubSub().addListener(adapter);
            network.pubSub().sync().subscribe(channel.name());
        }

        public static void subscribeAndListen(String channel, RedisPubSubAdapter<String, Packet> adapter) {
            network.pubSub().addListener(adapter);
            network.pubSub().sync().subscribe(channel);
        }

        public static void createNetwork(String connectionPattern) {
            network = new Network(connectionPattern);
        }
    }

}
