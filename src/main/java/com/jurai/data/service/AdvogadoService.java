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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdvogadoService {
    private final RequestHandler requestHandler = new RequestHandler("http://127.0.0.1:5000");
    private final Gson gson;
    private static final AdvogadoService instance = new AdvogadoService();

    private AdvogadoService() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Advogado.class, new AdvogadoSerializer());
        builder.registerTypeAdapter(Requerente.class, new RequerenteSerializer());
        gson = builder.create();
    }

    public static AdvogadoService getInstance() {
        return instance;
    }

    public void create(
            String username,
            String email,
            String password,
            String oab
    ) throws ResponseNotOkException {
        try {
        JsonObject advogadoJson = new JsonObject();
        advogadoJson.addProperty("username", username);
        advogadoJson.addProperty("email", email);
        advogadoJson.addProperty("password", password);
        advogadoJson.addProperty("oab", oab);

        JsonObject response = requestHandler.post("/advogado/new", advogadoJson);
        EventLogger.log("Response on advogado creation: " +  response.toString());

        } catch(ResponseNotOkException e) {
            EventLogger.logError("Error communicating to API on AdvogadoService.create(): error " + e.getCode());
            throw e;
        }
    }

    public void deauthenticate() {
        ApplicationState.getInstance().setCurrentUser(null);
    }

    public void authenticate(String uname, String password) throws ResponseNotOkException {
        JsonObject body = new JsonObject();
        body.addProperty("username", uname);
        body.addProperty("password", password);

        try {
            JsonObject response = requestHandler.post("/advogado/get", body);
            Advogado advogado = gson.fromJson(response.get("advogado"), Advogado.class);
            ApplicationState.getInstance().setCurrentUser(advogado);
            reloadRequerentes();
        }catch(ResponseNotOkException e) {
            throw e;
        }
    }

    public void reloadRequerentes() throws ResponseNotOkException {
        Map<String, String> body = new HashMap<>();
        Advogado currentUser = ApplicationState.getInstance().getCurrentUser();
        body.put("access_token", currentUser.getAccessToken());
        String jsonifiedBody = gson.toJson(body);

        try {
            JsonObject response = requestHandler.post("/advogado/requerentes", JsonUtils.asJson(jsonifiedBody));
            List<Requerente> requerentes =
                response.get("requerentes_list").getAsJsonArray().asList().stream().
                map(element -> gson.fromJson(element, Requerente.class)).toList();

                currentUser.getRequerentes().clear();
                currentUser.getRequerentes().addAll(requerentes);
            EventLogger.log("Loaded requerentes for advogado " + currentUser.getNome());
        } catch(ResponseNotOkException e) {
            EventLogger.logError("Error communicating to API on AdvogadoService.loadRequerentes(): error " + e.getCode());
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public void addRequerente(Requerente r) throws ResponseNotOkException {
        JsonObject body = JsonUtils.asJson(gson.toJson(r, Requerente.class));
        body.addProperty("access_token", ApplicationState.getInstance().getCurrentUser().getAccessToken());
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
        return ApplicationState.getInstance().getCurrentUser();
    }

    private void setCurrent(Advogado a) {
        ApplicationState.getInstance().setCurrentUser(a);
    }
}
