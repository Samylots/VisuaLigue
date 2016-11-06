/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.gui.javafx.fxcontrollers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;

/**
 * FXML Controller class
 *
 * @author Samuel
 */
public class MainToolbarController implements Initializable {

    @FXML
    private ToolBar toolbar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void cursorMode(ActionEvent event) {
        untoggleOthers(event.getSource());
    }

    @FXML
    private void addPlayerMode(ActionEvent event) {
        untoggleOthers(event.getSource());
    }

    @FXML
    private void AddAccessoryMode(ActionEvent event) {
        untoggleOthers(event.getSource());
    }

    @FXML
    private void addObstacleMode(ActionEvent event) {
        untoggleOthers(event.getSource());
    }

    private void untoggleOthers(Object source) {
        for (Node button : toolbar.getItems()) {
            if (source != button) {
                ToggleButton actualButton = (ToggleButton) button;
                actualButton.setSelected(false);
            }
        }
        ((ToggleButton) source).setSelected(true);
    }

}
