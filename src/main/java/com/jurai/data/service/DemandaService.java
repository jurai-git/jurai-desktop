package com.jurai.data.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.jurai.data.AppState;
import com.jurai.data.model.Advogado;
import com.jurai.data.model.Demanda;
import com.jurai.data.model.Requerente;
import com.jurai.data.model.internal_state.AsyncState;
import com.jurai.data.request.RequestHandler;
import com.jurai.data.request.ResponseNotOkException;
import com.jurai.data.model.serializer.AdvogadoSerializer;
import com.jurai.data.model.serializer.DemandaSerializer;
import com.jurai.data.model.serializer.RequerenteSerializer;
import com.jurai.data.json.JsonUtils;
import com.jurai.util.EventLogger;
import javafx.application.Platform;

import java.util.List;

public class DemandaService {
    private static final DemandaService instance = new DemandaService();
    private final RequestHandler requestHandler = new RequestHandler(AppState.get().getApiUrl());
    private final Gson gson;

    private DemandaService() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Advogado.class, new AdvogadoSerializer());
        builder.registerTypeAdapter(Requerente.class, new RequerenteSerializer());
        builder.registerTypeAdapter(Demanda.class, new DemandaSerializer());
        gson = builder.create();

        AppState.get().listen(e -> {
            if("apiUrl".equals(e.getPropertyName())) {
                requestHandler.setBaseUrl(AppState.get().getApiUrl());
            }
        });
    }

    public static DemandaService getInstance() {
        return instance;
    }

    public void update(Demanda d) throws ResponseNotOkException {
        Advogado currentUser = AppState.get().getCurrentUser();
        JsonObject body = JsonUtils.asJson(gson.toJson(d, Demanda.class));

        try {
            requestHandler.patch("/demanda/" + (long) d.getId(), body, "Bearer: " + currentUser.getAccessToken());
            reloadDemandas();
        } catch (ResponseNotOkException e) {
            EventLogger.logError("Error communicating to API on DemandaService::reloadDemandas: error " + e.getCode());
            throw e;
        }

    }

    public void delete(Demanda d) throws ResponseNotOkException {
        Requerente r = AppState.get().getSelectedRequerente();
        JsonObject body = new JsonObject();

        try {
            requestHandler.delete("/demanda/" + (long) d.getId(), body, "Bearer: " + AppState.get().getCurrentUser().getAccessToken());
            r.demandas().remove(d);
            AppState.get().setSelectedDemanda(null);
        } catch (ResponseNotOkException e) {
            EventLogger.logError("Error communicating to API on DemandaService::delete: error " + e.getCode());
            throw e;
        }
    }

    public void reloadDemandas() throws ResponseNotOkException {
        JsonObject body = new JsonObject();
        Requerente selectedRequerente = AppState.get().getSelectedRequerente();
        Advogado currentUser = AppState.get().getCurrentUser();

        try {
            JsonObject response = requestHandler.get("/advogado/requerente/" + (long) selectedRequerente.getIdRequerente() + "/demandas", "Bearer " + currentUser.getAccessToken());
            List<Demanda> demandas =
                    response.get("demanda_list").getAsJsonArray().asList().stream().
                            map(element -> gson.fromJson(element, Demanda.class)).toList();

            Platform.runLater(() -> {
                selectedRequerente.demandas().clear();
                selectedRequerente.demandas().addAll(demandas);
            });
            EventLogger.log("Loaded requerentes for advogado " + currentUser.getNome());
        } catch(ResponseNotOkException e) {
            EventLogger.logError("Error communicating to API on AdvogadoService.reloadDemandas(): error " + e.getCode());
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public List<Demanda> loadSystemWideDemandas() throws ResponseNotOkException {
        try {
            JsonObject response = requestHandler.get("/advogado/demandas", "Bearer " + AppState.get().getCurrentUser().getAccessToken());
            List<Demanda> demandas = response.get("demandas").getAsJsonArray().asList().stream()
                    .map(element -> gson.fromJson(element, Demanda.class)).toList();

            EventLogger.log("Loaded all demandas for advogado " + AppState.get().getCurrentUser().getNome());
            return demandas;
        } catch (ResponseNotOkException e) {
            EventLogger.logError("Error communicating to API on AdvogadoService.loadSystemWideDemandas(): error " + e.getCode());
            throw e;
        }
    }
}
