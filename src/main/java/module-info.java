module com.example.tp1javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;

    exports com.example.tp1javafx to javafx.graphics, javafx.fxml;
    exports com.example.tp1javafx.Controller to javafx.fxml;
    exports com.example.tp1javafx.Model to javafx.fxml;
    exports com.example.tp1javafx.DAO to javafx.fxml;
    exports com.example.tp1javafx.utilitaires to javafx.fxml;

    opens com.example.tp1javafx to javafx.fxml, javafx.graphics;
    opens com.example.tp1javafx.Controller to javafx.fxml;
    opens com.example.tp1javafx.Model to javafx.fxml;
    opens com.example.tp1javafx.DAO to javafx.fxml;
    opens com.example.tp1javafx.utilitaires to javafx.fxml;
}