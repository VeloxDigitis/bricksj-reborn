package me.veloxdigitis.bricksj;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public abstract class Speaker<T> {

    private final List<T> listeners;

    protected Speaker(List<T> listeners) {
        this.listeners = listeners;
    }

    protected Speaker() {
        this(Collections.emptyList());
    }

    protected void invokeListeners(Consumer<T> consumer) {
        listeners.forEach(consumer);
    }

}
