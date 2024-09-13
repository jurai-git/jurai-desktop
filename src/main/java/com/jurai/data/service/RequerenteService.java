package com.jurai.data.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.jurai.data.ApplicationState;
import com.jurai.data.model.Advogado;
import com.jurai.data.model.Requerente;
import com.jurai.data.request.RequestHandler;
import com.jurai.data.request.ResponseNotOkException;
import com.jurai.data.serializer.AdvogadoSerializer;
import com.jurai.data.serializer.RequerenteSerializer;
import com.jurai.data.util.JsonUtils;
import com.jurai.util.EventLogger;

public class RequerenteService {
    private final RequestHandler requestHandler = new RequestHandler("http://127.0.0.1:5000");
    private final Gson gson;

    public RequerenteService() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Advogado.class, new AdvogadoSerializer());
        builder.registerTypeAdapter(Requerente.class, new RequerenteSerializer());
        gson = builder.create();
    }

    public void update(Requerente r) throws ResponseNotOkException {
        Advogado currentUser = ApplicationState.getCurrentUser();
        JsonObject body = JsonUtils.asJson(gson.toJson(r, Requerente.class));
        body.addProperty("access_token", currentUser.getAccessToken());
        try {
            JsonObject response = requestHandler.put("/requerente/update", body);
            new AdvogadoService().reloadRequerentes();
        } catch (ResponseNotOkException e) {
            EventLogger.logError("Error communicating to API on AdvogadoService.addRequerente(): error " + e.getCode());
            throw e;
        }
    }

}
