package com.jurai.ui.menus;

import com.jurai.data.model.ChatMessage;
import com.jurai.data.request.ResponseNotOkException;
import com.jurai.ui.animation.HoverAnimator;
import com.jurai.ui.error.DocChatErrorTranslator;
import com.jurai.ui.viewmodel.DocumentChatVM;
import com.jurai.util.TextAreaUtils;
import dev.mgcvale.fluidfx.components.controls.FButton;
import dev.mgcvale.fluidfx.components.controls.FLabel;
import dev.mgcvale.fluidfx.components.controls.FTextArea;
import dev.mgcvale.fluidfx.components.core.BoxSpacing;
import dev.mgcvale.fluidfx.components.groups.HGroup;
import dev.mgcvale.fluidfx.components.groups.ScrollGroup;
import dev.mgcvale.fluidfx.components.groups.StackGroup;
import dev.mgcvale.fluidfx.components.groups.VGroup;
import dev.mgcvale.fluidfx.components.layout.Pad;
import dev.mgcvale.fluidfx.components.layout.Spacers;
import dev.mgcvale.fluidfx.components.layout.Wrappers;
import dev.mgcvale.fluidfx.components.util.Ref;
import dev.mgcvale.fluidfx.reactive.BooleanMapper;
import dev.mgcvale.fluidfx.reactive.ListMapper;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class DocumentChat extends VBox {
    private DocumentChatVM vm;
    private Ref<VGroup> messagesBox;


    public DocumentChat(DocumentChatVM vm) {
        super();
        this.vm = vm;

        initControls();
        layControls();
    }

    protected void initControls() {

    }

    protected void layControls() {
        getStyleClass().addAll("pane", "spacing-4");
        setAlignment(Pos.CENTER);
        messagesBox = new Ref<>(null);
        Ref<FTextArea> textAreaRef = new Ref<>(null);
        Ref<HGroup> textAreaGroupRef = new Ref<>(null);
        DoubleProperty inputHeight = new SimpleDoubleProperty(0);
        Ref<ScrollGroup> scrollGroupRef = new Ref<>(null);

        getChildren().addAll(
            new VGroup().wSpacing(8).wChildren(
                new HGroup().wAlignment(Pos.CENTER_LEFT).wSpacing(12).wChildren(
                    new FButton("Voltar para os documentos").onAction(e -> vm.backToChooser()),
                    new FLabel("JurAI Chat").wStyleClass("header")
                ),
                new FLabel().inText(vm.demanda().asString("Demanda selecionada: %s")).wStyleClass("subheader")
            ),
            new StackGroup().wMaxHeight(Region.USE_COMPUTED_SIZE).vgrow().wMaxWidth(900).wChildren(
                new ScrollGroup().grabInstance(scrollGroupRef).wVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED).wPrefHeight(0).wFixedWitdh().wContent(
                    new VGroup().wChildren(
                        new VGroup().wSpacing(24).grabInstance(messagesBox).inChildren(
                            ListMapper.map(vm.messages(), this::createMessageUI)
                        ),
                        Spacers.vSpacer(inputHeight.add(24))
                    )

                ),
                new VGroup().wAlignment(Pos.CENTER).inVisible(Bindings.isEmpty(messagesBox.ref.getChildren())).wStackAlignment(Pos.CENTER).wChildren(
                    new FLabel("Essa conversa está vazia!").wStyleClass("subheader"),
                    new FLabel("Envie uma mensagem para iniciar a conversa com o JurAI.").wWrap(true)
                ),
                new HGroup().outHeightProperty(inputHeight).wMaxHeight(Region.USE_PREF_SIZE).wStackAlignment(Pos.BOTTOM_CENTER).grabInstance(textAreaGroupRef).wAlignment(Pos.BOTTOM_CENTER).wSpacing(12).wChildren(
                    new FTextArea().grabInstance(textAreaRef).biText(vm.currentMessage()).hgrow().wStyleClass("text-field-base", "text-area-field", "chat-input").wPrompt("Escreva sua mensagem").wMaxHeight(Region.USE_PREF_SIZE).wPadding(Pad.zero()).wPrefRowCount(2).wWrap(true).onKeyPressed(e -> {
                        Platform.runLater(() -> {
                            int lines = TextAreaUtils.getVisualLineCount(textAreaRef.ref) - 1;
                            lines = Math.min(Math.max(2, lines), 5);
                            textAreaRef.ref.setPrefRowCount(lines);
                        });
                    }),
                    new FButton("Enviar").onAction(e -> vm.sendMessage()).wPadding(Pad.all(8).addX(16)).wStyleClass("blue-button").wTranslateY(-4).applyCustomFunction(HoverAnimator::animateAll).inDisable(vm.sendDisabled())
                )
            )
        );
        vm.setScrollGroup(scrollGroupRef.ref);
    }

    private Node createMessageUI(ChatMessage msg) {
        List<Node> children = new ArrayList<>();

        if (msg.error() == null) { // no error
            if (msg.contents() == null) { // this means the message is loading
                children.add(new FLabel("Carregando resposata..."));
            } else { // message is done
                children.add(new FLabel().inMaxWidth(messagesBox.ref.widthProperty().multiply(0.8)).wText(
                    msg.AIMessage() ? "JurAI: " + msg.contents() : msg.contents()).wStyleClass(msg.AIMessage() ? "ai-message" : "user-message").wWrap(true)
                );
            }
        } else { // error occurred, load error message
            children.add(
                new VGroup().wSpacing(4).wChildren(
                    new FLabel("Ocorreu um erro!").wStyleClass("text-red"),
                    new FLabel(DocChatErrorTranslator.translateMessageError((ResponseNotOkException) msg.error()))
                )
            );
        }

        return new HGroup()
            .wBoxSpacing(msg.AIMessage() ? BoxSpacing.LAYOUT_START : BoxSpacing.LAYOUT_END)
            .wChildren(children);
    }


}
