package io.jurai.ui.panes;

import io.jurai.ui.menus.RequerentesMenu;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class DashboardPane extends AbstractPane {
    private BorderPane view;
    private RequerentesMenu requerentesMenu;

    public DashboardPane() {
        super();
    }

    @Override
    protected void initControls() {
        view = new BorderPane();
        requerentesMenu = new RequerentesMenu();
    }

    @Override
    protected void layControls() {
        view.setCenter(requerentesMenu.getContent());
    }

    @Override
    public BorderPane getView() {
        return view;
    }

    public TableView getRequerentesTable() {
        return requerentesMenu.getRequerentesTable();
    }

    public TextField getRequerentesSearch() {
        return requerentesMenu.getSearchTf();
    }

    public ComboBox getRequerentesSearchType() {
        return requerentesMenu.getSearchBy();
    }

    public Button getRequerentesCadastrarBtn() {
        return requerentesMenu.getCadastrarBtn();
    }
}
