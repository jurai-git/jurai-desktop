package com.jurai.data.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.jurai.data.AppState;
import com.jurai.data.model.AIMessage;
import com.jurai.data.model.DemandaAnalysis;
import com.jurai.data.model.serializer.DemandaAnalysisSerializer;
import com.jurai.data.request.RequestHandler;
import com.jurai.data.request.ResponseNotOkException;
import com.jurai.util.EventLogger;
import lombok.Getter;

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
            JsonObject res = requestHandler.post("/ai/rag", req, auth);

            if (!res.has("response")) {
                throw new ResponseNotOkException(BAD_RESPONSE_ERROR);
            }

            return new AIMessage(res.get("response").getAsString());
        } catch (ResponseNotOkException e) {
            EventLogger.logError("Error communicating to API on AIService::sendMessage: error " + e.getCode());
            throw e;
        }
    }

}
