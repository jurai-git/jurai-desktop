package io.jurai.ui.menus;

import io.jurai.ui.controls.SidebarNavItem;
import io.jurai.util.FileUtils;
import io.jurai.util.UILogger;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;

import java.io.IOException;

public class SidebarNav extends AbstractMenu {
    private VBox content;
    private SidebarNavItem dashboard, quickQuery, documents, account;
    private SVGPath dashboardIcon, quickQueryIcon, documentsIcon, accountIcon;

    public SidebarNav() {
        super();
    }

    @Override
    protected void initControls() {
        initIcons();
        content = new VBox();
        content.getStyleClass().add("sidebar-nav");

        dashboard = new SidebarNavItem(dashboardIcon, "Dashboard");
        dashboard.dotWidthProperty().bind(content.widthProperty().multiply(0.025));

        quickQuery = new SidebarNavItem(quickQueryIcon, "Consulta Rápida");
        quickQuery.dotWidthProperty().bind(content.widthProperty().multiply(0.025));

        documents = new SidebarNavItem(documentsIcon, "Documentos");
        documents.dotWidthProperty().bind(content.widthProperty().multiply(0.025));

        account = new SidebarNavItem(accountIcon, "Sua Conta");
        account.dotWidthProperty().bind(content.widthProperty().multiply(0.025));
    }

    @Override
    protected void layControls() {
        content.getChildren().addAll(dashboard, quickQuery, documents, account);
        documents.setActive(true);
    }

    private void initIcons() {
        try {
            dashboardIcon = new SVGPath();
            dashboardIcon.setContent(FileUtils.getFileContent("/paths/dashboard.path"));

            quickQueryIcon = new SVGPath();
            quickQueryIcon.setContent(FileUtils.getFileContent("/paths/quickquery.path"));

            documentsIcon = new SVGPath();
            documentsIcon.setContent(FileUtils.getFileContent("/paths/docs.path"));

            accountIcon = new SVGPath();
            accountIcon.setContent(FileUtils.getFileContent("/paths/account.path"));
        } catch(IOException e) {
            e.printStackTrace();
            UILogger.logError("unable to load sidebar icon paths");
            UILogger.logWarning("Proceding without sidebar icons");
        }
    }

    public SidebarNavItem getDashboard() {
        return dashboard;
    }

    public SidebarNavItem getAccount() {
        return account;
    }

    public SidebarNavItem getQuickQuery() {
        return quickQuery;
    }

    public SidebarNavItem getDocuments() {
        return documents;
    }

    @Override
    public Node getContent() {
        return content;
    }
}
