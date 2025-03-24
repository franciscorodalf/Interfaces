package es.ies.puerto.controller;

import es.ies.puerto.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controlador para la vista que muestra la información del usuario logueado.
 */
public class mostrarinformacionController {

    @FXML
    private TextField textFieldUsuario;
    @FXML
    private TextField textFieldGmail;
    @FXML
    private TextField textFieldContrasenia;

    @FXML
    private Button buttonVolverInfo;
    @FXML
    private Text textInformacionUsuario;
    @FXML
    private Text textUsuarioInformacion;
    @FXML
    private Text textInfoGmail;
    @FXML
    private Text textInfoContrasenia;

    /**
     * Método que recibe el objeto Usuario y lo muestra en los campos
     * correspondientes.
     */
    public void mostrarInformacionUsuario(Usuario usuario) {
        if (usuario != null) {
            textFieldUsuario.setText(usuario.getUsuario());
            textFieldGmail.setText(usuario.getEmail());
            textFieldContrasenia.setText(usuario.getContraseña());
            
            textFieldUsuario.setEditable(false);
            textFieldGmail.setEditable(false);
            textFieldContrasenia.setEditable(false);
        }
    }

    /**
     * Método para volver a la pantalla de login.
     */
    @FXML
    private void clickButtonVolver(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/es/ies/puerto/login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            System.out.println("Error al volver a login: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
