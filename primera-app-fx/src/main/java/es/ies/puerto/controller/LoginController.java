package es.ies.puerto.controller;

import es.ies.puerto.PrincipalApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController {

    private final String usuario = "pokemon";
    private final String password = "pokemon";

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
    protected void onLoginButtonClick() {
        boolean esInvalido = textFieldUsuario == null || textFieldUsuario.getText().isEmpty() ||
                textFieldPassword == null || textFieldPassword.getText().isEmpty() ||
                !textFieldUsuario.getText().equals(usuario) ||
                !textFieldPassword.getText().equals(password);

        if (esInvalido) {
            textFieldMensaje.setText("Credenciales inválidas");
            textFieldMensaje.setStyle("-fx-text-fill: red;"); // Texto en rojo
            aceptarButton.setStyle("-fx-background-color: red; -fx-text-fill: white;"); // Botón en rojo con texto
                                                                                        // blanco
            return;
        }

        textFieldMensaje.setText("Usuario validado correctamente");
        textFieldMensaje.setStyle("-fx-text-fill: green;"); // Texto en verde
        aceptarButton.setStyle("-fx-background-color: green; -fx-text-fill: white;"); // Botón en verde con texto blanco
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

}