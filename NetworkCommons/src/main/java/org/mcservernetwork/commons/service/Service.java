package org.mcservernetwork.commons.service;

import java.util.concurrent.*;

public class Service {

    private static final ScheduledExecutorService service = Executors.newScheduledThreadPool(8);

    public static ScheduledFuture<?> scheduleAtFixedRate(Runnable runnable, int delay, int period, TimeUnit unit) {
        return service.scheduleAtFixedRate(runnable, delay, period, unit);
    }

    public static <V> ScheduledFuture<V> schedule(Callable<V> callable, int delay, TimeUnit unit) {
        return service.schedule(callable, delay, unit);
    }

    public static ScheduledFuture<?> schedule(Runnable runnable, int delay, TimeUnit unit) {
        return service.schedule(runnable, delay, unit);
    }

    public static void shutdown() {
        service.shutdown();
    }

}
