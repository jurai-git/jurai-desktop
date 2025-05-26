package com.jurai.data.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.jurai.data.ApplicationState;
import com.jurai.data.model.DemandaAnalysis;
import com.jurai.data.model.serializer.DemandaAnalysisSerializer;
import com.jurai.data.request.RequestHandler;
import com.jurai.data.request.ResponseNotOkException;
import com.jurai.util.EventLogger;

public class AIService {
    private final RequestHandler requestHandler = new RequestHandler(ApplicationState.getInstance().getApiUrl());
    private final Gson gson;

    public AIService() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(DemandaAnalysis.class, new DemandaAnalysisSerializer());
        gson = builder.create();

        ApplicationState.getInstance().addPropertyChangeListener(e -> {
            if("apiUrl".equals(e.getPropertyName())) {
                requestHandler.setBaseUrl(ApplicationState.getInstance().getApiUrl());
            }
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

}
