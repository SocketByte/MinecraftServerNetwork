package org.mcservernetwork.proxy.task;

import org.mcservernetwork.commons.NetworkAPI;
import org.mcservernetwork.commons.net.Channel;
import org.mcservernetwork.commons.net.packet.PacketWeatherSync;

import java.util.concurrent.ThreadLocalRandom;

public class WeatherSyncRunner implements Runnable {

    private static PacketWeatherSync.Weather currentWeather = PacketWeatherSync.Weather.CLEAR;
    private static long nextWeatherChange = TimeSyncRunner.getDays();

    @Override
    public void run() {
        if (TimeSyncRunner.getDays() > nextWeatherChange) {
            nextWeatherChange += ThreadLocalRandom.current().nextInt(7);

            int randomWeatherIndex = ThreadLocalRandom.current()
                    .nextInt(0, PacketWeatherSync.Weather.values().length);
            currentWeather = PacketWeatherSync.Weather.values()[randomWeatherIndex];
        }
        PacketWeatherSync packet = new PacketWeatherSync();
        packet.weather = currentWeather;
        NetworkAPI.Net.publish(Channel.WEATHER_SYNC, packet);
    }
}
