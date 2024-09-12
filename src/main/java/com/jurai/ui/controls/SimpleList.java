package com.jurai.ui.controls;

import com.jurai.data.ApplicationData;
import com.jurai.data.model.Model;
import com.jurai.ui.controller.Controllable;
import com.jurai.ui.util.SpacerFactory;
import com.jurai.util.FileUtils;
import com.jurai.util.UILogger;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

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
    private final ObservableList<SimpleListItem<T>> listItems = FXCollections.observableArrayList();
    private final ObservableList<T> listObjects = FXCollections.observableArrayList();

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
        listItemsContainer.setFillWidth(true);
        listItemsContainer.getStyleClass().add("list-items");
    }

    private void layControls() {
        HBox.setHgrow(searchIconContainer, Priority.NEVER);
        HBox.setHgrow(searchTextField, Priority.ALWAYS);
        searchArea.getChildren().addAll(searchTextField, searchIconContainer);
        searchArea.prefWidthProperty().bind(header.widthProperty().multiply(0.3));

        HBox.setHgrow(headerLabel, Priority.NEVER);
        HBox.setHgrow(searchArea, Priority.ALWAYS);
        header.getChildren().addAll(headerLabel, SpacerFactory.createHBoxSpacer(Priority.ALWAYS), searchArea);

        setTop(header);

        VBox wrapper = new VBox(listItemsContainer);
        VBox.setVgrow(listItemsContainer, Priority.ALWAYS);
        listItemsContainer.setFillWidth(true);

        scrollPane = new ScrollPane(wrapper);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setBackground(Background.EMPTY);
        scrollPane.getStyleClass().add("scroll-pane");
        scrollPane.setFitToWidth(true);
        setCenter(scrollPane);
    }

    private void initAnimations() {

    }

    private void initData() {
        listObjects.addListener((ListChangeListener<T>) change -> {
            System.out.println("listObjects changed");
            listItemsContainer.getChildren().clear();
            listItemsContainer.getChildren().addAll(listObjects.stream().map(this::createListItem).toList());
        });
    }

    public SimpleListItem<T> createListItem(T object) {
        var item =  new SimpleListItem<>(object);
        item.setFillHeight(true);
        HBox.setHgrow(item, Priority.ALWAYS);
        double em = ApplicationData.getEm();
        VBox.setMargin(item, new Insets(em*0.5, 0, em*0.5, 0));
        return item;
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

    public ObservableList<T> getListObjects() {
        return listObjects;
    }

    public VBox getListItemsContainer() {
        return listItemsContainer;
    }
}