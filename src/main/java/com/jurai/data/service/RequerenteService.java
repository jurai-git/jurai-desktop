package com.jurai.data.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.jurai.data.AppState;
import com.jurai.data.model.Advogado;
import com.jurai.data.model.Demanda;
import com.jurai.data.model.Requerente;
import com.jurai.data.request.RequestHandler;
import com.jurai.data.request.ResponseNotOkException;
import com.jurai.data.model.serializer.AdvogadoSerializer;
import com.jurai.data.model.serializer.DemandaSerializer;
import com.jurai.data.model.serializer.RequerenteSerializer;
import com.jurai.data.json.JsonUtils;
import com.jurai.util.EventLogger;

public class RequerenteService {
    private static final RequerenteService instance = new RequerenteService();
    private final RequestHandler requestHandler = new RequestHandler(AppState.get().getApiUrl());
    private final Gson gson;

    private RequerenteService() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Advogado.class, new AdvogadoSerializer());
        builder.registerTypeAdapter(Requerente.class, new RequerenteSerializer());
        builder.registerTypeAdapter(Demanda.class, new DemandaSerializer());
        gson = builder.create();

        AppState.get().apiUrlProperty().addListener((obs, o, n) -> {
            requestHandler.setBaseUrl(n);
        });
    }

    public static RequerenteService getInstance() {
        return instance;
    }

    public void update(Requerente r) throws ResponseNotOkException {
        Advogado currentUser = AppState.get().getCurrentUser();
        JsonObject body = JsonUtils.asJson(gson.toJson(r, Requerente.class));
        try {
            requestHandler.patch("/requerente/" + (long) r.getIdRequerente(), body, "Bearer: " + currentUser.getAccessToken());
            AdvogadoService.getInstance().reloadRequerentes();
        } catch (ResponseNotOkException e) {
            EventLogger.logError("Error communicating to API on RequerenteService::update: error " + e.getCode());
            throw e;
        }
    }

    public void delete(Requerente r) throws ResponseNotOkException {
        Advogado currentUser = AppState.get().getCurrentUser();
        JsonObject body = new JsonObject();

        try {
            requestHandler.delete("/requerente/" + (long) r.getIdRequerente(), body, "Bearer " + currentUser.getAccessToken());
            currentUser.getRequerentes().remove(r);
        } catch (ResponseNotOkException e) {
            EventLogger.logError("Error communicating to API on RequerenteService::delete: error: " + e.getCode());
            throw e;
        }
    }

    public void addDemanda(Demanda d) throws ResponseNotOkException{
        Advogado currentUser = AppState.get().getCurrentUser();
        Requerente currentRequerente = AppState.get().getSelectedRequerente();
        if(currentRequerente == null) {
            EventLogger.logError("Error adding demanda: no requerente selected");
            throw new IllegalStateException("No requerente selected");
        }

        JsonObject body = JsonUtils.asJson(gson.toJson(d, Demanda.class));

        try {
            JsonObject response = requestHandler.post("/requerente/" + (long) currentRequerente.getIdRequerente() + "/demanda", body, "Bearer " + currentUser.getAccessToken());
            DemandaService.getInstance().reloadDemandas();
        } catch (ResponseNotOkException e) {
            EventLogger.logError("Error communicating to API on RequerenteService::addDemanda: error " + e.getCode());
            throw e;
        }
    }
}
