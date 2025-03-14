package es.ies.puerto.controller;

import es.ies.puerto.model.Usuario;
import es.ies.puerto.model.GestorUsuarios;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RegistroController {
    @FXML
    private TextField textoUsuario;
    @FXML
    private TextField textoGmail;
    @FXML
    private PasswordField textoContrasenia;
    @FXML
    private PasswordField textoRepetirContrasenia;
    @FXML
    private Text textMensaje;

    @FXML
    protected void onClickRegistrar() {
        String usuario = textoUsuario.getText().trim();
        String email = textoGmail.getText().trim();
        String contraseña = textoContrasenia.getText().trim();
        String repetirContraseña = textoRepetirContrasenia.getText().trim();

        if (usuario.isEmpty() || email.isEmpty() || contraseña.isEmpty() || repetirContraseña.isEmpty()) {
            mostrarMensaje("⚠️ Todos los campos son obligatorios.", "red");
            return;
        }
        if (!contraseña.equals(repetirContraseña)) {
            mostrarMensaje("⚠️ Las contraseñas no coinciden.", "red");
            return;
        }

        Usuario nuevoUsuario = new Usuario(usuario, email, contraseña);
        boolean registrado = GestorUsuarios.agregarUsuario(nuevoUsuario);

        if (registrado) {
            mostrarMensaje("✅ Registro exitoso.", "green");
        } else {
            mostrarMensaje("⚠️ Usuario ya registrado.", "red");
        }
    }

    private void mostrarMensaje(String mensaje, String color) {
        textMensaje.setText(mensaje);
        textMensaje.setStyle("-fx-fill: " + color + ";");
        textMensaje.setVisible(true);
    }

    @FXML
    private void onClickVolverLogin(ActionEvent event) {
        try {
            System.out.println("🔄 Volviendo a la pantalla de login...");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/es/ies/puerto/login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            System.err.println("❌ Error al volver a la pantalla de login: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
