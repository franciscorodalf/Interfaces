package es.ies.puerto.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import es.ies.puerto.PrincipalApplication;
import es.ies.puerto.controller.abstractas.AbstractController;
import es.ies.puerto.model.GestorUsuarios;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Controlador de la pestaña Login.
 * 
 * @author franciscorodalf
 * @version 1.0.0
 */
public class LoginController extends AbstractController {

    @FXML
    private TextField textFieldUsuario;
    @FXML
    private PasswordField textFieldPassword;
    @FXML
    private Text textFieldMensaje;
    @FXML
    private Button aceptarButton;
    @FXML
    private Button openRegistrarButton;
    @FXML
    private Button buttonRecuperarContrasenia;
    @FXML
    private Text textUsuario;
    @FXML
    private Text textContrasenia;
    @FXML
    private ComboBox comboIdioma;

    /**
     * Metodo que se ejecuta cuando el usuario interactua con el botón de login.
     * Valida los datos introducidos y muestra mensaje segun el resultado.
     */
    @FXML
    protected void onLoginButtonClick() {
        String usuario = textFieldUsuario.getText().trim();
        String password = textFieldPassword.getText().trim();
        Properties properties = getPropertiesIdioma();
        if (properties == null) {
            properties = loadIdioma("idioma", "es");
            setPropertiesIdioma(properties);
        }

        if (usuario.isEmpty() || password.isEmpty()) {
            textFieldMensaje
                    .setText(properties.getProperty("login.mensaje.campos", "⚠️ Todos los campos son obligatorios."));
            textFieldMensaje.setStyle("-fx-text-fill: red;");
            textFieldMensaje.setVisible(true);
            return;
        }

        if (GestorUsuarios.validarUsuario(usuario, password)) {
            String mensaje = properties.getProperty("login.mensaje.bienvenido", "✅ Bienvenido, {0}")
                    .replace("{0}", usuario);
            textFieldMensaje.setText(mensaje);
            textFieldMensaje.setStyle("-fx-text-fill: green;");
        } else {
            textFieldMensaje
                    .setText(properties.getProperty("login.mensaje.incorrecto", "❌ Usuario o contraseña incorrectos."));
            textFieldMensaje.setStyle("-fx-text-fill: red;");
        }

        textFieldMensaje.setVisible(true);
    }

    /**
     * Metodo que se ejecuta cuando el usuario interactua con el botón de registrar.
     * Abre la pestaña de registrar, trasladando tambien la informacion del idioma
     * seleccionado por el usuario.
     */
    @FXML
    protected void openRegistrarClick() {
        try {

            FXMLLoader loader = new FXMLLoader(PrincipalApplication.class.getResource("registro.fxml"));
            Parent root = loader.load();

            RegistroController registroController = loader.getController();
            registroController.setPropertiesIdioma(this.getPropertiesIdioma());

            registroController.postInitialize();

            Stage stage = (Stage) textFieldUsuario.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Metodo que se ejecuta cuando el usuario interactua con el botón de recuperar
     * contraseña.
     * Abre la pestaña de recuperar contraseña, trasladando tambien la informacion
     * del idioma seleccionado por el usuario.
     */
    @FXML
    private void buttonRecuperarContraseniaClick(ActionEvent event) {
        try {
            System.out.println("Abriendo pantalla de recuperar contraseña...");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/es/ies/puerto/recuperar-contrasenia.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 500, 400);
            RecuperarContraseniaController controller = fxmlLoader.getController();
            controller.setPropertiesIdioma(this.getPropertiesIdioma());
            controller.postInitialize();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Recuperar Contraseña");
            stage.show();
        } catch (IOException e) {
            System.out.println("Error al cambiar a la pantalla de recuperación de contraseña: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Carga los idiomas disponibles en la pestaña de login.
     */
    @FXML
    public void initialize() {
        List<String> idiomas = new ArrayList<>();
        idiomas.add("es");
        idiomas.add("en");
        idiomas.add("fr");
        comboIdioma.getItems().addAll(idiomas);
    }

    /**
     * Aplica la traduccion de los textos de la pantalla de login.
     */
    public void postInitialize() {
        Properties properties = getPropertiesIdioma();
        if (properties == null)
            return;

        textUsuario.setText(properties.getProperty("textUsuario", "Usuario"));
        textContrasenia.setText(properties.getProperty("textContrasenia", "Contraseña"));
        openRegistrarButton.setText(properties.getProperty("openRegistrarButton.text", "Registrarte"));
        aceptarButton.setText(properties.getProperty("aceptarButton.text", "Aceptar"));
        buttonRecuperarContrasenia
                .setText(properties.getProperty("buttonRecuperarContrasenia.text", "¿Olvidaste tu contraseña?"));
    }

    /**
     * Método que cambia el idioma seleccionado por el usuario y actualiza los
     * textos.
     */
    @FXML
    protected void cambiarIdioma() {
        setPropertiesIdioma(loadIdioma("idioma", comboIdioma.getValue().toString()));
        postInitialize();
    }

}
