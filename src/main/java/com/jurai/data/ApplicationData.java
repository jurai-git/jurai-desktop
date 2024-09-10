package com.jurai.data;

import com.jurai.util.StateLogger;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public final class ApplicationData {
    private static final PropertyChangeSupport support = new PropertyChangeSupport(new ApplicationData());

    private static final DoubleProperty defaultIconSize = new SimpleDoubleProperty(0);
    private static final DoubleProperty headerHeight = new SimpleDoubleProperty(0);
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public static void initialize() {
        initializeSupportLogging(support);
        StateLogger.log("initialized ApplicationData logging");

        defaultIconSize.addListener((observableValue, number, t1) -> {
            String oldValueString = (number == null) ? "null" : number.toString();
            String newValueString = (t1 == null) ? "null" : t1.toString();
            StateLogger.log(("defaultIconSIze changed from " + oldValueString + " to " + newValueString));
        });
    }

    static void initializeSupportLogging(PropertyChangeSupport support) {
        support.addPropertyChangeListener(propertyChangeEvent -> {
            String oldValueString = (propertyChangeEvent.getOldValue() == null) ? "null" : propertyChangeEvent.getOldValue().toString();
            String newValueString = (propertyChangeEvent.getNewValue() == null) ? "null" : propertyChangeEvent.getNewValue().toString();
            StateLogger.log((propertyChangeEvent.getPropertyName() + " changed from " + oldValueString + " to " + newValueString));
        });
    }

    public static double getDefaultIconSize() {
        return defaultIconSize.get();
    }

    public static DoubleProperty defaultIconSizeProperty() {
        return defaultIconSize;
    }

    public static void setDefaultIconSize(double defaultIconSize) {
        support.firePropertyChange("defaultIconSize", ApplicationData.defaultIconSize, defaultIconSize);
        ApplicationData.defaultIconSize.set(defaultIconSize);
    }

    public static double getHeaderHeight() {
        return headerHeight.get();
    }

    public static void setHeaderHeight(double headerHeight) {
        support.firePropertyChange("headerHeight", ApplicationData.headerHeight, headerHeight);
        ApplicationData.headerHeight.set(headerHeight);
    }

    public static DoubleProperty headerHeightProperty() {
        return headerHeight;
    }

    public static Dimension getScreenSize() {
        return screenSize;
    }

    public static void addPropertyChangeListener(PropertyChangeListener l) {
        support.addPropertyChangeListener(l);
    }

}
