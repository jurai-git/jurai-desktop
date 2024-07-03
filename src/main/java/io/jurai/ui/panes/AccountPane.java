package io.jurai.ui.panes;

import io.jurai.data.ApplicationState;
import io.jurai.data.notifier.StateNotifier;
import io.jurai.ui.menus.LoginMenu;
import javafx.scene.layout.StackPane;

public class AccountPane implements StateNotifier<Boolean> {
    private StackPane view;
    private LoginMenu loginMenu;

    public AccountPane() {
        view = new StackPane();
        initControls();
        addNotifiers();
    }

    private void initControls() {
        loginMenu = new LoginMenu();
        loginMenu.getView().maxWidthProperty().bind(view.widthProperty().divide(8).multiply(5));
        loginMenu.getView().maxHeightProperty().bind(view.heightProperty().divide(8).multiply(6));
        view.getChildren().add(loginMenu.getView());
    }

    private void addNotifiers() {
        ApplicationState.addLoggedInListener(this);
        stateChanged(ApplicationState.isLoggedIn());
    }

    @Override
    public void stateChanged(Boolean newState) {
        if(!newState) {
            //remove login view and add account dashboard view
            view.getChildren().clear();
            view.getChildren().add(loginMenu.getView());
        } else {
            view.getChildren().clear();
        }


        System.out.println("[STATE LOGGER] updated loggedIn property: " + ApplicationState.isLoggedIn());
    }

    public StackPane getView() {
        return view;
    }
}
