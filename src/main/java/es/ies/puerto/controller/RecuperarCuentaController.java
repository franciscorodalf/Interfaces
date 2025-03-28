package es.ies.puerto.controller;

import java.io.IOException;
import java.util.Properties;

import es.ies.puerto.controller.abstractas.AbstractController;
import es.ies.puerto.model.GestorUsuarios;
import javafx.util.Duration;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Controlador de la pestaña de recuperar-cuenta.fmxl
 * 
 * @author franciscorodalf
 * @version 1.0.0
 */
public class RecuperarCuentaController extends AbstractController {

    @FXML
    private PasswordField nuevaContrasenia;
    @FXML
    private PasswordField confirmarContrasenia;
    @FXML
    private Text mensajeCambio;
    @FXML
    private TextField campoUsuario;
    @FXML
    private Label textRestablecerContrasenia;
    @FXML
    private Text textUsuarioContrasenia;
    @FXML
    private Text textNuevaContrasenia;
    @FXML
    private Text textConfirmarContrasenia;
    @FXML
    private Button buttonCambiarContrasenia;
    @FXML
    private Button buttonVolver;

    /**
     * Método que aplica la traduccion de los textos de la pantalla de Recuperar
     * Cuenta
     */
    public void postInitialize() {
        Properties properties = getPropertiesIdioma();
        if (properties != null) {
            textRestablecerContrasenia
                    .setText(properties.getProperty("textRestablecerContrasenia", "Restablecer Contraseña"));
            textUsuarioContrasenia.setText(properties.getProperty("textUsuarioContrasenia", "Usuario"));
            textNuevaContrasenia.setText(properties.getProperty("textNuevaContrasenia", "Nueva Contraseña"));
            textConfirmarContrasenia
                    .setText(properties.getProperty("textConfirmarContrasenia", "Confirmar Contraseña"));
            buttonCambiarContrasenia.setText(properties.getProperty("buttonCambiarContrasenia", "Cambiar Contraseña"));
            buttonVolver.setText(properties.getProperty("buttonVolverCambiarContrasenia", "Volver"));
        }
    }

    /**
     * Método que se ejecuta cuando el usuario hace clic en "Cambiar Contraseña".
     * Comprube que la inforamcion introducida por el usuario sea valida
     * si todo está correcto, actualiza la contraseña.
     */
    @FXML
    private void clickButtonCambiar() {
        String nueva = nuevaContrasenia.getText().trim();
        String confirmar = confirmarContrasenia.getText().trim();
        String usuario = campoUsuario.getText().trim();

        mensajeCambio.setVisible(false);
        Properties properties = getPropertiesIdioma();

        if (usuario.isEmpty() || nueva.isEmpty() || confirmar.isEmpty()) {
            mensajeCambio.setText(properties.getProperty("cambiar.mensaje.vacio", "⚠️ Rellene todos los campos."));
            mensajeCambio.setStyle("-fx-fill: red;");
            mensajeCambio.setVisible(true);
            return;
        }

        if (!nueva.equals(confirmar)) {
            mensajeCambio
                    .setText(properties.getProperty("cambiar.mensaje.noCoincide", "⚠️ Las contraseñas no coinciden."));
            mensajeCambio.setStyle("-fx-fill: red;");
            mensajeCambio.setVisible(true);
            return;
        }

        boolean cambioExitoso = GestorUsuarios.actualizarContrasenia(usuario, nueva);

        if (cambioExitoso) {
            mensajeCambio.setText(properties.getProperty("cambiar.mensaje.exito", "✅ Contraseña cambiada con éxito."));
            mensajeCambio.setStyle("-fx-fill: green;");
            mensajeCambio.setVisible(true);

            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(event -> volverALogin());
            delay.play();
        } else {
            mensajeCambio
                    .setText(properties.getProperty("cambiar.mensaje.usuarioNoExiste", "❌ Usuario no encontrado."));
            mensajeCambio.setStyle("-fx-fill: red;");
            mensajeCambio.setVisible(true);
        }
    }

    /**
     * Metodo que se ejecuta cuando el usuario interactua con el botón de cambiar contraseña.
     * Abre la pestaña de login, trasladando tambien la informacion del idioma
     * seleccionado por el usuario.
     */
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
 /**
     * Metodo que se ejecuta cuando el usuario interactua con el botón de volver.
     * Abre la pestaña de login, trasladando tambien la informacion del idioma
     * seleccionado por el usuario.
     */
    @FXML
    private void clickButtonVolver(ActionEvent event) {
        try {
            System.out.println("Volviendo a la pantalla de login...");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/es/ies/puerto/login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            LoginController loginController = fxmlLoader.getController();
            loginController.setPropertiesIdioma(getPropertiesIdioma());
            loginController.postInitialize();
            stage.setScene(scene);
            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            System.out.println("Error al volver a la pantalla de login: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
