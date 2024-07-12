package io.jurai.ui.panes;

import io.jurai.ui.controls.AccountControl;
import io.jurai.ui.controls.controller.AccountControlController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import java.util.Objects;

public class Navbar extends AbstractPane {
    private HBox view;

    private ImageView icon;
    private Hyperlink inicioBtn, consultaRapidaBtn, planosBtn;
    private AccountControl accountControl;

    public Navbar() {
        initControls();
        layControls();
        attachControllers();
    }

    @Override
    protected void initControls() {
        icon = new ImageView();
        HBox.setHgrow(icon, Priority.ALWAYS);
        icon.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/horizontal_logo.png"))));
        HBox.setHgrow(icon, Priority.ALWAYS);
        icon.setSmooth(true);
        icon.setPreserveRatio(true);
        icon.getStyleClass().add("image");

        inicioBtn = new Hyperlink("Inicio");
        inicioBtn.getStyleClass().add("navbar-button");
        HBox.setHgrow(inicioBtn, Priority.NEVER);

        consultaRapidaBtn = new Hyperlink("Consulta Rapida");
        consultaRapidaBtn.getStyleClass().add("navbar-button");
        HBox.setHgrow(consultaRapidaBtn, Priority.NEVER);

        planosBtn = new Hyperlink("Planos");
        planosBtn.getStyleClass().add("navbar-button");
        HBox.setHgrow(planosBtn, Priority.NEVER);

        accountControl = new AccountControl();
        HBox.setHgrow(accountControl, Priority.NEVER);

        icon.fitHeightProperty().bind(inicioBtn.heightProperty().multiply(1.5));
    }

    @Override
    protected void layControls() {
        view = new HBox();
        Region spacers[] = new Region[6];
        for(int i=0; i < 6; i++) {
            spacers[i] = new Region();
            HBox.setHgrow(spacers[i], Priority.ALWAYS);
        }

        view.getStyleClass().add("navbar");
        view.setAlignment(Pos.CENTER);
        view.getChildren().addAll(spacers[5], icon, spacers[0], inicioBtn, spacers[1], consultaRapidaBtn, spacers[2], planosBtn, spacers[3], accountControl, spacers[4]);

        view.getStylesheets().add(getClass().getResource("/style/navbar.css").toExternalForm());
    }

    private void attachControllers() {
        AccountControlController acc = new AccountControlController();
        acc.initialize(accountControl);
    }

    public AccountControl getAccountControl() {
        return accountControl;
    }

    @Override
    public Pane getView() {
        return view;
    }

    public Hyperlink getConsultaRapidaBtn() {
        return consultaRapidaBtn;
    }

    public Hyperlink getInicioBtn() {
        return inicioBtn;
    }

    public Hyperlink getPlanosBtn() {
        return planosBtn;
    }

    public Hyperlink getDashboardBtn() {
        return accountControl.getDashboardHpl();
    }

    public Hyperlink getAccountBtn() {
        return accountControl.getAccountPaneHpl();
    }

    public Button getLoginBtn() {
        return accountControl.getLoginBtn();
    }

    public Button getRegisterBtn() {
        return accountControl.getRegisterBtn();
    }

    public Button getExitBtn() {
        return accountControl.getExitBtn();
    }
}
