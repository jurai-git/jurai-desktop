package com.jurai.ui.menus;

import com.jurai.data.model.Demanda;
import com.jurai.ui.animation.HoverAnimator;
import com.jurai.ui.controls.*;
import com.jurai.ui.controls.fluent.*;
import com.jurai.ui.util.SpacerFactory;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import lombok.Getter;

import static com.jurai.ui.util.ControlWrappers.*;

public class DocumentChooser extends AbstractMenu<HBox> {
    private HBox content;
    private VBox currentDocumentContent;

    ReadOnlyDoubleProperty contentWidthProperty;
    DoubleBinding listWidthProperty;

    private Demanda lastDemanda = null;

    private StringProperty chatTextMessage;

    @Getter
    private SimpleList<Demanda> docList;

    @Override
    protected void initControls() {
        content = new HBox();
        contentWidthProperty = content.widthProperty();
        listWidthProperty = contentWidthProperty.multiply(0.55);

        docList = new SimpleList<>("Seus documentos");
        currentDocumentContent = new VBox();
        VBox.setVgrow(currentDocumentContent, Priority.ALWAYS);
        currentDocumentContent.getStyleClass().add("spacing-4");

        chatTextMessage = new SimpleStringProperty("");
    }

    @Override
    protected void layControls() {
        ReadOnlyDoubleProperty listHeightBinding = docList.heightProperty();

        content.getStyleClass().addAll("pane", "spacing-4");
        content.getChildren().addAll(
                SpacerFactory.hSpacer(12),
                new SplitGroup().withHgrow(Priority.ALWAYS).withDividerPosition(0, 0.6).withConstraints(0, new Pair<>(0.3, 0.8)).withItems(
                        new VGroup().withHgrow(Priority.ALWAYS).withChildren(
                                wrapStyleClasses(new Label("Seus documentos"), "header", "pb-2-i"),
                                wrapStyleClasses(new Label("Selecione um documento para fazer sua análise, accessar o chat e mais"), "subsubheader", "pb-4-i"),
                                wrapVgrow(docList)
                        ).bindPrefWidthProperty(listWidthProperty),
                        new VGroup().withHgrow(Priority.ALWAYS).withChildren(
                                wrapStyleClasses(new Label("Documento selecionado"), "subheader"),
                                SpacerFactory.vSpacer(Priority.ALWAYS),
                                currentDocumentContent
                        ).bindMinWidthProperty(listWidthProperty)
                )
        );
        currentDocumentContent.maxHeightProperty().bind(listHeightBinding);
        currentDocumentContent.prefHeightProperty().bind(listHeightBinding);
        updateContent(null);
    }

    public void updateContent(Demanda selectedDemanda) {
        lastDemanda = selectedDemanda;
        if (selectedDemanda == null) {
            currentDocumentContent.getChildren().setAll(
                    new VGroup().withStyleClass("small-content-box", "p-5").withVgrow(Priority.ALWAYS).withChildren(
                            new Label("Você não tem nenhuma demanda selecionada!")
                    )
            );
        } else {
            loadContentfulLayout();
        }
    }

    private void loadContentfulLayout() {
        if (lastDemanda == null) {
            updateContent(null);
            return;
        }
        currentDocumentContent.getChildren().setAll(
                new VGroup().withVgrow(Priority.ALWAYS).withStyleClass("small-content-box", "p-5").withChildren(
                        new HGroup().withChildren(
                                wrapStyleClasses(new Label(lastDemanda.getNome()), "subheader"),
                                SpacerFactory.hSpacer(Priority.ALWAYS),
                                new FluentButton("Ir ao Chat").withStyleClass("blue-button").applyCustomFunction(HoverAnimator::animateAll)
                        ),
                        wrapStyleClasses(new Label("Dono: " + lastDemanda.getDono()), "subsubheader"),
                        SpacerFactory.vSpacer(16),
                        new ScrollableGroup().withFixedWitdh().withVgrow(Priority.ALWAYS).vbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED).hbarPolicy(ScrollPane.ScrollBarPolicy.NEVER).withContent(
                                new VGroup().withStyleClass("spacing-4").withChildren(
                                        new ReactiveLabel("Foro: ", lastDemanda.foroProperty()),
                                        new ReactiveLabel("Competencia: ", lastDemanda.competenciaProperty()),
                                        new ReactiveLabel("Ass. Principal: ", lastDemanda.assuntoPrincipalProperty()),
                                        new ReactiveLabel("Status: ", lastDemanda.statusDemandaProperty()),
                                        new BooleanReactiveLabel("Tem Pedido de Liminar: ", lastDemanda.pedidoLiminarProperty()),
                                        new BooleanReactiveLabel("Tem Segredo de Justiça: ", lastDemanda.segJusticaProperty()),
                                        new BooleanReactiveLabel("Tem Dispensa Legal: ", lastDemanda.dispensaLegalProperty()),
                                        new BooleanReactiveLabel("Tem Justiça Gratuita: ", lastDemanda.justicaGratuitaProperty()),
                                        new BooleanReactiveLabel("Tem Guia de Custas: ", lastDemanda.guiaCustasProperty()),
                                        new ReactiveLabel("Valor da ação: ", lastDemanda.valorAcaoProperty()),
                                        new ReactiveLabel("Resumo: ", lastDemanda.resumoProperty())
                                )
                        ),
                        new HGroup().withStyleClass("spacing-4").withChildren(
                                new FluentTextField().withPrompt("Fale com o JurAI sobre o processo").bindTextValue(chatTextMessage).withStyleClass("text-field-base").withHgrow(Priority.ALWAYS),
                                new FluentButton("Enviar").withStyleClass("blue-button").applyCustomFunction(HoverAnimator::animateAllStronger)
                        )
                )
        );
    }

    @Override
    public HBox getContent() {
        return content;
    }
}
