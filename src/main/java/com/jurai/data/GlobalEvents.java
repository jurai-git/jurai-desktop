package com.jurai.data;

import lombok.Getter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class GlobalEvents {

    @Getter
    private static volatile GlobalEvents instance;

    private final List<Consumer<Void>> pfpChangedListeners = new CopyOnWriteArrayList<>();

    public static void initialize() {
        if (instance == null) {
            synchronized (GlobalEvents.class) {
                if (instance == null) {
                    instance = new GlobalEvents();
                }
            }
        }
    }

    public static GlobalEvents get() {
        if (instance == null) {
            throw new IllegalStateException("GlobalEvents not initialized. Call initialize() first.");
        }
        return instance;
    }

    public void onPfpChanged(Consumer<Void> listener) {
        pfpChangedListeners.add(listener);
    }

    public void firePfpChanged() {
        for (Consumer<Void> listener : pfpChangedListeners) {
            listener.accept(null);
        }
    }
}
