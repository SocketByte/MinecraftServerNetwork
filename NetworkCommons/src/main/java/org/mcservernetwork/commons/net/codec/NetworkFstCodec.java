package org.mcservernetwork.commons.net.codec;

import io.lettuce.core.codec.RedisCodec;
import org.mcservernetwork.commons.net.packet.Packet;
import org.nustaq.serialization.FSTConfiguration;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * Default safe network codec
 */
public class NetworkFstCodec implements RedisCodec<String, Packet> {
    private final Charset charset = StandardCharsets.UTF_8;
    private final FSTConfiguration configuration = FSTConfiguration.createDefaultConfiguration();

    @Override
    public String decodeKey(ByteBuffer bytes) {
        return charset.decode(bytes).toString();
    }

    @Override
    public Packet decodeValue(ByteBuffer bytes) {
        byte[] buffer = new byte[bytes.limit()];
        bytes.get(buffer);
        return (Packet) configuration.asObject(buffer);
    }

    @Override
    public ByteBuffer encodeKey(String key) {
        return charset.encode(key);
    }

    @Override
    public ByteBuffer encodeValue(Packet value) {
        return ByteBuffer.wrap(configuration.asByteArray(value));
    }
}
