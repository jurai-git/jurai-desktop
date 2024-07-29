package io.jurai.ui.controls;

import io.jurai.data.model.Model;
import io.jurai.ui.controller.Controllable;
import io.jurai.ui.util.SpacerFactory;
import io.jurai.util.FileUtils;
import io.jurai.util.UILogger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.TextAlignment;

public class SimpleList<T extends Model> extends BorderPane implements Controllable {
    private String headerText;
    
    private Label headerLabel;
    private StackPane searchIconContainer;
    private TextField searchTextField;
    private SVGPath searchIcon;
    private HBox header;
    private HBox searchArea;
    private ScrollPane scrollPane;
    private VBox listItemsContainer;
    private final ObservableList<T> listItems = FXCollections.observableArrayList();

    //constructors
    public SimpleList() {
        this("Header");
    }
    
    public SimpleList(String headerText) {
        super();
        getStyleClass().add("simple-list");
        this.headerText = headerText;
        initData();
        initControls();
        layControls();
        initAnimations();
    }

    // UI managers
    private void initControls() {

        searchIcon = new SVGPath();
        String searchIconString;
        try {
            searchIconString = FileUtils.getFileContent("/paths/search.path");
        } catch(Exception e) {
            UILogger.logError("Unable to load search icon svg from path");
            UILogger.logWarning("Proceeding without search icon");
            searchIconString = "";
        }
        searchIcon.setFill(Color.web("#BBBBBB"));
        searchIcon.setContent(searchIconString);
        searchIconContainer = new StackPane(searchIcon);
        searchIconContainer.getStyleClass().add("search-icon-container");

        searchArea = new HBox();
        searchArea.getStyleClass().add("search-area");


        searchTextField = new TextField();
        searchTextField.setPromptText("Buscar");
        searchTextField.getStyleClass().add("search-tf");
        searchTextField.prefHeightProperty().bind(searchIconContainer.heightProperty());

        header = new HBox();
        header.getStyleClass().add("list-header");

        headerLabel = new Label(headerText);
        headerLabel.getStyleClass().add("header-label");
        headerLabel.prefHeightProperty().bind(searchArea.heightProperty());
        headerLabel.minWidthProperty().bind(header.widthProperty().multiply(0.15));

        listItemsContainer = new VBox();
        listItemsContainer.getStyleClass().add("list-items");
    }

    private void layControls() {
        HBox.setHgrow(searchIconContainer, Priority.NEVER);
        HBox.setHgrow(searchTextField, Priority.ALWAYS);
        searchArea.getChildren().addAll(searchTextField, searchIconContainer);
        searchArea.maxWidthProperty().bind(header.widthProperty().multiply(0.3));

        HBox.setHgrow(headerLabel, Priority.NEVER);
        HBox.setHgrow(searchArea,Priority.ALWAYS);
        header.getChildren().addAll(headerLabel, SpacerFactory.createHBoxSpacer(Priority.ALWAYS), searchArea);

        setTop(header);

        scrollPane = new ScrollPane(listItemsContainer);
        scrollPane.setBackground(Background.EMPTY);
        scrollPane.getStyleClass().add("scroll-pane");
        setCenter(scrollPane);
    }

    private void initAnimations() {

    }


    // misc

    private void initData() {

    }

    //getters & setters


    public void setHeaderText(String headerText) {
        this.headerText = headerText;
        headerLabel.setText(headerText);
    }

    public String getHeaderText() {
        return headerText;
    }

    public Label getHeaderLabel() {
        return headerLabel;
    }

    public ObservableList<T> getListItems() {
        return listItems;
    }

    public VBox getListItemsContainer() {
        return listItemsContainer;
    }
}