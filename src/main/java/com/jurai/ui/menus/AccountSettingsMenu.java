package com.jurai.ui.menus;
import com.jurai.ui.animation.HoverAnimator;
import com.jurai.ui.controls.HGroup;
import com.jurai.ui.controls.PasswordFieldSet;
import com.jurai.ui.controls.TextFieldSet;
import com.jurai.ui.controls.VGroup;
import com.jurai.ui.util.SpacerFactory;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import static com.jurai.ui.util.ControlWrappers.*;

public class AccountSettingsMenu extends AbstractMenu<VBox> {
    private VBox content;
    private ImageView userImageView;
    private Label usernameLabel, oabLabel;
    private TextFieldSet username, email, oab;
    private PasswordFieldSet changePassword, confirmPassword;
    private Button saveChanges, resetChanges, deleteAccount, changePasswordBtn;

    @Override
    protected void initControls() {
        content = new VBox();
        final Image img = new Image(getClass().getResource("/img/user-default.jpg").toExternalForm());
        userImageView = new ImageView(img);
        userImageView.setSmooth(true);
        userImageView.setPreserveRatio(true);
        userImageView.getStyleClass().addAll("bg-radius-full", "border-radius-full");
        userImageView.setFitWidth(128);
        userImageView.setFitHeight(128);
        userImageView.setClip(new Circle(64, 64, 64));

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

        confirmPassword = new PasswordFieldSet("Confirme a senha");
        confirmPassword.getInput().getStyleClass().add("text-field-base");
        confirmPassword.getInput().setPromptText("Confirme a senha");

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

        HoverAnimator.animateAll(1.2, 1.2, deleteAccount, changePasswordBtn, resetChanges, saveChanges);
    }

    @Override
    protected void layControls() {
        content.getChildren().addAll(
                wrapVgrow(new VGroup().withStyleClass("spacing-3", "small-content-box", "p-6").withChildren(
                        new HGroup().withStyleClass("spacing-3", "pl-4").withChildren(
                                userImageView,
                                new VGroup().withChildren(
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
                        wrapStyleClasses(new Label("Troque sua senha"), "subheader"),
                        changePassword,
                        confirmPassword,
                        SpacerFactory.vSpacer(Priority.ALWAYS),
                        new HGroup().withChildren(
                                SpacerFactory.hSpacer(Priority.ALWAYS),
                                changePasswordBtn
                        )
                )),
                new HGroup().withChildren(
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

    public Button getDeleteAccount() {
        return deleteAccount;
    }

    public TextFieldSet getUsername() {
        return username;
    }

    public TextFieldSet getEmail() {
        return email;
    }

    public TextFieldSet getOab() {
        return oab;
    }

    public PasswordFieldSet getChangePassword() {
        return changePassword;
    }

    public PasswordFieldSet getConfirmPassword() {
        return confirmPassword;
    }

    public Button getResetChanges() {
        return resetChanges;
    }

    public Button getSaveChanges() {
        return saveChanges;
    }

    public Button getChangePasswordBtn() {
        return changePasswordBtn;
    }

    @Override
    public VBox getContent() {
        return content;
    }
}
