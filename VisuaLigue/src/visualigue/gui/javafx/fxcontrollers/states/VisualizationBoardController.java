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
        timeConfig.setText("5");
    }

    @FXML
    private void backwardStep(ActionEvent event) {
        VisuaLigue.domain.goToFrame(-(getStepNumber()));
    }

    private int getStepNumber() {
        try {
            return Integer.parseInt(timeConfig.getText());
        } catch (Exception e) {
            return 1;
        }
    }

    @FXML
    private void backward(ActionEvent event) {
        VisuaLigue.domain.goToFrame(-1);
    }

    @FXML
    private void play(ActionEvent event) {
        VisuaLigue.domain.startGame();
    }

    @FXML
    private void pause(ActionEvent event) {
        VisuaLigue.domain.pauseGame();
    }

    @FXML
    private void foward(ActionEvent event) {
        VisuaLigue.domain.goToFrame(1);
    }

    @FXML
    private void fowardStep(ActionEvent event) {
        VisuaLigue.domain.goToFrame(getStepNumber());
    }

    @FXML
    private void stop(ActionEvent event) {
        VisuaLigue.domain.pauseGame();
        VisuaLigue.domain.goToFrame(-((int) VisuaLigue.domain.getTotalFrame()));
    }

    @Override
    public void updateFrames() {
        timeSlider.setMax(VisuaLigue.domain.getTotalFrame());
        timeSlider.setValue(VisuaLigue.domain.getActualFrame());
    }

}
