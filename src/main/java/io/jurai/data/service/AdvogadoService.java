package io.jurai.data.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import io.jurai.data.ApplicationState;
import io.jurai.data.model.Advogado;
import io.jurai.data.request.RequestHandler;
import io.jurai.data.request.ResponseNotOkException;
import io.jurai.data.serializer.AdvogadoSerializer;
import io.jurai.data.util.JsonUtils;
import io.jurai.util.EventLogger;

import java.util.HashMap;
import java.util.Map;

public class AdvogadoService {
    private final RequestHandler requestHandler = new RequestHandler("http://127.0.0.1:5000");
    AdvogadoSerializer advogadoSerializer = new AdvogadoSerializer();
    private final Gson gson;

    public AdvogadoService() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Advogado.class, new AdvogadoSerializer());
        gson = builder.create();
    }

    public void create(
            String username,
            String email,
            String password,
            String oab
    ) {
        try {
        Map<String, String> advogado = new HashMap<>();
        advogado.put("username", username);
        advogado.put("email", email);
        advogado.put("password", password);
        advogado.put("oab", oab);

        JsonObject response = requestHandler.post("/advogado/new", JsonUtils.asJson(gson.toJson(advogado)));
        EventLogger.log("Response on advogado creation: " +  response.toString());

        } catch(Exception e) {
            EventLogger.logError("Error communicating to API on AdvogadoService.create()");
            EventLogger.logError("Exception: " + e.getMessage());
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

    public Advogado getCurrent() {
        return ApplicationState.getCurrentUser();
    }

    private void setCurrent(Advogado a) {
        ApplicationState.setCurrentUser(a);
    }
}
