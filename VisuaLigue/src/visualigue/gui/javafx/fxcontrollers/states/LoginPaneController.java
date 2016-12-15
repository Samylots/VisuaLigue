/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.gui.javafx.fxcontrollers.states;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import visualigue.VisuaLigue;

/**
 * FXML Controller class
 *
 * @author Samuel
 */
public class LoginPaneController implements Initializable {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;

    private boolean isValidLogin = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void clear() {
        usernameField.clear();
        passwordField.clear();
        usernameField.requestFocus();
    }

    @FXML
    private void login(ActionEvent event) {
        int loginResult = VisuaLigue.domain.login(usernameField.getText(), passwordField.getText());
        if (loginResult == 1 || loginResult == 2) {
            isValidLogin = true;
        }
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.close();
    }

    public boolean isValidLogin() {
        return isValidLogin;
    }

}
