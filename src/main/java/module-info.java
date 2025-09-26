module jurai {
    // Java base modules
    requires transitive java.naming;
    requires transitive java.logging;
    requires java.sql;
    requires jdk.compiler;
    requires java.management;
    requires java.net.http;
    requires java.desktop;

    // JavaFX modules (make sure all are present)
    requires javafx.controls;
    requires javafx.base;
    requires javafx.graphics;

    // Third-party dependencies
    requires com.google.gson;
    requires org.controlsfx.controls;
    requires net.harawata.appdirs;
    requires static lombok;
    requires org.fxmisc.richtext;
    requires fluidfx;

    // Open packages for reflection (Lombok, Gson, JavaFX)
    opens com.jurai to lombok;
    opens com.jurai.data to lombok;
    opens com.jurai.data.model to com.google.gson, lombok;
    opens com.jurai.ui to lombok;
    opens com.jurai.ui.menus to lombok;
    opens com.jurai.ui.controller to lombok;
    opens com.jurai.ui.modal to lombok;
    opens com.jurai.ui.modal.notif to lombok;
    opens com.jurai.ui.util to javafx.controls, com.google.gson, lombok;

    // Exports
    exports com.jurai to javafx.graphics;
    exports com.jurai.ui.util to javafx.controls, com.google.gson;
}