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
import javafx.scene.Parent;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import visualigue.VisuaLigue;
import visualigue.domain.events.FramesListener;

/**
 * FXML Controller class
 *
 * @author Samuel
 */
public class MainToolbarController implements Initializable, FramesListener {

    @FXML
    private ToggleButton cursorButton;
    @FXML
    private ToggleButton addPlayerButton;

    @Override
    public void init(Parent parent) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateFrames() {
        update();
    }

    public enum EditMode {
        CURSOR,
        ADD_PLAYER,
        ADD_ACCESSORY,
        ADD_OBSTACLE;
    }

    private EditMode mode;

    private MainWindowController parentController;

    @FXML
    private ToolBar toolbar;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mode = EditMode.CURSOR;
        ((ToggleButton) toolbar.getItems().get(0)).setSelected(true);
    }

    public void init(MainWindowController paretnController) {
        this.parentController = paretnController;
    }
    
    public void update(){
        addPlayerButton.setDisable(VisuaLigue.domain.getTotalFrame() != 1);
    }

    public void selectCursorMode() {
        untoggleOthers(cursorButton);
        mode = EditMode.CURSOR;
    }

    @FXML
    private void cursorMode(ActionEvent event) {
        untoggleOthers(event.getSource());
        mode = EditMode.CURSOR;
        parentController.showDefaultToolbar();
    }

    @FXML
    private void addPlayerMode(ActionEvent event) {
        untoggleOthers(event.getSource());
        if (mode != EditMode.ADD_PLAYER) {
            mode = EditMode.ADD_PLAYER;
            parentController.showTeamList();
        }
    }

    @FXML
    private void AddAccessoryMode(ActionEvent event) {
        untoggleOthers(event.getSource());
        if (mode != EditMode.ADD_ACCESSORY) {
            mode = EditMode.ADD_ACCESSORY;
            parentController.showDefaultToolbar();
        }
    }

    @FXML
    private void addObstacleMode(ActionEvent event) {
        untoggleOthers(event.getSource());
        if (mode != EditMode.ADD_OBSTACLE) {
            mode = EditMode.ADD_OBSTACLE;
            parentController.showAddObstacleList();
        }
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
