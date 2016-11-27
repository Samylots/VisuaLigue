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
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import visualigue.VisuaLigue;
import visualigue.inter.dto.PlayerDTO;

/**
 * FXML Controller class
 *
 * @author Samuel
 */
public class PlayerToolbarController implements Initializable {

    private PlayerDTO player;

    @FXML
    private ToolBar toolbar;
    @FXML
    private Button deleteButton;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void update(PlayerDTO player) {
        this.player = player;
        if (VisuaLigue.domain.getTotalFrame() == 1) {
            deleteButton.setVisible(true);
        } else {
            deleteButton.setVisible(false);
        }
    }

    @FXML
    private void unselect(ActionEvent event) {
        VisuaLigue.domain.unSelectCurrentEntity();
    }

    @FXML
    private void editRole(ActionEvent event) {
    }

    @FXML
    private void editName(ActionEvent event) {
    }

    @FXML
    private void delete(ActionEvent event) {
        VisuaLigue.domain.deleteCurrentEntity();
    }

}
