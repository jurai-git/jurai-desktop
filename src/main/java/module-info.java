module jurai {
    requires javafx.controls;
    requires javafx.graphics;
    requires java.base;
    requires org.controlsfx.controls;
    requires java.naming;
    requires java.desktop;

    opens io.jurai to javafx.graphics, javafx.controls, java.base, org.controlsfx.controls;
}
