package io.jurai.ui.controller;

import io.jurai.App;
import io.jurai.data.ApplicationState;
import io.jurai.ui.util.StageType;

public class StageController extends AbstractController<App> {
    @Override
    protected void attachEvents(App app) {

    }

    @Override
    protected void attachNotifiers(App app) {
        ApplicationState.addPropertyChangeListener(changeEvent -> {
            if("stageType".equals(changeEvent.getPropertyName())) {
                switchStage(ApplicationState.getStageType(), app);
            }
        });
    }

    public void switchStage(StageType stageType, App app) {
        switch(stageType) {
            case MAIN_STAGE -> {
                app.getPrimaryStage().show();
                app.getSecondaryStage().hide();
            }
            case SECONDARY_STAGE -> {
                app.getSecondaryStage().show();
                app.getPrimaryStage().hide();
            }
        }
    }
}
