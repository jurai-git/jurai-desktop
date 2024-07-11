package io.jurai.ui.menus;

import io.jurai.data.ApplicationState;
import io.jurai.data.model.Advogado;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;

public class AccountPopupMenu {
    private BorderPane content;
    private VBox labelPane;
    private HBox buttonsPane;
    private Label messageLbl;
    private Hyperlink accountPaneHpl, dashboardHpl;
    private Button exitBtn, loginBtn, registerBtn;

    public AccountPopupMenu() {
        initControls();
        layControls();
        attachNotifiers();
        currentUserChanged(null);
    }

    private void initControls() {
        messageLbl = new Label();
        messageLbl.getStyleClass().add("h3");
        HBox.setHgrow(messageLbl, Priority.ALWAYS);

        accountPaneHpl = new Hyperlink("Sua Conta");
        accountPaneHpl.getStyleClass().add("h4");
        dashboardHpl = new Hyperlink("Dashboard");
        accountPaneHpl.getStyleClass().add("h4");

        exitBtn = new Button("Sair");
        loginBtn = new Button("Login");
        registerBtn = new Button("Cadastre-se");
    }

    private void layControls() {
        labelPane = new VBox();
        labelPane.getStyleClass().add("vbox");
        buttonsPane = new HBox();
        buttonsPane.getStyleClass().add("hbox");
        content = new BorderPane();

        HBox wrapper = new HBox();
        wrapper.getChildren().add(messageLbl);
        content.setTop(wrapper);
        content.setCenter(labelPane);
        content.setBottom(buttonsPane);
        content.getStyleClass().add("border-pane");
    }

    private void attachNotifiers() {
        ApplicationState.addPropertyChangeListener(event -> {
            if("currentUser".equals(event.getPropertyName())) {
                currentUserChanged((Advogado) event.getNewValue());
            }
        });
    }

    private void currentUserChanged(Advogado newUser) {
        if(newUser == null) {
            messageLbl.setText("Faça log-in ou cadastre-se");
            labelPane.getChildren().removeAll(accountPaneHpl, dashboardHpl);
            buttonsPane.getChildren().removeAll(exitBtn);
            buttonsPane.getChildren().addAll(loginBtn, registerBtn);
        } else {
            messageLbl.setText(newUser.getNomeUsuario());
            labelPane.getChildren().addAll(accountPaneHpl, dashboardHpl);
            buttonsPane.getChildren().removeAll(loginBtn, registerBtn);
            buttonsPane.getChildren().addAll(exitBtn);
        }
    }

    public Button getExitBtn() {
        return exitBtn;
    }

    public Button getLoginBtn() {
        return loginBtn;
    }

    public Button getRegisterBtn() {
        return registerBtn;
    }

    public BorderPane getContent() {
        return content;
    }

    public Hyperlink getDashboardHpl() {
        return dashboardHpl;
    }

    public Hyperlink getAccountPaneHpl() {
        return accountPaneHpl;
    }
}
