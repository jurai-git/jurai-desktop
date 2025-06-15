package com.jurai.ui.viewmodel;

import com.jurai.data.model.AIMessage;
import com.jurai.data.model.ChatMessage;
import com.jurai.data.model.Demanda;
import com.jurai.data.request.ResponseNotOkException;
import com.jurai.data.service.AIService;
import com.jurai.ui.panes.DocumentsPane;
import com.jurai.util.UILogger;
import dev.mgcvale.fluidfx.components.groups.ScrollGroup;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.NoSuchElementException;

public class DocumentChatVM extends ViewModelBase {
    private StringProperty currMsg;
    private AIService aiService;
    private ObservableList<ChatMessage> messages;
    private BooleanProperty loading;
    private BooleanProperty sendDisabled;
    private ScrollGroup scrollGroup;

    public DocumentChatVM() {
        currMsg = new SimpleStringProperty("");
        aiService = AIService.getInstance();
        loading = new SimpleBooleanProperty(false);
        messages = FXCollections.observableArrayList();
        sendDisabled = new SimpleBooleanProperty(true);

        currMsg.addListener((obs, o, n) -> {
            if (currMsg.get().strip().isEmpty()) {
                sendDisabled.set(true);
            } else {
                sendDisabled.set(false);
            }
        });
    }

    public ReadOnlyObjectProperty<Demanda> demanda() {
        return appState.globalSelectedDemandaProperty();
    }
    public StringProperty currentMessage() {
        return currMsg;
    }
    public BooleanProperty loading() {
        return loading;
    }
    public ObservableList<ChatMessage> messages() {
        return messages;
    }
    public BooleanProperty sendDisabled() {
        return sendDisabled;
    }

    public void setScrollGroup(ScrollGroup group) {
        messages.addListener((ListChangeListener<? super ChatMessage>) change -> {
            group.setVvalue(1.1);
        });
    }

    public void sendMessage() {
        String msg = currMsg.get().strip();
        if (msg.isEmpty()) return;
        currMsg.set("");
        messages.add(new ChatMessage(msg, false, null));
        doSend(msg);
    }

    private void doSend(String msg) {
        loading.set(true);

        new Thread(() -> {
            try {
                AIMessage res = aiService.sendMessage(msg);
                Platform.runLater(() -> messages.add(new ChatMessage(res.getMessage(), true, null)));
            } catch (ResponseNotOkException e) {
                Platform.runLater(() -> messages.add(new ChatMessage(msg, true, e)));
            } finally {
                Platform.runLater(() -> loading.set(false));
            }
        }).start();
    }

    public void retryLast() {
        try {
            ChatMessage aiMessage = messages.remove(messages.size() - 1);
            if (!aiMessage.AIMessage()) {
                UILogger.logWarning("Tried to retry sending a message, but the last present message wasn't an AI message. Ignoring.");
                return;
            }
        } catch (NoSuchElementException e) {
            UILogger.logWarning("Tried to retry sending a message, but there were no messages in the chat. Ignoring.");
            return;
        }

        try {
            ChatMessage last = messages.get(messages.size() - 1);
            if (last.AIMessage()) {
                UILogger.logWarning("Tried to retry sending an AI message somehow. Ignoring.");
                return;
            }
            doSend(last.contents());
        } catch (NoSuchElementException e) {
            UILogger.logWarning("Tried to retry sending a message, but the last present message before the AI message didn't exist. Ignoring");
        }
    }

    public void backToChooser() {
        appState.setDocPaneMode(DocumentsPane.Mode.CHOOSER);
    }
}
