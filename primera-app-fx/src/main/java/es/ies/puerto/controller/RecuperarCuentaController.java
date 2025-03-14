package es.ies.puerto.controller;

import java.io.IOException;

import es.ies.puerto.model.GestorUsuarios;
import javafx.util.Duration;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RecuperarCuentaController {

    @FXML
    private PasswordField nuevaContrasenia;
    @FXML
    private PasswordField confirmarContrasenia;
    @FXML
    private Text mensajeCambio;
    @FXML
    private TextField campoUsuario;

    @FXML
    private void clickButtonCambiar() {
        String nueva = nuevaContrasenia.getText().trim();
        String confirmar = confirmarContrasenia.getText().trim();
        String usuario = campoUsuario.getText().trim(); 

        mensajeCambio.setVisible(false);

        if (usuario.isEmpty() || nueva.isEmpty() || confirmar.isEmpty()) {
            mensajeCambio.setText("⚠️ Rellene todos los campos.");
            mensajeCambio.setStyle("-fx-fill: red;");
            mensajeCambio.setVisible(true);
            return;
        }

        if (!nueva.equals(confirmar)) {
            mensajeCambio.setText("⚠️ Las contraseñas no coinciden.");
            mensajeCambio.setStyle("-fx-fill: red;");
            mensajeCambio.setVisible(true);
            return;
        }

        boolean cambioExitoso = GestorUsuarios.actualizarContrasenia(usuario, nueva);

        if (cambioExitoso) {
            mensajeCambio.setText("✅ Contraseña cambiada con éxito.");
            mensajeCambio.setStyle("-fx-fill: green;");
            mensajeCambio.setVisible(true);

            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(event -> volverALogin());
            delay.play();
        } else {
            mensajeCambio.setText("❌ Usuario no encontrado.");
            mensajeCambio.setStyle("-fx-fill: red;");
            mensajeCambio.setVisible(true);
        }
    }

    private void volverALogin() {
        try {
            System.out.println("Volviendo a la pantalla de login...");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/es/ies/puerto/login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            Stage stage = (Stage) mensajeCambio.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            System.out.println("Error al volver a la pantalla de login: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private Button buttonVolver;

    @FXML
    private void clickButtonVolver(ActionEvent event) {
        try {
            System.out.println("Volviendo a la pantalla de login...");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/es/ies/puerto/login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            System.out.println("Error al volver a la pantalla de login: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
