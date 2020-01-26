package org.mcservernetwork.commons.future;

import java.util.HashMap;
import java.util.Map;

public class TrackedFutureProvider<I, V> {

    private final Map<I, TrackedFuture<I, V>> trackedFutureContainer = new HashMap<>();

    public boolean complete(I id, V value) {
        TrackedFuture<I, V> future = trackedFutureContainer.get(id);
        if (future == null) {
            return false;
        }

        return future.complete(value);
    }

    public TrackedFuture<I, V> create(I id) {
        TrackedFuture<I, V> future = new TrackedFuture<>(id, this);

        trackedFutureContainer.put(id, future);
        return future;
    }

    protected void remove(I id) {
        trackedFutureContainer.remove(id);
    }

}
