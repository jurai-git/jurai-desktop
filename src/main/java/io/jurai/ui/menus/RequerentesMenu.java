package io.jurai.ui.menus;

import io.jurai.data.model.Advogado;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.controlsfx.control.tableview2.TableView2;

public class ClientsMenu extends AbstractMenu{
    private TitledPane content;
    private BorderPane contentBody;
    private ScrollPane tablePane;
    private TableView2<Advogado> clientesView;

    @Override
    protected void initControls() {

    }

    @Override
    protected void layControls() {

    }

    @Override
    public TitledPane getContent() {
        return content;
    }
}
