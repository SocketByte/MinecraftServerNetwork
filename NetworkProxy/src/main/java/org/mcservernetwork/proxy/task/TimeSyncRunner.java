package org.mcservernetwork.proxy.task;

import org.mcservernetwork.commons.NetworkAPI;
import org.mcservernetwork.commons.net.Channel;
import org.mcservernetwork.commons.net.packet.PacketTimeSync;

public class TimeSyncRunner implements Runnable {

    private static short globalTime;
    private static long days;

    @Override
    public void run() {
        globalTime += 10;

        if (globalTime >= 24000) {
            globalTime = 0;
            days++;
        }

        PacketTimeSync packet = new PacketTimeSync();
        packet.time = globalTime;
        NetworkAPI.Net.publish(Channel.TIME_SYNC, packet);
    }

    public static int getGlobalTime() {
        return globalTime;
    }

    public static long getDays() {
        return days;
    }
}
