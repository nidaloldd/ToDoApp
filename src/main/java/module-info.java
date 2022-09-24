module com.example.todoapplication {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.todoapplication to javafx.fxml;
    opens Model to javafx.fxml;
    exports com.example.todoapplication;
    exports Model;
}