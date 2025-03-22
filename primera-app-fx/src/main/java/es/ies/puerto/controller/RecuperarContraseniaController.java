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
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import es.ies.puerto.controller.abstractas.AbstractController;
import javafx.util.Duration;

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

    private static final Pattern GMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@gmail\\.com$");

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

    @FXML
    public void textoInvicible() {
        textoErrorContraseña.setVisible(false);
        tickConfirmacion.setVisible(false);
        loadingIndicator.setVisible(false);
    }

    @FXML
    private void clickButtonRecuperar() {
        String correo = textoCorreo.getText().trim();
        Properties properties = getPropertiesIdioma();

        textoErrorContraseña.setVisible(false);
        tickConfirmacion.setVisible(false);
        loadingIndicator.setVisible(false);

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
    private void redirigirARecuperacion() {
        try {
            System.out.println("Redirigiendo a la pantalla de recuperación de cuenta...");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/es/ies/puerto/recuperarCuenta.fxml"));
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
