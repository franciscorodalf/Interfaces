package es.ies.puerto.controller;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.control.ProgressIndicator;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Pattern;

import es.ies.puerto.controller.abstractas.AbstractController;
import javafx.util.Duration;

/**
 * Controlador de la pestaña de recuperar-contrasenia.fxml
 * 
 * @author franciscorodalf
 * @version 1.0.0
 */
public class RecuperarContraseniaController extends AbstractController {

    @FXML
    private TextField textoCorreo;
    @FXML
    private Text textoErrorContraseña;
    @FXML
    private Text tickConfirmacion;
    @FXML
    private ProgressIndicator loadingIndicator;
    @FXML
    private Button buttonRecuperar;
    @FXML
    private Label textoRecuperarContrasenia;
    @FXML
    Text textoIngresarGmail;
    @FXML
    private Button buttonVolver;

    /**
     * Patrón para verificar si el formato del correo que ha insertado el usuario es
     * válido.
     */
    private static final Pattern GMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@gmail\\.com$");

    /**
     * Método que aplica la traduccion de los textos de la pantalla de Recuperar
     * Contraseña.
     */
    public void postInitialize() {
        Properties properties = getPropertiesIdioma();
        if (properties != null) {
            textoRecuperarContrasenia
                    .setText(properties.getProperty("textoRecuperarContrasenia.text", "Recuperar Contraseña"));
            textoIngresarGmail.setText(properties.getProperty("textoIngresarGmail.text", "Ingrese su correo:"));
            buttonVolver.setText(properties.getProperty("buttonVolver", "Volver"));
            buttonRecuperar.setText(properties.getProperty("buttonRecuperar", "Enviar"));
        }
    }

    /**
     * Método que oculta los indicadores y textos
     */
    @FXML
    public void textoInvicible() {
        textoErrorContraseña.setVisible(false);
        tickConfirmacion.setVisible(false);
        loadingIndicator.setVisible(false);
    }

    /**
     * Método que se ejecuta cuando el usuario presiona el botón de recuperar.
     * Verifica si el correo esta vacio y si el patron de gmail coincide con el
     * correo que ha insertado el usuario.
     * Si todo esta correcto, envia un mensaje a la interfaz confirmando que el
     * proceso se ha hecho correctamente y
     * redirige a la pantalla de Login tras 4 segundos.
     */
    @FXML
    private void clickButtonRecuperar() {
        String correo = textoCorreo.getText().trim();
        
        textoErrorContraseña.setVisible(false);
        tickConfirmacion.setVisible(false);
        loadingIndicator.setVisible(false);
        
        if (getPropertiesIdioma() == null) {
            setPropertiesIdioma(loadIdioma("idioma", "es")); 
        }
        Properties properties = getPropertiesIdioma();

        if (correo.isEmpty()) {
            textoErrorContraseña.setText(
                    properties.getProperty("recuperar.mensaje.vacio", "⚠️ El campo de correo no puede estar vacío."));
            textoErrorContraseña.setStyle("-fx-fill: red;");
            textoErrorContraseña.setVisible(true);
            return;
        }

        if (!GMAIL_PATTERN.matcher(correo).matches()) {
            textoErrorContraseña.setText(
                    properties.getProperty("recuperar.mensaje.invalido", "⚠️ Introduzca un correo Gmail válido."));
            textoErrorContraseña.setStyle("-fx-fill: red;");
            textoErrorContraseña.setVisible(true);
            return;
        }

        tickConfirmacion
                .setText(properties.getProperty("recuperar.mensaje.enviado", "✔️ Correo enviado correctamente."));
        tickConfirmacion.setStyle("-fx-fill: green;");
        tickConfirmacion.setVisible(true);

        loadingIndicator.setVisible(true);

        PauseTransition delay = new PauseTransition(Duration.seconds(4));
        delay.setOnFinished(event -> redirigirARecuperacion());
        delay.play();
    }

    /**
     * Cambia a la pantalla para introducir nueva contraseña.
     * También pasa el idioma seleccionado.
     */
    private void redirigirARecuperacion() {
        try {
            System.out.println("Redirigiendo a la pantalla de recuperación de cuenta");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/es/ies/puerto/recuperar-cuenta.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
    
            RecuperarCuentaController controller = fxmlLoader.getController(); 
            controller.setPropertiesIdioma(getPropertiesIdioma());
            controller.postInitialize(); 
    
            Stage stage = (Stage) textoCorreo.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Restablecer Contraseña");
            stage.show();
        } catch (IOException e) {
            System.out.println("Error al redirigir: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
   /**
     * Metodo que se ejecuta cuando el usuario interactua con el botón de volver
     * Abre la pestaña de login, trasladando tambien la informacion del idioma seleccionado por el usuario.
     */
    @FXML
    private void clickButtonVolver(ActionEvent event) {
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
            System.out.println("Error al volver a la pantalla de login: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
