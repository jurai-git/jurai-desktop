package com.jurai.data.service;

import com.google.gson.*;
import com.jurai.data.AppState;
import com.jurai.data.model.AIMessage;
import com.jurai.data.model.ChatMessage;
import com.jurai.data.model.Demanda;
import com.jurai.data.model.DemandaAnalysis;
import com.jurai.data.model.serializer.DemandaAnalysisSerializer;
import com.jurai.data.request.RequestHandler;
import com.jurai.data.request.ResponseNotOkException;
import com.jurai.util.EventLogger;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static com.jurai.data.request.InternalErrorCodes.BAD_RESPONSE_ERROR;

public class AIService {
    private final RequestHandler requestHandler = new RequestHandler(AppState.get().getApiUrl());
    private final Gson gson;

    @Getter
    private static final AIService instance = new AIService();


    private AIService() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(DemandaAnalysis.class, new DemandaAnalysisSerializer());
        gson = builder.create();

        AppState.get().apiUrlProperty().addListener((obs, o, n) -> {
            requestHandler.setBaseUrl(AppState.get().getApiUrl());
        });
    }

    public DemandaAnalysis analyzeDemanda(String ementa) throws ResponseNotOkException {
        JsonObject object = new JsonObject();
        object.addProperty("text", ementa.toLowerCase());
        requestHandler.post("/ai/probability", object);
        try {
            return gson.fromJson(requestHandler.post("/ai/probability", object), DemandaAnalysis.class);
        } catch (ResponseNotOkException e) {
            EventLogger.logError("Error communicating to API on AIService::analyzeDemanda: error " + e.getCode());
            throw e;
        }
    }

    public AIMessage sendMessage(String message) throws ResponseNotOkException {
        try {
            JsonObject req = new JsonObject();
            req.addProperty("query", message);
            String auth = "Bearer " + AppState.get().getCurrentUser().getAccessToken();
            JsonObject res = requestHandler.post("/ai/chat", req, auth);

            if (!res.has("response")) {
                throw new ResponseNotOkException(BAD_RESPONSE_ERROR);
            }

            return new AIMessage(res.get("response").getAsString());
        } catch (ResponseNotOkException e) {
            EventLogger.logError("Error communicating to API on AIService::sendMessage: error " + e.getCode());
            throw e;
        }
    }

    public AIMessage sendMessageOnDemandaChat(String message, Demanda d, boolean useRAG) throws ResponseNotOkException {
        try {
            JsonObject req = new JsonObject();
            req.addProperty("query", message);
            req.addProperty("rag", useRAG);
            String auth = "Bearer " + AppState.get().getCurrentUser().getAccessToken();
            JsonObject res = requestHandler.post("/demanda/" + d.getId() + "/chat", req, auth);

            if (!res.has("response")) {
                throw new ResponseNotOkException(BAD_RESPONSE_ERROR);
            }

            return new AIMessage(res.get("response").getAsString());
        } catch (ResponseNotOkException e) {
            EventLogger.logError("Error communicating to API on AIService::sendMessageOnDemandaChat: error " + e.getCode());
            throw e;
        }
    }

    public List<ChatMessage> getMessages(Demanda d) throws ResponseNotOkException {
        try {
            String auth = "Bearer " + AppState.get().getCurrentUser().getAccessToken();
            JsonObject res = requestHandler.get("/demanda/" + d.getId() + "/chat", auth);

            if (!res.has("chat")) {
                throw new ResponseNotOkException(BAD_RESPONSE_ERROR);
            }

            JsonObject chat = res.get("chat").getAsJsonObject();
            int msgCount = chat.get("message_count").getAsInt();
            List<JsonElement> messages = chat.get("messages").getAsJsonArray().asList();

            List<ChatMessage> messagesFinal = new ArrayList<>(msgCount);

            for (JsonElement message : messages) {
                JsonObject msgObj = message.getAsJsonObject();
                boolean isAiMessage = msgObj.get("role").getAsString().equals("model");
                messagesFinal.add(new ChatMessage(msgObj.get("contents").getAsString(), msgObj.get("message_type").getAsString(), isAiMessage, null));
            }

            System.out.println(messagesFinal);

            return messagesFinal;
        } catch (ResponseNotOkException e) {
            EventLogger.logError("Error communicating to API on AIService::getMessages: error: " + e.getCode());
            throw e;
        }

    }

}
