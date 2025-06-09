package com.jurai.data;

import com.jurai.App;
import com.jurai.data.json.JsonParser;
import com.jurai.data.request.ResponseNotOkException;
import com.jurai.data.service.AdvogadoService;
import com.jurai.ui.util.AccountMode;
import com.jurai.util.EventLogger;
import net.harawata.appdirs.AppDirsFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ApplicationStatePersistor {
    private static volatile ApplicationStatePersistor instance;

    private Path storagePath;
    private Path applicationStateFile;
    private final JsonParser parser;

    public static void initialize() throws IOException {
        if (instance == null) {
            synchronized (ApplicationStatePersistor.class) {
                if (instance == null) {
                    instance = new ApplicationStatePersistor();
                }
            }
        }
    }

    public static ApplicationStatePersistor getInstance() {
        if (instance == null) {
            throw new IllegalStateException("ApplicationStatePersistor not initialized. Initialize it with initialize() first.");
        }
        return instance;
    }

    private ApplicationStatePersistor() throws IOException {
        EventLogger.log("Initialized ApplicationStatePersistor");
        parser = new JsonParser();
        AppState.initialize();
        AppState.get().setAccountMode(AccountMode.LOGGING_IN);

        this.storagePath = Path.of(AppDirsFactory.getInstance().getUserDataDir("JurAI", null, null));
        if(!storagePath.toFile().exists()) {
            storagePath.toFile().mkdir();
        }

        applicationStateFile = storagePath.resolve("application-state.json");
        System.out.println(storagePath);
        System.out.println(applicationStateFile);

        if (!Files.exists(applicationStateFile)) {
            Files.createFile(applicationStateFile);
        }
    }

    public void load() throws IOException {
        EventLogger.log("loading data with ApplicationStatePersistor");
        AppState.initialize();
        System.out.println(applicationStateFile);
        Map<String, String> savedState = parser.fromFile(applicationStateFile);
        if (savedState == null) {
            return;
        }

        try {
            // advogado
            String token = savedState.get("currentUserToken");
            App.getCurrentInstance().addAfterLoadTask(() -> {
                try {
                    AdvogadoService.getInstance().authenticate(token);
                    System.out.println("StageType after persistor: " + AppState.get().getStageType());
                } catch (ResponseNotOkException e) {
                    EventLogger.logWarning("Failed to authenticate advogado after loading json data. Error code: " + e.getCode());
                }
            });

            // remembersUser
            AppState.get().setRemembersUser(savedState.get("remembersUser").equals("true"));

            // Theme
            AppState.get().setUseLightTheme(savedState.get("lightTheme").equals("true"));

            // API Url
            AppState.get().setApiUrl(savedState.get("apiUrl"));

            // Animations
            AppState.get().setUseAnimations(savedState.get("useAnimations").equals("true"));

        } catch (NullPointerException e) {
            e.printStackTrace();
            EventLogger.logWarning("Failed to laod application state data from file.");
        }
    }

    public void save() throws IOException {
        Map<String, String> updatedState = new HashMap<>();
        updatedState.put("remembersUser", AppState.get().remembersUser() ? "true" : "false");
        if(AppState.get().getCurrentUser() != null && AppState.get().remembersUser()) {
            updatedState.put("currentUserToken", AppState.get().getCurrentUser().getAccessToken());
        }

        // Theme
        updatedState.put("lightTheme", AppState.get().isUseLightTheme() ? "true" : "false");

        // API Url
        updatedState.put("apiUrl", AppState.get().getApiUrl());

        // Animations
        updatedState.put("useAnimations", AppState.get().isUseAnimations() ? "true" : "false");

        parser.toFile(updatedState, applicationStateFile);
    }

}
