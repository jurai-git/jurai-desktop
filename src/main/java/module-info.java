module jurai {
    requires transitive javafx.controls;
    requires transitive javafx.graphics;
    requires java.base;
    requires transitive org.controlsfx.controls;
    requires transitive java.naming;
    requires transitive java.desktop;
    requires transitive java.logging;
    requires com.google.gson;


    exports com.jurai to javafx.graphics;
    exports com.jurai.ui.util to javafx.controls;
}
