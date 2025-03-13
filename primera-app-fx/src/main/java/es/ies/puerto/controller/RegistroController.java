package es.ies.puerto.controller;

import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

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

    // Expresión regular para validar correos de Gmail
    private static final Pattern GMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@gmail\\.com$");

    @FXML
    public void initialize() {
        // Asegurar que textMensaje está visible y limpio al inicio
        textMensaje.setText("");
        textMensaje.setVisible(true);
    }

    @FXML
    protected void onClickRegistrar() {
        System.out.println("Botón 'Registrar' presionado");

        if (textMensaje == null) {
            System.out.println("ERROR: textMensaje no está vinculado en Scene Builder.");
            return;
        }

        textMensaje.setVisible(true);
        boolean error = false;
        StringBuilder mensajeError = new StringBuilder(); // Acumulador de errores

        // Validar que los campos no estén vacíos
        if (textoUsuario.getText().trim().isEmpty() ||
                textoGmail.getText().trim().isEmpty() ||
                textoRepetirGmail.getText().trim().isEmpty() ||
                textoContrasenia.getText().trim().isEmpty() ||
                textoRepetirContrasenia.getText().trim().isEmpty()) {

            mensajeError.append("⚠️ Todos los campos son obligatorios. \n");
            error = true;
        }

        // Validar Gmail
        if (!textoGmail.getText().matches("^[a-zA-Z0-9._%+-]+@gmail\\.com$")) {
            mensajeError.append("⚠️ Ingrese un Gmail válido. \n");
            error = true;
        }

        // Validar que los correos coincidan
        if (!textoGmail.getText().equals(textoRepetirGmail.getText())) {
            mensajeError.append("⚠️ Los correos no coinciden. \n");
            error = true;
        }
        
        if (textoUsuario.getText().equals(textoContrasenia.getText())) {
            mensajeError.append("🚫 El usuario no puede ser igual a la contraseña. \n");
            error = true;
        }
        // Validar que las contraseñas coincidan
        if (!textoContrasenia.getText().equals(textoRepetirContrasenia.getText())) {
            mensajeError.append("⚠️ Las contraseñas no coinciden. \n");
            error = true;
        }

        // Si hubo errores, mostrar todos los mensajes y salir
        if (error) {
            textMensaje.setText(mensajeError.toString());
            textMensaje.setStyle("-fx-text-fill: red;");
            System.out.println("Errores detectados: \n" + mensajeError);
            return;
        }

        // Si todo está correcto
        textMensaje.setText("✅ ¡Registro exitoso!");
        textMensaje.setStyle("-fx-text-fill: green;");
        System.out.println("Registro completado correctamente.");
    }

}
