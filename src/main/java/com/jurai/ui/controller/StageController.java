package com.jurai.ui.controller;

import com.jurai.App;
import com.jurai.data.ApplicationData;
import com.jurai.data.ApplicationState;
import com.jurai.ui.util.StageType;

public class StageController extends AbstractController<App> {
    @Override
    protected void attachEvents(App app) {

    }

    @Override
    protected void attachNotifiers(App app) {
        ApplicationState.getInstance().addPropertyChangeListener(changeEvent -> {
            if("stageType".equals(changeEvent.getPropertyName())) {
                switchStage(ApplicationState.getInstance().getStageType(), app);
            }
        });
    }

    public void switchStage(StageType stageType, App app) {
        switch(stageType) {
            case MAIN_STAGE -> {
                app.getPrimaryStage().show();
                app.getSecondaryStage().hide();
                ApplicationState.getInstance().setCurrentStage(app.getPrimaryStage());
                app.getPrimaryScene().setSidebarMode(true);
            }
            case SECONDARY_STAGE -> {
                app.getSecondaryStage().show();
                app.getPrimaryStage().hide();
                ApplicationState.getInstance().setCurrentStage(app.getSecondaryStage());
            }
        }
    }
}
