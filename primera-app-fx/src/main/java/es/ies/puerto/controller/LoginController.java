package es.ies.puerto.controller;

import java.io.IOException;

import es.ies.puerto.PrincipalApplication;
import es.ies.puerto.model.GestorUsuarios;
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

public class LoginController {

    @FXML private TextField textFieldUsuario;
    @FXML private PasswordField textFieldPassword;
    @FXML private Text textFieldMensaje;
    @FXML private Button aceptarButton;
    @FXML private Button openRegistrarButton;
    @FXML private Button buttonRecuperarContraseña;

    @FXML
    protected void onLoginButtonClick() {
        String usuario = textFieldUsuario.getText().trim();
        String password = textFieldPassword.getText().trim();

        // Verifica si los campos están vacíos
        if (usuario.isEmpty() || password.isEmpty()) {
            textFieldMensaje.setText("⚠️ Todos los campos son obligatorios.");
            textFieldMensaje.setStyle("-fx-text-fill: red;");
            textFieldMensaje.setVisible(true);
            return;
        }

        if (GestorUsuarios.validarUsuario(usuario, password)) {
            textFieldMensaje.setText("✅ Bienvenido, " + usuario + ".");
            textFieldMensaje.setStyle("-fx-text-fill: green;");
        } else {
            textFieldMensaje.setText("❌ Usuario o contraseña incorrectos.");
            textFieldMensaje.setStyle("-fx-text-fill: red;");
        }
        textFieldMensaje.setVisible(true);
    }

    @FXML
    protected void openRegistrarClick() {
        try {
            Stage stage = (Stage) openRegistrarButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(PrincipalApplication.class.getResource("registro.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 820, 640);
            stage.setTitle("Pantalla Registro");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void buttonRecuperarContraseñaClick(ActionEvent event) {
        try {
            System.out.println("Abriendo pantalla de recuperar contraseña...");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/es/ies/puerto/recuperarContrasenia.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 500, 400);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Recuperar Contraseña");
            stage.show();
        } catch (IOException e) {
            System.out.println("Error al cambiar a la pantalla de recuperación de contraseña: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
