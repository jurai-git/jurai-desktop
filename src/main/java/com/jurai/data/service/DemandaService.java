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
import com.jurai.data.model.serializer.AdvogadoSerializer;
import com.jurai.data.model.serializer.DemandaSerializer;
import com.jurai.data.model.serializer.RequerenteSerializer;
import com.jurai.data.json.JsonUtils;
import com.jurai.util.EventLogger;
import javafx.application.Application;
import javafx.application.Platform;

import java.util.List;

public class DemandaService {
    private static final DemandaService instance = new DemandaService();
    private final RequestHandler requestHandler = new RequestHandler(ApplicationState.getInstance().getApiUrl());
    private final Gson gson;

    private DemandaService() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Advogado.class, new AdvogadoSerializer());
        builder.registerTypeAdapter(Requerente.class, new RequerenteSerializer());
        builder.registerTypeAdapter(Demanda.class, new DemandaSerializer());
        gson = builder.create();

        ApplicationState.getInstance().addPropertyChangeListener(e -> {
            if("apiUrl".equals(e.getPropertyName())) {
                requestHandler.setBaseUrl(ApplicationState.getInstance().getApiUrl());
            }
        });
    }

    public static DemandaService getInstance() {
        return instance;
    }

    public void update(Demanda d) throws ResponseNotOkException {
        Advogado currentUser = ApplicationState.getInstance().getCurrentUser();
        JsonObject body = JsonUtils.asJson(gson.toJson(d, Demanda.class));
        body.addProperty("id_demanda", d.getId());
        body.addProperty("id_requerente", ApplicationState.getInstance().getSelectedRequerente().getIdRequerente());

        try {
            requestHandler.put("/demanda/update", body, "Bearer: " + currentUser.getAccessToken());
            reloadDemandas();
        } catch (ResponseNotOkException e) {
            EventLogger.logError("Error communicating to API on DemandaService::reloadDemandas: error " + e.getCode());
            throw e;
        }

    }

    public void delete(Demanda d) throws ResponseNotOkException {
        Requerente r = ApplicationState.getInstance().getSelectedRequerente();
        JsonObject body = new JsonObject();
        body.addProperty("demanda_id", d.getId());
        body.addProperty("requerente_id", r.getIdRequerente());

        try {
            requestHandler.delete("/demanda/delete", body, "Bearer: " + ApplicationState.getInstance().getCurrentUser().getAccessToken());
            r.demandas().remove(d);
            ApplicationState.getInstance().setSelectedDemanda(null);
        } catch (ResponseNotOkException e) {
            EventLogger.logError("Error communicating to API on DemandaService::delete: error " + e.getCode());
            throw e;
        }
    }

    public void reloadDemandas() throws ResponseNotOkException {
        JsonObject body = new JsonObject();
        Requerente selectedRequerente = ApplicationState.getInstance().getSelectedRequerente();
        Advogado currentUser = ApplicationState.getInstance().getCurrentUser();

        body.addProperty("id_requerente", selectedRequerente.getIdRequerente());

        try {
            JsonObject response = requestHandler.post("/requerente/demandas", body, "Bearer " + currentUser.getAccessToken());
            List<Demanda> demandas =
                    response.get("demanda_list").getAsJsonArray().asList().stream().
                            map(element -> gson.fromJson(element, Demanda.class)).toList();

            Platform.runLater(() -> {
                selectedRequerente.demandas().clear();
                selectedRequerente.demandas().addAll(demandas);
            });
            EventLogger.log("Loaded requerentes for advogado " + currentUser.getNome());
        } catch(ResponseNotOkException e) {
            EventLogger.logError("Error communicating to API on AdvogadoService.loadRequerentes(): error " + e.getCode());
            System.out.println(e.getMessage());
            throw e;
        }
    }
}
