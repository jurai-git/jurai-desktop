package com.jurai.ui.menus;
import com.jurai.data.AppState;
import com.jurai.ui.animation.HoverAnimator;
import com.jurai.ui.controls.*;
import com.jurai.ui.util.SpacerFactory;
import com.jurai.util.EventLogger;
import dev.mgcvale.fluidfx.components.controls.FLabel;
import dev.mgcvale.fluidfx.components.groups.HGroup;
import dev.mgcvale.fluidfx.components.groups.VGroup;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;
import lombok.Getter;

import static dev.mgcvale.fluidfx.components.layout.Wrappers.wStyleClasses;
import static dev.mgcvale.fluidfx.components.layout.Wrappers.wVgrow;

public class AccountSettingsMenu extends AbstractMenu<VBox> {
    private VBox content;

    @Getter
    private ProfilePicture profilePicture;
    private Label usernameLabel, oabLabel;

    @Getter
    private StringProperty pwdChangeErrorProperty;

    @Getter
    private TextFieldSet username, email, oab;
    @Getter
    private PasswordFieldSet changePassword, confirmPassword;
    @Getter
    private Button saveChanges, resetChanges, deleteAccount, changePasswordBtn;

    @Override
    protected void initControls() {
        content = new VBox();
        profilePicture = new ProfilePicture();
        loadFallback();
        profilePicture.setSmooth(true);
        profilePicture.setPreserveRatio(true);
        profilePicture.getImageView().getStyleClass().addAll("bg-radius-full", "border-radius-full");
        profilePicture.setFitWidth(128);
        profilePicture.setFitHeight(128);
        profilePicture.setClip(new Circle(64, 64, 64));

        username = new TextFieldSet("Nome de usuário");
        username.getInput().getStyleClass().add("text-field-base");
        username.getInput().setPromptText("username");
        usernameLabel = new Label();
        usernameLabel.getStyleClass().addAll("subheader", "");
        usernameLabel.textProperty().bind(username.getInput().textProperty());

        email = new TextFieldSet("E-mail");
        email.getInput().getStyleClass().add("text-field-base");
        email.getInput().setPromptText("email");

        changePassword = new PasswordFieldSet("Troque sua senha");
        changePassword.getInput().getStyleClass().add("text-field-base");
        changePassword.getInput().setPromptText("Nova senha");
        changePassword.getInput().textProperty().addListener((obs, o, n) -> getPwdChangeErrorProperty().set(""));

        confirmPassword = new PasswordFieldSet("Confirme a senha");
        confirmPassword.getInput().getStyleClass().add("text-field-base");
        confirmPassword.getInput().setPromptText("Confirme a senha");
        confirmPassword.getInput().textProperty().addListener((obs, o, n) -> getPwdChangeErrorProperty().set(""));

        oab = new TextFieldSet("OAB");
        oab.getInput().getStyleClass().add("text-field-base");
        oab.getInput().setEditable(false);
        oab.getInput().setText("243101");
        oabLabel = new Label();
        oabLabel.getStyleClass().addAll("text-secondary");
        oabLabel.textProperty().bind(Bindings.createStringBinding(
                () -> "OAB: " + oab.getInput().getText(),
                oab.getInput().textProperty()
        ));

        saveChanges = new Button("Salvar");
        saveChanges.getStyleClass().addAll("blue-button");
        saveChanges.setDisable(true);

        resetChanges = new Button("Limpar dados");
        resetChanges.setDisable(true);

        deleteAccount = new Button("Deletar conta");
        deleteAccount.getStyleClass().addAll("red-button");

        changePasswordBtn = new Button("Mudar senha");
        changePasswordBtn.setDisable(true);

        pwdChangeErrorProperty = new SimpleStringProperty("");

        HoverAnimator.animateAll(1.2, 1.2, deleteAccount, changePasswordBtn, resetChanges, saveChanges);
    }

    @Override
    protected void layControls() {
        content.getChildren().addAll(
                wVgrow(new VGroup().wStyleClass("spacing-3", "small-content-box", "p-6").wChildren(
                        new HGroup().wStyleClass("spacing-3", "pl-4").wChildren(
                                profilePicture,
                                new VGroup().wChildren(
                                        SpacerFactory.vSpacer(Priority.ALWAYS),
                                        usernameLabel,
                                        oabLabel,
                                        SpacerFactory.vSpacer(Priority.ALWAYS)
                                )
                        ),
                        SpacerFactory.vSpacer(content.heightProperty().multiply(0.04)),
                        username,
                        email,
                        oab,
                        SpacerFactory.vSpacer(content.heightProperty().multiply(0.03).add(4)),
                        wStyleClasses(new Label("Troque sua senha"), "subheader"),
                        changePassword,
                        confirmPassword,
                        new FLabel().inText(pwdChangeErrorProperty).inVisible(pwdChangeErrorProperty.isNotEmpty()).wStyleClass("text-red").wTextAlignment(TextAlignment.RIGHT),
                        SpacerFactory.vSpacer(Priority.ALWAYS),
                        new HGroup().wChildren(
                                SpacerFactory.hSpacer(Priority.ALWAYS),
                                changePasswordBtn
                        )
                )),
                new HGroup().wChildren(
                        resetChanges,
                        SpacerFactory.hSpacer(Priority.ALWAYS),
                        deleteAccount,
                        SpacerFactory.hSpacer(12),
                        saveChanges
                )
        );
        content.setPrefWidth(500);
        content.setMaxWidth(Double.MAX_VALUE);
        content.getStyleClass().add("spacing-4");
    }

    public void updatePfp(String url) {
        if (url == null || url.isEmpty()) {
            loadFallback();
            return;
        }
        EventLogger.log("Loading profile picture w URL " + url + " on AccountSettingsMenu");

        ChangeListener<Boolean> errorHandler = (obs, oldVal, hasError) -> {
          if (hasError) {
              EventLogger.logWarning("No image found for current user, loading default user image on AccountSettingsMenu");
              loadFallback();
          }
        };
        Image img = new Image(url, true);
        img.errorProperty().addListener(errorHandler);
        profilePicture.setImage(img);
        profilePicture.setHasCustomImage(true);
    }

    public void loadFallback() {
        Image img = new Image(getClass().getResource(AppState.get().getFallbackPfpPath()).toExternalForm());
        profilePicture.setImage(img);
        profilePicture.setHasCustomImage(false);
    }

    @Override
    public VBox getContent() {
        return content;
    }
}
