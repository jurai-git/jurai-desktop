package io.jurai.ui.util;

import io.jurai.util.EventLogger;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.skin.ScrollPaneSkin;
import javafx.scene.layout.StackPane;

import java.lang.reflect.Field;

public class DefaultScrollPaneSkin extends ScrollPaneSkin {
    public DefaultScrollPaneSkin(final ScrollPane scrollpane) {
        super(scrollpane);
        try {
            Field viewRectField = ScrollPaneSkin.class.getDeclaredField("viewRect");
            viewRectField.setAccessible(true);
            StackPane viewRect = (StackPane) viewRectField.get(this);
            viewRect.setCache(false);
        } catch (Exception e) {
            EventLogger.logError("failed to disable scroll pane cache, exception: " + e.getMessage());
        }
    }
}