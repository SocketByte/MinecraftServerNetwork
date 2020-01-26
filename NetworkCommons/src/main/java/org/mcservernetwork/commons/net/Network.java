package org.mcservernetwork.commons.net;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import org.mcservernetwork.commons.net.codec.NetworkFstCodec;
import org.mcservernetwork.commons.net.packet.Packet;

import java.util.*;

public class Network {

    private static final NetworkFstCodec codec = new NetworkFstCodec();

    static {
        codec.registerAll();
    }

    private final RedisClient client;
    private final StatefulRedisPubSubConnection<String, Packet> pubSub;
    private final StatefulRedisConnection<String, Packet> connection;

    private Map<String, Sector> sectors = new HashMap<>();

    public Network(String connectionPattern) {
        this.client = RedisClient.create(connectionPattern);
        this.pubSub = this.client.connectPubSub(codec);
        this.connection = this.client.connect(codec);
    }

    public void applySectors(Map<String, Sector> sectors) {
        this.sectors = sectors;
    }

    public void addSector(Sector sector) {
        this.sectors.put(sector.getSectorName(), sector);
    }

    public Map<String, Sector> getSectors() {
        return Collections.unmodifiableMap(this.sectors);
    }

    public RedisClient getClient() {
        return client;
    }

    public StatefulRedisConnection<String, Packet> connection() {
        return connection;
    }

    public StatefulRedisPubSubConnection<String, Packet> pubSub() {
        return pubSub;
    }
}
