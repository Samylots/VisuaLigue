/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.gui.javafx.fxcontrollers.states;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import visualigue.VisuaLigue;
import visualigue.domain.events.FramesListener;

/**
 * FXML Controller class
 *
 * @author Samuel
 */
public class VisualizationBoardController implements Initializable, FramesListener {

    private Parent parent;
    private boolean isChangingBySlider = false;

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
        timeConfig.setText("5");
        timeSlider.setMin(1);
        timeSlider.setShowTickLabels(true);
        timeSlider.setShowTickMarks(true);
        timeSlider.setBlockIncrement(1);
        timeSlider.setMajorTickUnit(5);
        timeSlider.setMinorTickCount(4);
        timeSlider.setSnapToTicks(true);
        timeSlider.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
            isChangingBySlider = true;
            VisuaLigue.domain.showFrame(new_val.intValue());
            isChangingBySlider = false;
        });
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
        if (!isChangingBySlider) {
            timeSlider.setMax(VisuaLigue.domain.getTotalFrame());
            timeSlider.setValue(VisuaLigue.domain.getActualFrame());
        }
    }

}
