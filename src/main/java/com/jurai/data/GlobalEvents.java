package com.jurai.data;

import lombok.Getter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class GlobalEvents {

    @Getter
    private static volatile GlobalEvents instance;

    private final List<Runnable> globalDemandasEditedListeners = new CopyOnWriteArrayList<>();
    private final List<Runnable> globalDemandasChangedListeners = new CopyOnWriteArrayList<>();
    private final List<Runnable> pfpChangedListeners = new CopyOnWriteArrayList<>();

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

    public void onGlobalDemandasChanged(Runnable listener) {
        globalDemandasChangedListeners.add(listener);
    }

    public void fireGlobalDemandasChanged() {
        globalDemandasChangedListeners.forEach(Runnable::run);
    }

    public void onGlobalDemandasEdited(Runnable listener) {
        globalDemandasEditedListeners.add(listener);
    }

    public void fireGlobalDemandasEdited() {
        globalDemandasEditedListeners.forEach(Runnable::run);
    }

    public void onPfpChanged(Runnable listener) {
        pfpChangedListeners.add(listener);
    }

    public void firePfpChanged() {
        pfpChangedListeners.forEach(Runnable::run);
    }
}
