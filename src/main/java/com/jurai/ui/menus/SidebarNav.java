package com.jurai.ui.menus;

import com.jurai.data.ApplicationData;
import com.jurai.data.AppState;
import com.jurai.ui.controls.ArrowToggleButton;
import com.jurai.ui.controls.fluent.HGroup;
import com.jurai.ui.controls.SidebarNavItem;
import com.jurai.ui.util.SpacerFactory;
import com.jurai.util.FileUtils;
import com.jurai.util.UILogger;
import javafx.css.PseudoClass;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import lombok.Getter;

import java.io.IOException;
import java.util.List;

public class SidebarNav extends AbstractMenu<VBox> {
    private VBox content;
    @Getter
    private SidebarNavItem dashboard, quickQuery, documents, account, logout;
    private SVGPath dashboardIcon, quickQueryIcon, documentsIcon, accountIcon, logoutIcon;
    @Getter
    private ArrowToggleButton sidebarToggleButton;

    public SidebarNav() {
        super();
    }

    @Override
    protected void initControls() {
        initIcons();
        content = new VBox();
        content.getStyleClass().add("sidebar-nav");

        dashboard = new SidebarNavItem(dashboardIcon, "Dashboard");
        dashboard.dotWidthProperty().set(4);

        quickQuery = new SidebarNavItem(quickQueryIcon, "Consulta Rápida");
        quickQuery.dotWidthProperty().set(4);

        documents = new SidebarNavItem(documentsIcon, "Documentos");
        documents.dotWidthProperty().set(4);

        account = new SidebarNavItem(accountIcon, "Sua Conta");
        account.dotWidthProperty().set(4);

        sidebarToggleButton = new ArrowToggleButton();
        sidebarToggleButton.setActive(AppState.get().isSidebarExtended());

        ApplicationData.defaultIconSizeProperty().bind(account.getIconContainer().widthProperty());

        logout = new SidebarNavItem(logoutIcon, "Sair");
        logout.setDotVisible(false);
        logout.setIconColor(Color.web("#c05050"));
    }

    @Override
    protected void layControls() {
        content.getChildren().addAll(
                dashboard,
                quickQuery,
                documents,
                account,
                SpacerFactory.vSpacer(Priority.ALWAYS),
                new HGroup().withChildren(
                        SpacerFactory.hSpacer(Priority.ALWAYS),
                        sidebarToggleButton
                ).withStyle("-fx-padding: 0; -fx-gap: 0;"),
                SpacerFactory.vSpacer(Priority.ALWAYS),
                logout);
        dashboard.setActive(true);
    }

    private void initIcons() {
        try {
            dashboardIcon = new SVGPath();
            dashboardIcon.setContent(FileUtils.getResourceContent("/paths/dashboard.path"));

            quickQueryIcon = new SVGPath();
            quickQueryIcon.setContent(FileUtils.getResourceContent("/paths/quickquery.path"));

            documentsIcon = new SVGPath();
            documentsIcon.setContent(FileUtils.getResourceContent("/paths/docs.path"));

            accountIcon = new SVGPath();
            accountIcon.setContent(FileUtils.getResourceContent("/paths/account.path"));

            logoutIcon = new SVGPath();
            logoutIcon.setContent(FileUtils.getResourceContent("/paths/logout.path"));
        } catch(IOException e) {
            e.printStackTrace();
            UILogger.logError("unable to load sidebar icon paths");
            UILogger.logWarning("Proceding without sidebar icons");
        }
    }

    public void setIconsOnly(boolean iconsOnly) {
        dashboard.setIconsOnly(iconsOnly);
        quickQuery.setIconsOnly(iconsOnly);
        documents.setIconsOnly(iconsOnly);
        account.setIconsOnly(iconsOnly);
        logout.setIconsOnly(iconsOnly);

        if(iconsOnly) {
            content.pseudoClassStateChanged(PseudoClass.getPseudoClass("iconsOnly"), true);
        } else {
            content.pseudoClassStateChanged(PseudoClass.getPseudoClass("iconsOnly"), false);
        }
    }

    public List<SidebarNavItem> getItems() {
        return List.of(dashboard, account, quickQuery, documents);
    }

    @Override
    public VBox getContent() {
        return content;
    }
}
