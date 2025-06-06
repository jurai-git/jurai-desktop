package com.jurai.ui.panes;

import com.jurai.data.ApplicationData;
import com.jurai.ui.animation.interpolator.PowerEase;
import com.jurai.ui.menus.SidebarNav;
import com.jurai.ui.controller.SidebarNavController;
import javafx.animation.ScaleTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.util.Duration;
import lombok.Getter;

public class Sidebar extends AbstractPane {
    private VBox view;
    private Separator separator;
    private Region headerSpacer;
    private SidebarNav nav;
    @Getter
    private boolean iconsOnly;
    private DoubleProperty finalWidth = new SimpleDoubleProperty();
    private DoubleProperty initialWidth = new SimpleDoubleProperty();

    private ScaleTransition separatorTransition;

    public Sidebar() {
        super();
        attachControllers();
    }

    @Override
    protected void initControls() {
        view = new VBox();
        view.setAlignment(Pos.TOP_CENTER);
        view.getStyleClass().add("sidebar");

        separator = new Separator();
        separator.setOrientation(Orientation.HORIZONTAL);
        VBox.setVgrow(separator, Priority.NEVER);
        separator.getStyleClass().add("separator");

        headerSpacer = new Region();
        headerSpacer.minHeightProperty().bind(ApplicationData.headerHeightProperty());

        nav = new SidebarNav();
        VBox.setVgrow(nav.getContent(), Priority.ALWAYS);

        separatorTransition = new ScaleTransition(Duration.millis(300), separator);
        separatorTransition.setInterpolator(new PowerEase(2, true));
    }

    private void attachControllers() {
        SidebarNavController sidebarNavController = new SidebarNavController();
        sidebarNavController.initialize(nav);
    }

    @Override
    protected void layControls() {
        view.getChildren().addAll(
            headerSpacer,
            nav.getContent()
        );
    }

    private void recalculateWidthProperty() {
        finalWidth.unbind();
        initialWidth.unbind();
        if (iconsOnly) {
            initialWidth.bind(ApplicationData.defaultIconSizeProperty().multiply(10));
            finalWidth.bind(ApplicationData.defaultIconSizeProperty().multiply(3));
            separatorTransition.setToX(0);
            separatorTransition.setToY(0);
            separatorTransition.playFromStart();
        } else {
            initialWidth.bind(ApplicationData.defaultIconSizeProperty().multiply(3));
            finalWidth.bind(ApplicationData.defaultIconSizeProperty().multiply(10));
            separatorTransition.setToX(1);
            separatorTransition.setToY(1);
            separatorTransition.playFromStart();
        }
    }

    public DoubleProperty finalWidthProperty() {
        return finalWidth;
    }

    public DoubleProperty initialWidthProperty() {
        return initialWidth;
    }

    public void setIconsOnly(boolean iconsOnly) {
        this.iconsOnly = iconsOnly;
        recalculateWidthProperty();
        nav.setIconsOnly(iconsOnly);
    }

    @Override
    public VBox getView() {
        return view;
    }
}
