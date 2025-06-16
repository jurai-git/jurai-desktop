package com.jurai.ui.viewmodel;

import com.jurai.data.AppState;
import com.jurai.data.GlobalEvents;
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
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Getter;

import java.util.NoSuchElementException;

public class DocumentChatVM extends ViewModelBase {
    private StringProperty currMsg;
    private AIService aiService;
    private ObservableList<ChatMessage> messages;
    private BooleanProperty sendDisabled;
    private ObjectProperty<Image> pfp;
    private final Image fallbackPfp;

    @Getter
    private Image juraiPfp;

    public DocumentChatVM() {
        currMsg = new SimpleStringProperty("");
        AppState.get().pretypedChatMessageProperty().addListener((obs, o, n) -> {
            currMsg.set(n);
        });
        aiService = AIService.getInstance();
        messages = FXCollections.observableArrayList();
        sendDisabled = new SimpleBooleanProperty(true);

        fallbackPfp = new Image(getClass().getResource(appState.getFallbackPfpPath()).toExternalForm());
        pfp = new SimpleObjectProperty<>();
        loadPfp();
        appState.apiUrlProperty().addListener((obs, o, n) -> loadPfp());
        appState.currentUserProperty().addListener((obs, o, b) -> loadPfp());
        GlobalEvents.get().onPfpChanged(this::loadPfp);

        juraiPfp = new Image(getClass().getResource(appState.getJuraiIcon()).toExternalForm());

        currMsg.addListener((obs, o, n) -> {
            if (currMsg.get().strip().isEmpty()) {
                sendDisabled.set(true);
            } else {
                sendDisabled.set(false);
            }
        });

        appState.globalSelectedDemandaProperty().addListener((obs, o, n) -> {
            // when the demanda changes, we need to reload the chat for that demanda.
            reloadChatHistory(n);
        });

        GlobalEvents.get().onSentMessageFromDocList(() -> {
            if (appState.getGlobalSelectedDemanda() != null && !currMsg.get().isBlank()) {
                sendMessage();
            }
        });
    }

    private void reloadChatHistory(Demanda d) {
        // clear everything up from the last session
        currMsg.set("");
        messages.clear();

        // load messages from this session from the API

    }

    public ReadOnlyObjectProperty<Demanda> demanda() {
        return appState.globalSelectedDemandaProperty();
    }
    public StringProperty currentMessage() {
        return currMsg;
    }
    public ObservableList<ChatMessage> messages() {
        return messages;
    }
    public BooleanProperty sendDisabled() {
        return sendDisabled;
    }
    public ObservableValue<Image> pfp() {
        return pfp;
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
        messages.add(new ChatMessage(null, true, null));

        new Thread(() -> {
            try {
                AIMessage res = aiService.sendMessage(msg);

                Platform.runLater(() -> messages.add(new ChatMessage(res.getMessage(), true, null)));
            } catch (ResponseNotOkException e) {
                Platform.runLater(() -> messages.add(new ChatMessage(msg, true, e)));
            } finally {
                Platform.runLater(() -> messages.removeIf(m -> m.contents() == null));
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

    private void loadPfp() {
        if (appState.getCurrentUser() == null || appState.getApiUrl() == null) {
            pfp.set(fallbackPfp);
            return;
        }

        pfp.set(new Image(appState.getApiUrl() + "/advogado/" + appState.getCurrentUser().getId() + "/pfp"));
        pfp.get().errorProperty().addListener((obs, o, n) -> {
            if (pfp.get().isError()) {
                pfp.set(fallbackPfp);
            }
        });
    }
}
