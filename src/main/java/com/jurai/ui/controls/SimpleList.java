package com.jurai.ui.controls;

import com.jurai.data.model.Model;
import com.jurai.ui.util.SpacerFactory;
import com.jurai.util.FileUtils;
import com.jurai.util.UILogger;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import lombok.Getter;

public class SimpleList<T extends Model> extends VBox {
    private String headerText;

    private LoadingCircle loadingCircle;
    private Label headerLabel;
    private StackPane searchIconContainer, listItemsWrapper;
    @Getter
    private TextField searchTextField;
    private SVGPath searchIcon;
    private HBox header;
    private HBox searchArea;
    private ScrollPane scrollPane;
    private VBox listItemsContainer;
    @Getter
    private final ObservableList<SimpleListItem<T>> listItems = FXCollections.observableArrayList();
    @Getter
    private final ObservableList<T> listObjects = FXCollections.observableArrayList();
    private ObjectProperty<SimpleListItem<T>> selectedItem = new SimpleObjectProperty<>();

    //constructors
    public SimpleList() {
        this("Header");
    }

    public SimpleList(String headerText) {
        super();
        getStyleClass().add("simple-list");
        this.headerText = headerText;
        initControls();
        layControls();
        initData();
    }

    // UI managers
    private void initControls() {
        loadingCircle = new LoadingCircle();

        searchIcon = new SVGPath();
        String searchIconString;
        try {
            searchIconString = FileUtils.getResourceContent("/paths/search.path");
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
        enableAutoSearch(); // autoSearch is enabled by default
    }

    private void layControls() {
        HBox.setHgrow(searchIconContainer, Priority.NEVER);
        HBox.setHgrow(searchTextField, Priority.ALWAYS);
        searchArea.getChildren().addAll(searchTextField, searchIconContainer);
        searchArea.prefWidthProperty().bind(header.widthProperty().multiply(0.3));

        HBox.setHgrow(headerLabel, Priority.NEVER);
        HBox.setHgrow(searchArea, Priority.ALWAYS);
        header.getChildren().addAll(headerLabel, SpacerFactory.hSpacer(Priority.ALWAYS), searchArea);

        getChildren().add(header);
        listItemsContainer.setFillWidth(true);
        listItemsContainer.getStyleClass().add("items-container");

        scrollPane = new ScrollPane(listItemsContainer);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setBackground(Background.EMPTY);
        scrollPane.getStyleClass().add("scroll-pane");
        scrollPane.setFitToWidth(true);
        listItemsWrapper = new StackPane(scrollPane);
        VBox.setVgrow(listItemsWrapper, Priority.ALWAYS);
        getChildren().add(listItemsWrapper);
    }

    public void setLoading(boolean loading) {
        if(loading) {
            listItemsWrapper.getChildren().setAll(loadingCircle);
            loadingCircle.play();
        } else {
            listItemsWrapper.getChildren().setAll(scrollPane);
            loadingCircle.stop();
        }
    }

    public void setError(Label errorMsg) {
        loadingCircle.stop();
        listItemsWrapper.getChildren().setAll(errorMsg);
    }

    private void initData() {
        listObjects.addListener((ListChangeListener<T>) change -> {
            System.out.println("listObjects changed");
            listItemsContainer.getChildren().clear();
            listObjects.forEach(this::createListItem);
            listItemsContainer.setClip(null);
        });
    }

    public void createListItem(T object) {
        var item =  new SimpleListItem<>(object);
        item.setOnMouseClicked(event -> {
            setSelectedItem(item);
        });
        item.setFillHeight(true);
        HBox.setHgrow(item, Priority.ALWAYS);
        listItemsContainer.getChildren().add(item);
        listItemsContainer.setClip(null);
    }

    //getters & setters

    public void addSelectedItemListener(ChangeListener<SimpleListItem<T>> listener) {
        selectedItem.addListener(listener);
    }

    public void setSelectedItem(SimpleListItem<T> item) {
        if(selectedItem.get() == null) {
            selectedItem.set(item);
            item.setSelected(true);
            return;
        }

        if(selectedItem.get().equals(item)) {
            item.setSelected(false);
            selectedItem.set(null);
        } else {
            selectedItem.get().setSelected(false);
            selectedItem.set(item);
            item.setSelected(true);
        }
    }

    public void enableAutoSearch() {
        searchTextField.setOnKeyTyped(event -> {
            listItemsContainer.getChildren().forEach(listItem -> {
                if (listItem instanceof SimpleListItem<?> casted) {
                    boolean matches = casted.getName().toLowerCase().contains(searchTextField.getText().toLowerCase());
                    listItem.setVisible(matches);
                    listItem.setManaged(matches);
                }
            });
        });
    }

    public void disableAutoSearch() {
        searchTextField.setOnKeyTyped(null);
    }

    public SimpleListItem<T> getSelectedItem() {
        return selectedItem.get();
    }

    public void setHeaderText(String headerText) {
        this.headerText = headerText;
        headerLabel.setText(headerText);
    }
}