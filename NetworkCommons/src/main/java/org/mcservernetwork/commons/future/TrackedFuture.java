package org.mcservernetwork.commons.future;

import org.mcservernetwork.commons.service.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class TrackedFuture<I, V> {

    private final I id;
    private final TrackedFutureProvider<I, V> manager;
    private final CompletableFuture<V> future;

    private ScheduledFuture<?> timeoutService;

    protected TrackedFuture(I id, TrackedFutureProvider<I, V> manager) {
        this.id = id;
        this.manager = manager;
        this.future = new CompletableFuture<>();
    }

    public TrackedFuture<I, V> completed(Consumer<V> consumer) {
        future.thenAccept(consumer);
        return this;
    }

    public TrackedFuture<I, V> withTimeout(int delay, TimeUnit unit) {
        timeoutService = Service.schedule(() -> {
            future.cancel(true);
            manager.remove(id);
        }, delay, unit);
        return this;
    }

    protected boolean complete(V value) {
        if (timeoutService != null)
            timeoutService.cancel(true);

        manager.remove(id);

        return future.complete(value);
    }

}
