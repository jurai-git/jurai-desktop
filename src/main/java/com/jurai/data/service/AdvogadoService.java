package com.jurai.data.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.jurai.data.ApplicationState;
import com.jurai.data.model.Advogado;
import com.jurai.data.model.Requerente;
import com.jurai.data.request.RequestHandler;
import com.jurai.data.request.ResponseNotOkException;
import com.jurai.data.model.serializer.AdvogadoSerializer;
import com.jurai.data.model.serializer.RequerenteSerializer;
import com.jurai.data.json.JsonUtils;
import com.jurai.ui.util.AccountMode;
import com.jurai.util.EventLogger;
import javafx.application.Platform;

import java.io.File;
import java.util.List;

public class AdvogadoService {
    private final RequestHandler requestHandler = new RequestHandler(ApplicationState.get().getApiUrl());
    private final Gson gson;
    private static final AdvogadoService instance = new AdvogadoService();

    private AdvogadoService() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Advogado.class, new AdvogadoSerializer());
        builder.registerTypeAdapter(Requerente.class, new RequerenteSerializer());
        gson = builder.create();

        ApplicationState.get().addPropertyChangeListener(e -> {
            if("apiUrl".equals(e.getPropertyName())) {
                requestHandler.setBaseUrl(ApplicationState.get().getApiUrl());
            }
        });
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

            JsonObject response = requestHandler.post("/advogado", advogadoJson);
        } catch(ResponseNotOkException e) {
            EventLogger.logError("Error communicating to API on AdvogadoService::create: error " + e.getCode());
            throw e;
        }
    }

    public void delete() throws ResponseNotOkException {
        try {
            JsonObject body = new JsonObject();
            requestHandler.delete("/advogado", body, "Bearer: " + ApplicationState.get().getCurrentUser().getAccessToken());
            deauthenticate();
        } catch (ResponseNotOkException e) {
            EventLogger.logError("Error communicating to API on AdvogadoService::delete: error " + e.getCode());
            throw e;
        }
    }

    public void deauthenticate() {
        ApplicationState.get().setCurrentUser(null);
        ApplicationState.get().setAccountMode(AccountMode.LOGGING_IN);
    }

    public void authenticate(String uname, String password) throws ResponseNotOkException {
        JsonObject body = new JsonObject();
        body.addProperty("username", uname);
        body.addProperty("password", password);

        try {
            JsonObject response = requestHandler.post("/advogado/auth", body);
            Advogado advogado = gson.fromJson(response.get("advogado"), Advogado.class);
            ApplicationState.get().setCurrentUser(advogado);
            reloadRequerentes();
        }catch(ResponseNotOkException e) {
            throw e;
        }
    }

    public void authenticate(String accessToken) throws ResponseNotOkException {
        try {
            JsonObject response = requestHandler.get("/advogado/auth", "Bearer " + accessToken);
            Advogado advogado = gson.fromJson(response.get("advogado"), Advogado.class);
            ApplicationState.get().setCurrentUser(advogado);
            reloadRequerentes();
        } catch (ResponseNotOkException e) {
            throw e;
        }
    }

    public void reloadRequerentes() throws ResponseNotOkException {
        Advogado currentUser = ApplicationState.get().getCurrentUser();

        try {
            JsonObject response = requestHandler.get("/advogado/requerentes", "Bearer " + currentUser.getAccessToken());
            List<Requerente> requerentes =
                response.get("requerentes_list").getAsJsonArray().asList().stream().
                map(element -> gson.fromJson(element, Requerente.class)).toList();

                Platform.runLater(() -> {
                    currentUser.getRequerentes().clear();
                    currentUser.getRequerentes().addAll(requerentes);
                });
            EventLogger.log("Loaded requerentes for advogado " + currentUser.getNome());
        } catch(ResponseNotOkException e) {
            EventLogger.logError("Error communicating to API on AdvogadoSezrvice.loadRequerentes(): error " + e.getCode());
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public void addRequerente(Requerente r) throws ResponseNotOkException {
        JsonObject body = JsonUtils.asJson(gson.toJson(r, Requerente.class));
        System.out.println(body);
        try {
            JsonObject response = requestHandler.post("/requerente", body, "Bearer: " + ApplicationState.get().getCurrentUser().getAccessToken());
            reloadRequerentes();
        } catch(ResponseNotOkException e) {
            EventLogger.logError("Error communicating to API on AdvogadoService.addRequerente(): error " + e.getCode());
            throw e;
        }
    }

    public void requestRecoveryEmail(String email) throws ResponseNotOkException {
        try {
            JsonObject response = requestHandler.post("/advogado/request-reset/" + email);
        } catch(ResponseNotOkException e) {
            EventLogger.logError("Error communicating to API on AdvogadoService.requestRecoveryEmail(): error " + e.getCode());
            EventLogger.logError("Error communicating to API on AdvogadoService.requestRecoveryEmail(): error " + e.getMessage());
            throw e;
        }
    }

    public void changePicture(File newPicture) throws ResponseNotOkException {
        try {
            requestHandler.postFile("/advogado/pfp", newPicture, "picture", "Bearer " + ApplicationState.get().getCurrentUser().getAccessToken());
        } catch (ResponseNotOkException e) {
            EventLogger.logError("Error communicating to API on AdvogadoService.changePicture(): error " + e.getCode());
            throw e;
        }
    }

    public void deletePicture() throws ResponseNotOkException {
        try {
            requestHandler.delete("/advogado/pfp", null, "Bearer " + ApplicationState.get().getCurrentUser().getAccessToken());
        } catch (ResponseNotOkException e) {
            EventLogger.logError("Error communicating to API on AdvogadoService.deletePicture: error " + e.getMessage());
            throw e;
        }
    }

    public Advogado getCurrent() {
        return ApplicationState.get().getCurrentUser();
    }

    private void setCurrent(Advogado a) {
        ApplicationState.get().setCurrentUser(a);
    }
}
