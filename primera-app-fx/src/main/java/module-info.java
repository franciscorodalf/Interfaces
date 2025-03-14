module es.ies.puerto {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;

    opens es.ies.puerto.controller to javafx.fxml;
    opens es.ies.puerto.model to com.fasterxml.jackson.databind;  

    exports es.ies.puerto;
}
