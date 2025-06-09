package com.jurai.ui.panes;

import com.jurai.ui.menus.AbstractMenu;
import com.jurai.ui.menus.DocumentChat;
import com.jurai.ui.menus.DocumentChooser;
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

        documentChooser = new DocumentChooser();
        documentChat = new DocumentChat();
    }

    @Override
    protected void layControls() {
        view.getChildren().add(documentChooser.getContent());
    }

    public void setPane(AbstractMenu<?> pane) {
        view.getChildren().clear();
        view.getChildren().add(pane.getContent());
    }



    @Override
    public StackPane getView() {
        return view;
    }
}
