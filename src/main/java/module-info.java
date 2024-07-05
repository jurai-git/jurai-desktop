module jurai {
    requires javafx.controls;
    requires javafx.graphics;
    requires java.base;

    opens io.jurai to javafx.graphics;
}
