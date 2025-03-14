module es.ies.puerto {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;

    opens es.ies.puerto.controller to javafx.fxml;
    exports es.ies.puerto;
}
