package com.jurai.ui.manager;

import com.jurai.data.AppState;
import com.jurai.util.EventLogger;
import javafx.scene.Scene;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ThemeManager {
    private final List<Scene> scenes = new ArrayList<>();
    private final String darkTheme = getClass().getResource("/style/var.css").toExternalForm();
    private final String lightTheme = getClass().getResource("/style/var-light.css").toExternalForm();

    public ThemeManager(Scene... scenes) {
        Collections.addAll(this.scenes, scenes);

        AppState.get().listen(change -> {
            if ("useLightTheme".equals(change.getPropertyName())) {
                boolean light = (boolean) change.getNewValue();
                updateTheme(light);
            }
        });
    }

    private void updateTheme(boolean light) {
        EventLogger.log("Updating theme to " + (light ? "light" : "dark") + " on ThemeManager");
        String toAdd = light ? lightTheme : darkTheme;
        String toRemove = light ? darkTheme : lightTheme;

        for (Scene scene : scenes) {
            scene.getStylesheets().remove(toRemove);
            if (!scene.getStylesheets().contains(toAdd)) {
                scene.getStylesheets().add(toAdd);
            }
        }
    }

    public void addScene(Scene scene) {
        scenes.add(scene);
        boolean isLight = AppState.get().isUseLightTheme();
        updateTheme(isLight);
    }
}
