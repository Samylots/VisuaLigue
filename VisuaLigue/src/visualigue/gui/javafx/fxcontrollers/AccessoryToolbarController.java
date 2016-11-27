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
import visualigue.VisuaLigue;

/**
 * FXML Controller class
 *
 * @author Samuel
 */
public class AccessoryToolbarController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void unselect(ActionEvent event) {
        VisuaLigue.domain.unSelectCurrentEntity();
    }

    @FXML
    private void delete(ActionEvent event) {
        VisuaLigue.domain.deleteCurrentEntity();
    }

}
