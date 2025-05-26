package com.jurai.ui.manager;

import com.jurai.data.ApplicationState;
import com.jurai.ui.PrimaryScene;
import com.jurai.ui.SecondaryScene;
import com.jurai.util.EventLogger;
import com.jurai.util.StateLogger;
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

        ApplicationState.getInstance().addPropertyChangeListener(change -> {
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
        boolean isLight = ApplicationState.getInstance().isUseLightTheme();
        updateTheme(isLight);
    }
}
