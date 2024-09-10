package com.jurai.data.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdvogadoService {
    private final RequestHandler requestHandler = new RequestHandler("http://127.0.0.1:5000");
    private final Gson gson;

    public AdvogadoService() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Advogado.class, new AdvogadoSerializer());
        builder.registerTypeAdapter(Requerente.class, new RequerenteSerializer());
        gson = builder.create();
    }

    public void create(
            String username,
            String email,
            String password,
            String oab
    ) throws ResponseNotOkException {
        try {
        Map<String, String> advogado = new HashMap<>();
        advogado.put("username", username);
        advogado.put("email", email);
        advogado.put("password", password);
        advogado.put("oab", oab);

        JsonObject response = requestHandler.post("/advogado/new", JsonUtils.asJson(gson.toJson(advogado)));
        EventLogger.log("Response on advogado creation: " +  response.toString());

        } catch(ResponseNotOkException e) {
            EventLogger.logError("Error communicating to API on AdvogadoService.create(): error " + e.getCode());
            throw e;
        }
    }

    public void deauthenticate() {
        ApplicationState.setCurrentUser(null);
    }

    public void authenticate(String uname, String password) throws ResponseNotOkException {
        Map<String, String> body = new HashMap<>();
        body.put("password", password);
        body.put("username", uname);
        String jsonifiedBody = gson.toJson(body);

        try {
            JsonObject response = requestHandler.post("/advogado/get", JsonUtils.asJson(jsonifiedBody));
            Advogado advogado = gson.fromJson(response.get("advogado"), Advogado.class);
            ApplicationState.setCurrentUser(advogado);
        }catch(ResponseNotOkException e) {
            throw e;
        }
    }
    public void reloadRequerentes() throws ResponseNotOkException {
        Map<String, String> body = new HashMap<>();
        Advogado currentUser = ApplicationState.getCurrentUser();
        body.put("access_token", currentUser.getAccessToken());
        String jsonifiedBody = gson.toJson(body);

        try {
            JsonObject response = requestHandler.post("/advogado/requerentes", JsonUtils.asJson(jsonifiedBody));
            List<Requerente> requerentes =
                response.get("requerentes_list").getAsJsonArray().asList().stream().
                map(element -> gson.fromJson(element, Requerente.class)).toList();
            currentUser.getRequerentes().removeAll(currentUser.getRequerentes());
            currentUser.getRequerentes().addAll(requerentes);
        } catch(ResponseNotOkException e) {
            EventLogger.logError("Error communicating to API on AdvogadoService.loadRequerentes(): error " + e.getCode());
            throw e;
        }
    }

    public void addRequerente(Requerente r) throws ResponseNotOkException {
        JsonObject body = JsonUtils.asJson(gson.toJson(r, Requerente.class));
        body.addProperty("access_token", ApplicationState.getCurrentUser().getAccessToken());
        System.out.println(body);
        try {
            JsonObject response = requestHandler.post("/requerente/new", body);

            reloadRequerentes();
        } catch(ResponseNotOkException e) {
            EventLogger.logError("Error communicating to API on AdvogadoService.addRequerente(): error " + e.getCode());
            throw e;
        }
    }


    public Advogado getCurrent() {
        return ApplicationState.getCurrentUser();
    }

    private void setCurrent(Advogado a) {
        ApplicationState.setCurrentUser(a);
    }
}
