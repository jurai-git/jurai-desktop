package com.jurai.data.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.jurai.data.ApplicationState;
import com.jurai.data.model.Advogado;
import com.jurai.data.model.Demanda;
import com.jurai.data.model.Requerente;
import com.jurai.data.request.RequestHandler;
import com.jurai.data.request.ResponseNotOkException;
import com.jurai.data.serializer.AdvogadoSerializer;
import com.jurai.data.serializer.DemandaSerializer;
import com.jurai.data.serializer.RequerenteSerializer;
import com.jurai.data.util.JsonUtils;
import com.jurai.util.EventLogger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequerenteService {
    private static final RequerenteService instance = new RequerenteService();
    private final RequestHandler requestHandler = new RequestHandler("http://127.0.0.1:5000");
    private final Gson gson;

    private RequerenteService() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Advogado.class, new AdvogadoSerializer());
        builder.registerTypeAdapter(Requerente.class, new RequerenteSerializer());
        builder.registerTypeAdapter(Demanda.class, new DemandaSerializer());
        gson = builder.create();
    }

    public static RequerenteService getInstance() {
        return instance;
    }

    public void update(Requerente r) throws ResponseNotOkException {
        Advogado currentUser = ApplicationState.getInstance().getCurrentUser();
        JsonObject body = JsonUtils.asJson(gson.toJson(r, Requerente.class));
        body.addProperty("access_token", currentUser.getAccessToken());
        try {
            requestHandler.put("/requerente/update", body);
            AdvogadoService.getInstance().reloadRequerentes();
        } catch (ResponseNotOkException e) {
            EventLogger.logError("Error communicating to API on RequerenteService::update: error " + e.getCode());
            throw e;
        }
    }

    public void addDemanda(Demanda d) {
        Advogado currentUser = ApplicationState.getInstance().getCurrentUser();
        Requerente currentRequerente = ApplicationState.getInstance().getSelectedRequerente();
        if(currentRequerente == null) {
            EventLogger.logError("Error adding demanda: no requerente selected");
            throw new IllegalStateException("No requerente selected");
        }

        JsonObject body = JsonUtils.asJson(gson.toJson(d, Demanda.class));
        body.addProperty("access_token", currentUser.getAccessToken());

        try {
            JsonObject response = requestHandler.post("/demanda/new", body);
            DemandaService.getInstance().reloadDemandas();
        } catch (ResponseNotOkException e) {
            EventLogger.logError("Error communicating to API on RequerenteService::addDemanda: error " + e.getCode());
        }
    }


}
