package com.jurai.ui.menus;

import com.jurai.data.model.Demanda;
import com.jurai.ui.animation.HoverAnimator;
import com.jurai.ui.controls.*;
import com.jurai.ui.controls.fluent.*;
import com.jurai.ui.util.SpacerFactory;
import com.jurai.util.Ref;
import dev.mgcvale.fluidfx.components.controls.FButton;
import dev.mgcvale.fluidfx.components.controls.FTextField;
import dev.mgcvale.fluidfx.components.groups.HGroup;
import dev.mgcvale.fluidfx.components.groups.ScrollGroup;
import dev.mgcvale.fluidfx.components.groups.VGroup;
import dev.mgcvale.fluidfx.reactive.FluidText;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import dev.mgcvale.fluidfx.components.controls.FLabel;
import lombok.Getter;

import static dev.mgcvale.fluidfx.components.layout.Wrappers.wStyleClasses;
import static dev.mgcvale.fluidfx.components.layout.Wrappers.wVgrow;

public class DocumentChooser extends AbstractMenu<HBox> {
    private HBox content;
    private VBox currentDocumentContent;

    private Demanda lastDemanda = null;

    private StringProperty chatTextMessage;

    @Getter
    private SimpleList<Demanda> docList;

    @Override
    protected void initControls() {
        content = new HBox();

        docList = new SimpleList<>("Seus documentos");
        currentDocumentContent = new VBox();
        VBox.setVgrow(currentDocumentContent, Priority.ALWAYS);
        currentDocumentContent.getStyleClass().add("spacing-4");

        chatTextMessage = new SimpleStringProperty("");
    }

    @Override
    protected void layControls() {
        ReadOnlyDoubleProperty listHeightBinding = docList.heightProperty();
        Ref<ReadOnlyDoubleProperty> splitGroupWidthProperty = new Ref<>(null);

        content.getStyleClass().addAll("pane", "spacing-4");
        content.getChildren().addAll(
                SpacerFactory.hSpacer(12),
                new SplitGroup().wHgrow(Priority.ALWAYS).wDividerPosition(0, 0.6).wConstraints(0, new Pair<>(0.3, 0.8)).wItems(
                        new VGroup().wHgrow(Priority.ALWAYS).wChildren(
                                wStyleClasses(new Label("Seus documentos"), "header", "pb-2-i"),
                                wStyleClasses(new Label("Selecione um documento para fazer sua análise, accessar o chat e mais"), "subsubheader", "pb-4-i"),
                                wVgrow(docList)
                        ),
                        new VGroup().wHgrow(Priority.ALWAYS).wChildren(
                                wStyleClasses(new Label("Documento selecionado"), "subheader"),
                                SpacerFactory.vSpacer(Priority.ALWAYS),
                                currentDocumentContent
                        )
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
                    new VGroup().wStyleClass("small-content-box", "p-5").wVgrow(Priority.ALWAYS).wChildren(
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
                new VGroup().wVgrow(Priority.ALWAYS).wStyleClass("small-content-box", "p-5").wChildren(
                        new HGroup().wChildren(
                                wStyleClasses(new Label(lastDemanda.getNome()), "subheader"),
                                SpacerFactory.hSpacer(Priority.ALWAYS),
                                new FButton("Ir ao Chat").wStyleClass("blue-button").applyCustomFunction(HoverAnimator::animateAll)
                        ),
                        wStyleClasses(new Label("Dono: " + lastDemanda.getDono()), "subsubheader"),
                        SpacerFactory.vSpacer(16),
                        new ScrollGroup().wFixedWitdh().wVgrow(Priority.ALWAYS).wVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED).wHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER).wContent(
                                new VGroup().wStyleClass("spacing-4").wChildren(
                                        new FLabel().inText(FluidText.build("Foro: ", lastDemanda.foroProperty())),
                                        new FLabel().inText(FluidText.build("Competencia: ", lastDemanda.competenciaProperty())),
                                        new FLabel().inText(FluidText.build("Ass. principal: ", lastDemanda.assuntoPrincipalProperty())),
                                        new FLabel().inText(FluidText.build("Status: ", lastDemanda.statusDemandaProperty())),
                                        new BooleanReactiveLabel("Tem Pedido de Liminar: ", lastDemanda.pedidoLiminarProperty()),
                                        new BooleanReactiveLabel("Tem Segredo de Justiça: ", lastDemanda.segJusticaProperty()),
                                        new BooleanReactiveLabel("Tem Dispensa Legal: ", lastDemanda.dispensaLegalProperty()),
                                        new BooleanReactiveLabel("Tem Justiça Gratuita: ", lastDemanda.justicaGratuitaProperty()),
                                        new BooleanReactiveLabel("Tem Guia de Custas: ", lastDemanda.guiaCustasProperty()),
                                        new FLabel().inText(FluidText.build("Valor ação: ", lastDemanda.valorAcaoProperty())),
                                        new FLabel().inText(FluidText.build("Resumo: ", lastDemanda.resumoProperty()))
                                )
                        ),
                        new HGroup().wStyleClass("spacing-4").wChildren(
                                new FTextField().wPrompt("Fale com o JurAI sobre o processo").inText(chatTextMessage).wStyleClass("text-field-base").wHgrow(Priority.ALWAYS),
                                new FButton("Enviar").wStyleClass("blue-button").applyCustomFunction(HoverAnimator::animateAllStronger)
                        )
                )
        );
    }

    @Override
    public HBox getContent() {
        return content;
    }
}
