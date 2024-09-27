module jurai {
    requires transitive javafx.controls;
    requires transitive javafx.graphics;
    requires java.base;
    requires transitive java.naming;
    requires transitive java.desktop;
    requires transitive java.logging;
    requires com.google.gson;
    requires java.sql;
    requires jdk.compiler;
    requires java.management;
    requires org.controlsfx.controls;
    requires jpro.webapi;
    requires net.harawata.appdirs;

    opens com.jurai.data.model to com.google.gson;
    exports com.jurai to javafx.graphics;
    exports com.jurai.ui.util to javafx.controls, com.google.gson;
}
