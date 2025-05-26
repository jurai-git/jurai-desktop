package com.jurai.ui.menus;

import com.jurai.ui.controls.HGroup;
import com.jurai.ui.controls.SettingsOption;
import com.jurai.ui.controls.VGroup;
import com.jurai.ui.util.SpacerFactory;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import lombok.Getter;
import org.controlsfx.control.ToggleSwitch;

import static com.jurai.ui.util.ControlWrappers.wrapHgrow;
import static com.jurai.ui.util.ControlWrappers.wrapStyleClasses;

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
    }

    @Override
    protected void layControls() {
        content.getChildren().addAll(
                new VGroup().withVgrow(Priority.ALWAYS).withStyleClass("form", "spacing-3", "p-6").withChildren(
                        useLightThemeOption,
                        useAnimationsOption,
                        apiUrlOption,
                        SpacerFactory.vSpacer(Priority.ALWAYS),
                        wrapStyleClasses(wrapHgrow(successLabel, Priority.ALWAYS), "text-green")
                ),
                new HGroup().withChildren(
                        exportConfigs,
                        SpacerFactory.hSpacer(12),
                        importConfigs,
                        SpacerFactory.hSpacer(Priority.ALWAYS),
                        saveChanges
                )
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
