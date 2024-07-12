package io.jurai.ui.menus;

import io.jurai.data.model.Advogado;
import io.jurai.data.model.Requerente;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.controlsfx.control.tableview2.TableView2;

public class RequerentesMenu extends AbstractMenu{
    private TitledPane content;
    private BorderPane contentBody;
    private ScrollPane tablePane;
    private TableView2<Requerente> requerentesTable;
    private TextField searchTf;
    private ComboBox<String> searchBy;
    private Button cadastrarBtn;

    private HBox borderTop;
    private HBox borderBottom;

    @Override
    protected void initControls() {
        contentBody = new BorderPane();
        contentBody.getStyleClass().add("border-pane");
        borderBottom = new HBox();
        borderBottom.getStyleClass().add("hbox");
        borderTop = new HBox();
        borderTop.getStyleClass().add("hbox");


        requerentesTable = new TableView2<>();
        createTableModel();
        tablePane = new ScrollPane(requerentesTable);
        tablePane.setFitToHeight(true);
        tablePane.setFitToWidth(true);
        searchTf = new TextField();
        searchTf.setPromptText("Buscar");
        searchBy = new ComboBox<>();
        searchBy.getItems().addAll("Nome", "Cpf/Cnpj");
        cadastrarBtn = new Button("Cadastrar novo cliente");
        cadastrarBtn.getStyleClass().add("wide-padding");
    }

    @Override
    protected void layControls() {
        borderTop.setAlignment(Pos.CENTER);
        HBox.setHgrow(searchTf, Priority.ALWAYS);
        HBox.setHgrow(searchBy, Priority.NEVER);
        borderTop.getChildren().addAll(searchTf, searchBy);

        borderBottom.setAlignment(Pos.CENTER);
        HBox.setHgrow(cadastrarBtn, Priority.NEVER);
        borderBottom.getChildren().addAll(cadastrarBtn);

        contentBody.setTop(borderTop);
        borderTop.getStyleClass().add("border-pane-child");
        contentBody.setCenter(tablePane);
        tablePane.getStyleClass().add("border-pane-child");
        contentBody.setBottom(borderBottom);
        borderBottom.getStyleClass().add("border-pane-child");

        content = new TitledPane("Consulta de clientes cadastrados", contentBody);
        content.setCollapsible(false);
        content.getStyleClass().addAll("floating", "titled-pane");
    }


    private void createTableModel() {
        requerentesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        TableColumn<Requerente, String> nameColumn = new TableColumn<>("Nome");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Requerente, String> cpfCnpjColumn = new TableColumn<>("Cpf/Cnpj");
        cpfCnpjColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        requerentesTable.getColumns().addAll(nameColumn, cpfCnpjColumn);
    }

    @Override
    public TitledPane getContent() {
        return content;
    }

    public TableView2<Requerente> getRequerentesTable() {
        return requerentesTable;
    }

    public Button getCadastrarBtn() {
        return cadastrarBtn;
    }

    public ComboBox<String> getSearchBy() {
        return searchBy;
    }

    public TextField getSearchTf() {
        return searchTf;
    }
}
