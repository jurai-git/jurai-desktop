package com.jurai.ui.panes;

import com.jurai.ui.controller.DemandaAnalysisController;
import com.jurai.ui.controller.DemandaDashboardController;
import com.jurai.ui.controller.RequerenteDashboardController;
import com.jurai.ui.menus.DemandaAnalysisDashboardMenu;
import com.jurai.ui.menus.DemandaDashboardMenu;
import com.jurai.ui.menus.RequerenteDashboardMenu;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class DashboardPane extends AbstractPane {
    private BorderPane view;
    private GridPane centerContent;
    private ColumnConstraints left, right;
    private RowConstraints top, bottom;
    private RequerenteDashboardMenu requerenteDashboardMenu;
    private DemandaDashboardMenu demandaDashboardMenu;
    private DemandaAnalysisDashboardMenu demandaAnalysisDashboardMenu;

    public DashboardPane() {
        super();
        attachControllers();
    }

    @Override
    protected void initControls() {
        view = new BorderPane();
        view.getStyleClass().addAll("pane", "no-bottom-padding");
        requerenteDashboardMenu = new RequerenteDashboardMenu();
        demandaDashboardMenu = new DemandaDashboardMenu();
        demandaAnalysisDashboardMenu = new DemandaAnalysisDashboardMenu();

        centerContent = new GridPane();
        left = new ColumnConstraints();
        right = new ColumnConstraints();
        bottom = new RowConstraints();
        top = new RowConstraints();
    }

    @Override
    protected void layControls() {
        final Label dl = new Label("Dashboard");
        dl.getStyleClass().add("header");

        left.setPercentWidth(45);
        right.setPercentWidth(55);
        bottom.setPercentHeight(45);
        top.setPercentHeight(55);
        centerContent.setHgap(16);
        centerContent.setVgap(16);
        centerContent.getColumnConstraints().addAll(left, right);
        centerContent.getRowConstraints().addAll(top, bottom);
        centerContent.add(requerenteDashboardMenu.getContent(), 0, 0, 1, 2);
        centerContent.add(demandaDashboardMenu.getContent(), 1, 0, 1, 1);
        centerContent.add(demandaAnalysisDashboardMenu.getContent(), 1, 1, 1, 1);

        view.setTop(dl);
        view.setCenter(centerContent);
    }

    private void attachControllers() {
        RequerenteDashboardController requerenteController = new RequerenteDashboardController();
        requerenteController.initialize(requerenteDashboardMenu);

        DemandaDashboardController demandaDashboardController = new DemandaDashboardController();
        demandaDashboardController.initialize(demandaDashboardMenu);

        DemandaAnalysisController demandaAnalysisController = new DemandaAnalysisController();
        demandaAnalysisController.initialize(demandaAnalysisDashboardMenu);
    }

    @Override
    public BorderPane getView() {
        return view;
    }
}