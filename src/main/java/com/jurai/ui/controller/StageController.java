package com.jurai.ui.controller;

import com.jurai.App;
import com.jurai.data.ApplicationState;
import com.jurai.ui.modal.ModalManager;
import com.jurai.ui.util.StageType;

public class StageController extends AbstractController<App> {
    @Override
    protected void attachEvents(App app) {
        /*app.getPrimaryScene().getScene().setOnKeyTyped(e -> {
            if(e.getCode() == KeyCode.ESCAPE) {
                ModalManager.getInstance().exitModal();
            }
        });*/
    }

    @Override
    protected void attachNotifiers(App app) {
        ApplicationState.get().addPropertyChangeListener(changeEvent -> {
            if("stageType".equals(changeEvent.getPropertyName())) {
                switchStage(ApplicationState.get().getStageType(), app);
            }
        });
    }

    public void switchStage(StageType stageType, App app) {
        System.out.println("Stage type switched! changing stages");
        switch(stageType) {
            case MAIN_STAGE -> {
                app.getPrimaryStage().show();
                app.getSecondaryStage().hide();
                ModalManager.getInstance().reinitialize(app.getPrimaryScene().getModalRoot(), app.getPrimaryScene().getContent());
                ApplicationState.get().setCurrentStage(app.getPrimaryStage());
                app.getPrimaryScene().setSidebarMode(true);
            }
            case SECONDARY_STAGE -> {
                app.getSecondaryStage().show();
                app.getPrimaryStage().hide();
                ModalManager.getInstance().reinitialize(app.getSecondaryScene().getModalRoot(), app.getSecondaryScene().getContent());
                ApplicationState.get().setCurrentStage(app.getSecondaryStage());
            }
        }
    }
}
