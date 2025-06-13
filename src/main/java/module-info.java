module jurai {
    requires transitive java.naming;
    requires transitive java.logging;
    requires com.google.gson;
    requires java.sql;
    // requires jdk.compiler;
    requires java.management;
    requires org.controlsfx.controls;
    requires net.harawata.appdirs;
    requires java.net.http;
    requires static lombok;
    requires org.fxmisc.richtext;
    requires fluidfx;
    requires java.desktop;

    opens com.jurai.data.model to com.google.gson;
    exports com.jurai to javafx.graphics;
    exports com.jurai.ui.util to javafx.controls, com.google.gson;
}
