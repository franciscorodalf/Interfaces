package es.ies.puerto.controller;

import es.ies.puerto.model.Usuario;
import es.ies.puerto.controller.abstractas.AbstractController;
import es.ies.puerto.model.GestorUsuarios;

import java.io.IOException;
import java.util.Properties;

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

public class RegistroController extends AbstractController {
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
    private Text textUsuario;
    @FXML
    private Button buttonVolverLogin;
    @FXML
    private Text textContrasenia;
    @FXML
    private Text textoRepetirGmailRegister;
    @FXML
    private Text textoGmailRegister;
    @FXML
    private Button buttonAceptar;
    @FXML
    private Text textoRepetirContraseniaRegister;
    

    public void postInitialize() {
        Properties properties = getPropertiesIdioma();
        if (properties != null) {
            textUsuario.setText(properties.getProperty("textUsuario", "Usuario"));
            textContrasenia.setText(properties.getProperty("textContrasenia", "Contraseña"));
            textoRepetirContraseniaRegister.setText(properties.getProperty("textoRepetirContraseniaRegister", "RepetirContraseña"));
            buttonVolverLogin.setText(properties.getProperty("buttonVolverLogin.text", "Volver"));
            textoGmailRegister.setText(properties.getProperty("textoGmailRegister", "Gmail"));
            textoRepetirGmailRegister.setText(properties.getProperty("textoRepetirGmailRegister", "Repetir Gmail"));
            buttonAceptar.setText(properties.getProperty("buttonAceptar.text", "Aceptar"));
        } else {
            textUsuario.setText("Usuario");
            textContrasenia.setText("Contraseña");
        }
    }

    @FXML
protected void onClickRegistrar() {
    String usuario = textoUsuario.getText().trim();
    String email = textoGmail.getText().trim();
    String contraseña = textoContrasenia.getText().trim();
    String repetirContraseña = textoRepetirContrasenia.getText().trim();
    Properties properties = getPropertiesIdioma();

    if (usuario.isEmpty() || email.isEmpty() || contraseña.isEmpty() || repetirContraseña.isEmpty()) {
        mostrarMensaje(properties.getProperty("registro.mensaje.campos", "⚠️ Todos los campos son obligatorios."), "red");
        return;
    }
    if (!contraseña.equals(repetirContraseña)) {
        mostrarMensaje(properties.getProperty("registro.mensaje.noCoinciden", "⚠️ Las contraseñas no coinciden."), "red");
        return;
    }
    
    Usuario nuevoUsuario = new Usuario(usuario, email, contraseña);
    boolean registrado = GestorUsuarios.agregarUsuario(nuevoUsuario);
    
    if (registrado) {
        mostrarMensaje(properties.getProperty("registro.mensaje.exito", "✅ Registro exitoso."), "green");
    } else {
        mostrarMensaje(properties.getProperty("registro.mensaje.repetido", "⚠️ Usuario ya registrado."), "red");
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
            System.out.println("Volviendo a la pantalla de login...");
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
