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
import javafx.scene.Parent;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import visualigue.VisuaLigue;
import visualigue.events.FramesListener;

/**
 * FXML Controller class
 *
 * @author Samuel
 */
public class VisualizationBoardController implements Initializable, FramesListener {

    private Parent parent;

    @Override
    public void init(Parent parent) {
        this.parent = parent;
    }

    @FXML
    private Slider timeSlider;
    @FXML
    private TextField timeConfig;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        timeSlider.setMin(1);
        timeSlider.setDisable(true);
    }

    @FXML
    private void backwardStep(ActionEvent event) {
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

    @FXML
    private void stop(ActionEvent event) {
    }

    @Override
    public void updateFrames() {
        timeSlider.setMax(VisuaLigue.domain.getTotalFrame());
        timeSlider.setValue(VisuaLigue.domain.getActualFrame());
    }

}
