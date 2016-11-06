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

    enum EditMode {

        CURSOR,
        ADD_PLAYER,
        ADD_ACCESSORY,
        ADD_OBSTACLE;
    }

    private EditMode mode;

    @FXML
    private ToolBar toolbar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mode = EditMode.CURSOR;
        // TODO
    }

    @FXML
    private void cursorMode(ActionEvent event) {
        untoggleOthers(event.getSource());
        mode = EditMode.CURSOR;
    }

    @FXML
    private void addPlayerMode(ActionEvent event) {
        untoggleOthers(event.getSource());
        mode = EditMode.ADD_PLAYER;
    }

    @FXML
    private void AddAccessoryMode(ActionEvent event) {
        untoggleOthers(event.getSource());
        mode = EditMode.ADD_ACCESSORY;
    }

    @FXML
    private void addObstacleMode(ActionEvent event) {
        untoggleOthers(event.getSource());
        mode = EditMode.ADD_OBSTACLE;
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

    public EditMode getEditMode() {
        return mode;
    }

}
