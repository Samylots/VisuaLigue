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
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Samuel
 */
public class VisualizationBoardController implements Initializable {

    @FXML
    private Slider timeSlider;
    @FXML
    private TextField timeConfig;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void backwrdStep(ActionEvent event) {
    }

    @FXML
    private void backward(ActionEvent event) {
    }

    @FXML
    private void play(ActionEvent event) {
    }

    @FXML
    private void pause(ActionEvent event) {
    }

    @FXML
    private void foward(ActionEvent event) {
    }

    @FXML
    private void fowardStep(ActionEvent event) {
    }

}
