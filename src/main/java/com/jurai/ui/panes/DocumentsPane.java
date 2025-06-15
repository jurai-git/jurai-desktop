package com.jurai.ui.panes;

import com.jurai.ui.menus.AbstractMenu;
import com.jurai.ui.menus.DocumentChat;
import com.jurai.ui.menus.DocumentChooser;
import com.jurai.ui.viewmodel.DocumentChatVM;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import lombok.Getter;

public class DocumentsPane extends AbstractPane {
    public enum Mode {
        CHOOSER,
        CHAT
    };

    private StackPane view;

    @Getter
    private DocumentChooser documentChooser;
    @Getter
    private DocumentChat documentChat;

    @Override
    protected void initControls() {
        view = new StackPane();
        DocumentChatVM documentChatVM = new DocumentChatVM();

        documentChooser = new DocumentChooser();
        documentChat = new DocumentChat(documentChatVM);
    }

    @Override
    protected void layControls() {
        view.getChildren().add(documentChooser.getContent());
    }

    public void setPane(Node content) {
        view.getChildren().clear();
        view.getChildren().add(content);
    }

    @Override
    public StackPane getView() {
        return view;
    }
}
