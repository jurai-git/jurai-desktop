package com.jurai.ui.menus;

import com.jurai.ui.animation.HoverAnimator;
import com.jurai.ui.controls.HGroup;
import com.jurai.ui.controls.ScrollableGroup;
import com.jurai.ui.controls.SettingsOption;
import com.jurai.ui.controls.VGroup;
import com.jurai.ui.util.SpacerFactory;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import lombok.Getter;
import org.controlsfx.control.ToggleSwitch;

import static com.jurai.ui.util.ControlWrappers.*;

public class AppSettingsMenu extends AbstractMenu<VBox>  {
    private VBox content;

    @Getter
    private ToggleSwitch useLightTheme, useAnimations;
    private SettingsOption<ToggleSwitch> useLightThemeOption, useAnimationsOption;

    @Getter
    private TextField apiUrl;
    private SettingsOption<TextField> apiUrlOption;

    @Getter
    private Button saveChanges, importConfigs, exportConfigs;

    @Getter
    private Label successLabel;

    @Override
    protected void initControls() {
        content = new VBox();
        useLightTheme = new ToggleSwitch("Tema claro");
        useLightThemeOption = new SettingsOption<>("Usar tema claro", useLightTheme);
        useAnimations = new ToggleSwitch("Animações");
        useAnimationsOption = new SettingsOption<>("Usar animações", useAnimations);

        apiUrl = new TextField();
        apiUrl.setPromptText("Change the API URL");
        apiUrl.getStyleClass().add("text-field-base-i");
        apiUrlOption = new SettingsOption<>("Mudar o URL da API", apiUrl);

        saveChanges = new Button("Salvar");
        saveChanges.getStyleClass().add("blue-button");
        saveChanges.setDisable(true);

        importConfigs = new Button("Importar");
        exportConfigs = new Button("Exportar");

        successLabel = new Label("");
        successLabel.setTextAlignment(TextAlignment.RIGHT);
        successLabel.setMaxWidth(Double.MAX_VALUE);
        successLabel.setPrefWidth(Double.MAX_VALUE);

        HoverAnimator.animateAll(1.2, 1.2, saveChanges, importConfigs, exportConfigs);
    }

    @Override
    protected void layControls() {
        content.getChildren().addAll(
                new ScrollableGroup().withVgrow(Priority.ALWAYS).withStyleClass("spacing-3").vbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED).hbarPolicy(ScrollPane.ScrollBarPolicy.NEVER).withFixedWitdh().withContent(
                        new VGroup().withVgrow(Priority.ALWAYS).withChildren(

                                wrapStyle(wrapStyleClasses(new Label("Configurações gerais"), "subheader"), "-fx-padding: 0 0 8px 6px;"),
                                new VGroup().withVgrow(Priority.SOMETIMES).withStyleClass("small-content-box", "spacing-3", "p-6").withChildren(
                                        wrapHgrow(useLightThemeOption, Priority.ALWAYS),
                                        wrapHgrow(useAnimationsOption, Priority.ALWAYS),
                                        wrapHgrow(apiUrlOption, Priority.ALWAYS)
                                ),
                                SpacerFactory.vSpacer(24),

                                wrapStyle(wrapStyleClasses(new Label("Privacidade e Segurança"), "subheader"), "-fx-padding: 0 0 8px 6px;"),
                                new VGroup().withStyleClass("small-content-box", "spacing-3", "p-6").withChildren(
                                        new HGroup().withHgrow(Priority.ALWAYS).withAlignment(Pos.CENTER).withChildren(
                                                new Label("Termos e condições"),
                                                SpacerFactory.hSpacer(Priority.ALWAYS),
                                                new Hyperlink("Clique aqui para ver")
                                        )
                                ),
                                SpacerFactory.vSpacer(24),

                                wrapStyle(wrapStyleClasses(new Label("Contato e suporte"), "subheader"), "-fx-padding: 0 0 8px 6px;"),
                                new VGroup().withStyleClass("small-content-box", "spacing-3", "p-6").withChildren(
                                        new HGroup().withHgrow(Priority.ALWAYS).withAlignment(Pos.CENTER).withChildren(
                                                new Label("Nosso e-mail"),
                                                SpacerFactory.hSpacer(Priority.ALWAYS),
                                                new Label("contas.jurai@gmail.com")
                                        ),
                                        new HGroup().withHgrow(Priority.ALWAYS).withAlignment(Pos.CENTER).withChildren(
                                                new Label("Formulário para contato"),
                                                SpacerFactory.hSpacer(Priority.ALWAYS),
                                                new Hyperlink("Clique aqui para acessar")
                                        )
                                )
                        )
                ),
                new HGroup().withChildren(
                        exportConfigs,
                        SpacerFactory.hSpacer(12),
                        importConfigs,
                        SpacerFactory.hSpacer(Priority.ALWAYS),
                        saveChanges
                ) // bottom buttons
        );
        content.setPrefWidth(600);
        content.getStyleClass().add("spacing-4");
        content.setMaxWidth(Double.MAX_VALUE);
    }

    @Override
    public VBox getContent() {
        return content;
    }
}
