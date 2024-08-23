module jurai {
    requires transitive javafx.controls;
    requires transitive javafx.graphics;
    requires java.base;
    requires transitive org.controlsfx.controls;
    requires transitive java.naming;
    requires transitive java.desktop;
    requires transitive java.logging;
    requires com.google.gson;

    opens io.jurai to javafx.graphics, javafx.controls, java.base, org.controlsfx.controls;
}
