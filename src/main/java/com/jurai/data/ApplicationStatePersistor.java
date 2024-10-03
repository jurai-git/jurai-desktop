package com.jurai.data;

import com.jurai.App;
import com.jurai.data.json.JsonParser;
import com.jurai.data.json.JsonUtils;
import com.jurai.data.model.Advogado;
import com.jurai.data.model.serializer.AdvogadoSerializer;
import com.jurai.data.request.ResponseNotOkException;
import com.jurai.data.service.AdvogadoService;
import com.jurai.ui.util.AccountMode;
import com.jurai.ui.util.StageType;
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
        ApplicationState.initialize();
        ApplicationState.getInstance().setAccountMode(AccountMode.LOGGING_IN);

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
        ApplicationState.initialize();
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
                    System.out.println("StageType after persistor: " + ApplicationState.getInstance().getStageType());
                } catch (ResponseNotOkException e) {
                    EventLogger.logWarning("Failed to authenticate advogado after loading json data. Error code: " + e.getCode());
                }
            });

            // remembersUser
            ApplicationState.getInstance().setRemembersUser(savedState.get("remembersUser").equals("true"));

        } catch (NullPointerException e) {
            e.printStackTrace();
            EventLogger.logWarning("Failed to laod application state data from file.");
        }
    }

    public void save() throws IOException {
        Map<String, String> updatedState = new HashMap<>();
        updatedState.put("remembersUser", ApplicationState.getInstance().remembersUser() ? "true" : "false");
        if(ApplicationState.getInstance().getCurrentUser() != null && ApplicationState.getInstance().remembersUser()) {
            updatedState.put("currentUserToken", ApplicationState.getInstance().getCurrentUser().getAccessToken());
        }

        parser.toFile(updatedState, applicationStateFile);
    }

}
