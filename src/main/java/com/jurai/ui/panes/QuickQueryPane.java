package com.jurai.ui.panes;

import com.jurai.ui.controls.BasicTab;
import com.jurai.ui.controls.BasicTabbedPane;
import com.jurai.ui.menus.EmentaQuickQueryTab;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class QuickQueryPane extends AbstractPane {
    private BorderPane view, centerContent;
    private BasicTabbedPane tabbedPane;
    private Label title, subtitle;
    private EmentaQuickQueryTab quickQueryTab;
    private BasicTab pdfTab, ementaTab;
    private Mode mode;

    public QuickQueryPane() {
        super();
    }

    @Override
    protected void initControls() {
        view = new BorderPane();
        view.getStyleClass().addAll("pane");

        tabbedPane = new BasicTabbedPane();
        tabbedPane.getStyleClass().addAll("content-box");
        tabbedPane.setPadding(new Insets(0, 24, 24 ,24));
        quickQueryTab = new EmentaQuickQueryTab();

        pdfTab = new BasicTab("PDF", quickQueryTab.getContent());
        ementaTab = new BasicTab("Ementa", quickQueryTab.getContent());

        tabbedPane.addTab(pdfTab);
        tabbedPane.addTab(ementaTab);
        tabbedPane.setActiveTab(pdfTab);

        title = new Label("Consulta Rápida");
        title.getStyleClass().add("header");
        subtitle = new Label("Faça uma rápida análise de uma Ementa ou Documento processual");
        subtitle.getStyleClass().add("subheader");
        centerContent = new BorderPane();
    }

    @Override
    protected void layControls() {
        centerContent.setTop(subtitle);
        BorderPane.setAlignment(tabbedPane, Pos.CENTER);
        tabbedPane.maxWidthProperty().bind(centerContent.widthProperty().multiply(0.9));
        tabbedPane.maxHeightProperty().bind(centerContent.heightProperty().subtract(subtitle.heightProperty()).multiply(0.9));
        centerContent.setCenter(tabbedPane);

        view.setTop(title);
        view.setCenter(centerContent);
    }

    @Override
    public BorderPane getView() {
        return view;
    }

    public BasicTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public enum Mode {
        PDF,
        EMENTA
    };
}
