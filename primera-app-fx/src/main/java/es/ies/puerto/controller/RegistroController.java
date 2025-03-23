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

/**
 * Controlador de la pestaña registro que verifica el registro del usuario.
 * @author franciscorodalf
 * @version 1.0.0
 */
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

    /**
     * 
     * Método que aplica la traduccion de los textos de la pantalla de Registro
     */
    public void postInitialize() {
        Properties properties = getPropertiesIdioma();
        textMensaje.setVisible(false);
        if (properties != null) {
            textUsuario.setText(properties.getProperty("textUsuario", "Usuario"));
            textContrasenia.setText(properties.getProperty("textContrasenia", "Contraseña"));
            textoRepetirContraseniaRegister
                    .setText(properties.getProperty("textoRepetirContraseniaRegister", "RepetirContraseña"));
            buttonVolverLogin.setText(properties.getProperty("buttonVolverLogin.text", "Volver"));
            textoGmailRegister.setText(properties.getProperty("textoGmailRegister", "Gmail"));
            textoRepetirGmailRegister.setText(properties.getProperty("textoRepetirGmailRegister", "Repetir Gmail"));
            buttonAceptar.setText(properties.getProperty("buttonAceptar.text", "Aceptar"));
        }
    }

    /**
     * Método que se ejecuta cuando el usuario interactua con el boton de Registrar.
     * Verifica la informacion puesta por el usuario y la registra en el archivo JSON si es válido
     */
    @FXML
    protected void onClickRegistrar() {
        String usuario = textoUsuario.getText().trim();
        String email = textoGmail.getText().trim();
        String contraseña = textoContrasenia.getText().trim();
        String repetirContraseña = textoRepetirContrasenia.getText().trim();
        Properties properties = getPropertiesIdioma();

        if (usuario.isEmpty() || email.isEmpty() || contraseña.isEmpty() || repetirContraseña.isEmpty()) {
            textMensaje.setVisible(true);
            mostrarMensaje(properties.getProperty("registro.mensaje.campos", "⚠️ Todos los campos son obligatorios."),
                    "red");
            return;
        }
        if (!contraseña.equals(repetirContraseña)) {
            textMensaje.setVisible(true);
            mostrarMensaje(properties.getProperty("registro.mensaje.noCoinciden", "⚠️ Las contraseñas no coinciden."),
                    "red");
            return;
        }
        Usuario nuevoUsuario = new Usuario(usuario, email, contraseña);
        boolean registrado = GestorUsuarios.agregarUsuario(nuevoUsuario);

        if (registrado) {
            textMensaje.setVisible(true);
            mostrarMensaje(properties.getProperty("registro.mensaje.exito", "✅ Registro exitoso."), "green");
        } else {
            mostrarMensaje(properties.getProperty("registro.mensaje.repetido", "⚠️ Usuario ya registrado."), "red");
        }
    }
    /**
     * Método que muestra el mensaje en la interfaz del color especificado.
     * @param mensaje que se quiere mostrar en la interfaz
     * @param color del mensaje
     */
    private void mostrarMensaje(String mensaje, String color) {
        textMensaje.setText(mensaje);
        textMensaje.setStyle("-fx-fill: " + color + ";");
        textMensaje.setVisible(true);
    }

      /**
     * Evento que se ejecuta al hacer clic en el botón "Volver".
     * Carga la pantalla de login conservando el idioma actual.
     */
    @FXML
    private void onClickVolverLogin(ActionEvent event) {
        try {
            System.out.println("Volviendo a la pantalla de login...");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/es/ies/puerto/login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            LoginController loginController = fxmlLoader.getController();
            loginController.setPropertiesIdioma(getPropertiesIdioma());
            loginController.postInitialize();
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
