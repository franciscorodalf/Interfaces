package es.ies.puerto.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import es.ies.puerto.PrincipalApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    private TextField textoRepetirGmail;
    @FXML
    private PasswordField textoContrasenia;
    @FXML
    private PasswordField textoRepetirContrasenia;
    @FXML
    private Text textMensaje;
    @FXML
    private Button buttonRegistrar;
    @FXML
    private Button buttonVolverLogin;

    @FXML
    public void initialize() {
        textMensaje.setText("");
        textMensaje.setVisible(true);
    }

    @FXML
    protected void onClickRegistrar() {
        System.out.println("Botón 'Registrar' presionado");

        textMensaje.setVisible(true);

        String usuario = textoUsuario.getText().trim();
        String gmail = textoGmail.getText().trim();
        String repetirGmail = textoRepetirGmail.getText().trim();
        String contrasenia = textoContrasenia.getText().trim();
        String repetirContrasenia = textoRepetirContrasenia.getText().trim();

        List<String> errores = new ArrayList<>();

        if (usuario.isEmpty() || gmail.isEmpty() || repetirGmail.isEmpty() || contrasenia.isEmpty()
                || repetirContrasenia.isEmpty()) {
            errores.add("⚠️ Todos los campos son obligatorios.");
        }
        if (!esGmailValido(gmail)) {
            errores.add("⚠️ Ingrese un Gmail válido.");
        }
        if (!gmail.equals(repetirGmail)) {
            errores.add("⚠️ Los correos no coinciden.");
        }
        if (usuario.equals(contrasenia)) {
            errores.add("🚫 El usuario no puede ser igual a la contraseña.");
        }
        if (!contrasenia.equals(repetirContrasenia)) {
            errores.add("⚠️ Las contraseñas no coinciden.");
        }

        if (!errores.isEmpty()) {
            textMensaje.setText(String.join("\n", errores));
            textMensaje.setStyle("-fx-text-fill: red;");
            System.out.println("Errores detectados:\n" + String.join("\n", errores));
            return;
        }

        textMensaje.setText("✅ ¡Registro exitoso!");
        textMensaje.setStyle("-fx-text-fill: green;");
        System.out.println("Registro completado correctamente.");
    }

    private static final Pattern GMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@gmail\\.com$");

    private boolean esGmailValido(String email) {
        return GMAIL_PATTERN.matcher(email).matches();
    }

    @FXML
    protected void onClickVolverLogin() {
        try {
            Stage stage = (Stage) buttonVolverLogin.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(PrincipalApplication.class.getResource("login.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 820, 640);
            stage.setTitle("Pantalla Login");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
