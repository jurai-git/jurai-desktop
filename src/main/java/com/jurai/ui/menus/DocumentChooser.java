package com.jurai.ui.menus;

import com.jurai.data.model.Demanda;
import com.jurai.ui.controls.SimpleList;
import com.jurai.ui.controls.StackGroup;
import com.jurai.ui.controls.VGroup;
import com.jurai.ui.util.SpacerFactory;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import lombok.Getter;

import static com.jurai.ui.util.ControlWrappers.*;

public class DocumentChooser extends AbstractMenu<HBox> {
    private HBox content;
    private VBox currentDocumentContent;

    @Getter
    private SimpleList<Demanda> docList;

    @Override
    protected void initControls() {
        content = new HBox();
        docList = new SimpleList<>("Seus documentos");
        currentDocumentContent = new VBox();
    }

    @Override
    protected void layControls() {
        ReadOnlyDoubleProperty listHeightBinding = docList.heightProperty();

        content.getStyleClass().addAll("pane", "spacing-4");
        content.getChildren().addAll(
                SpacerFactory.hSpacer(12),
                new VGroup().withHgrow(Priority.ALWAYS).withChildren(
                        wrapStyleClasses(new Label("Seus documentos"), "header", "pb-2-i"),
                        wrapStyleClasses(new Label("Selecione um documento para fazer sua análise, accessar o chat e mais"), "subsubheader", "pb-4-i"),
                        wrapVgrow(docList)
                ),
                new VGroup().withHgrow(Priority.ALWAYS).withChildren(
                        wrapStyleClasses(new Label("Documento selecionado"), "subheader"),
                        SpacerFactory.vSpacer(Priority.ALWAYS),
                        new StackGroup().withStyleClass("small-content-box", "p-5").withVgrow(Priority.ALWAYS).withChildren(
                                currentDocumentContent
                        ).bindMaxHeightProperty(listHeightBinding).bindPrefHeightProperty(listHeightBinding)
                )
        );
        updateContent(null);
    }

    public void updateContent(Demanda selectedDemanda) {
        if (selectedDemanda == null) {
            currentDocumentContent.getChildren().setAll(
                    new Label("Você não tem nenhuma demanda selecionada!")
            );
        } else {
            currentDocumentContent.getChildren().setAll(
                    wrapStyleClasses(new Label(selectedDemanda.getNome()), "subheader"),
                    wrapStyleClasses(new Label("Dono: " + selectedDemanda.getDono()), "subsubheader"),
                    SpacerFactory.vSpacer(12)
            );
        }
    }

    @Override
    public HBox getContent() {
        return content;
    }
}
