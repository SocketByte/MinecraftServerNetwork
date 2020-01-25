package org.mcservernetwork.commons;

import org.mcservernetwork.commons.net.packet.PacketStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class KeepAliveHandler {

    private static final ScheduledExecutorService service = Executors.newScheduledThreadPool(4);
    private static Map<String, PacketStatus> status = new HashMap<>();

    public static void set(PacketStatus packet) {
        status.put(packet.sectorName, packet);
    }

    public static void run(Runnable runnable) {
        service.scheduleAtFixedRate(runnable, 1, 1, TimeUnit.SECONDS);
    }

    public static PacketStatus get(String sectorName) {
        long duration = getDurationSinceLastPacket(sectorName);

        if (duration >= 3000 || duration == -1)
            return null;

        return status.get(sectorName);
    }

    public static long getDurationSinceLastPacket(String sectorName) {
        PacketStatus packet = status.get(sectorName);
        if (packet == null)
            return -1;

        return System.currentTimeMillis() - packet.timestamp;
    }

}
