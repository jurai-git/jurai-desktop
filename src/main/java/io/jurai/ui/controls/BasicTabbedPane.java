package io.jurai.ui.controls;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

public class BasicTabbedPane extends BorderPane {
    private final List<BasicTab> tabs = new ArrayList<>();
    private HBox tabHeaders;
    private BasicTab activeTab;

    public BasicTabbedPane() {
        super();
        getStyleClass().add("basic-tabbed-pane");
        tabHeaders = new HBox();
        tabHeaders.getStyleClass().add("tabs");
        tabHeaders.setSpacing(32);
        tabHeaders.setAlignment(Pos.CENTER);
        setTop(tabHeaders);
        tabHeaders.setCursor(Cursor.HAND);
    }

    public void addTab(BasicTab tab) {
        tabs.add(tab);
        tab.getTabHeader().setAlignment(Pos.CENTER);
        tab.setOnAction(event -> tabClicked(tab));
        tab.setActive(false);
        tabHeaders.getChildren().add(tab.getTabHeader());
    }

    public void setActiveTab(BasicTab tab) {
        if(activeTab != null)
            activeTab.setActive(false);
        activeTab = tab;
        activeTab.setActive(true);
        setCenter(activeTab.getContent());
    }

    private void tabClicked(BasicTab tab) {
        if(activeTab != tab) {
            setActiveTab(tab);
        }
    }
}
