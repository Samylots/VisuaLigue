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
import javafx.scene.control.TextField;
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
    private TextField passwordField;
    @FXML
    private Button loginButton;

    private boolean isValidLogin = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void login(ActionEvent event) {
        int loginResult = VisuaLigue.domain.login(usernameField.getText(), passwordField.getText());
        if (loginResult == 1) {
            isValidLogin = true;
        } else if (loginResult == 2) {
            isValidLogin = true;
        }
    }

    public boolean isValidLogin() {
        return isValidLogin;
    }

}
