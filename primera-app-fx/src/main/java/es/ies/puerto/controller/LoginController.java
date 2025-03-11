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

        if (textFieldUsuario == null || textFieldUsuario.getText().isEmpty() ||
                textFieldPassword == null || textFieldPassword.getText().isEmpty()) {
            textFieldMensaje.setText("Credenciales null o vacias");
            return;
        }

        if (!textFieldUsuario.getText().equals(usuario) || !textFieldPassword.getText().equals(password)) {
            textFieldMensaje.setText("Credenciales invalidas");
            return;
        }

        textFieldMensaje.setText("Usuario validado correctamente");
    }

    @FXML
    public void buttonAceptarClick(MouseEvent event) {
        aceptarButton.setStyle("-fx-background-color: #E74C3C;"); 
    }
    @FXML
    public void buttonOpenRegistrarClick(MouseEvent event) {
        aceptarButton.setStyle("-fx-background-color: #E74C3C;"); 
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