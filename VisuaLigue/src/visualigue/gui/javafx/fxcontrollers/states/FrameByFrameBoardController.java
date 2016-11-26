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
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import visualigue.VisuaLigue;
import visualigue.domain.events.FramesListener;
import visualigue.inter.utils.exceptions.CantDeleteFrameException;
import visualigue.inter.utils.exceptions.MustPlaceAllPlayersOnFieldException;
import visualigue.gui.javafx.fxlayouts.Dialog;

/**
 * FXML Controller class
 *
 * @author Samuel
 */
public class FrameByFrameBoardController implements Initializable, FramesListener {

    @FXML
    private Label frameLabel;
    @FXML
    private Slider frameSlider;

    private Parent parent;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        frameSlider.setMin(1);
        frameSlider.setDisable(true);
    }

    @Override
    public void init(Parent parent) {
        this.parent = parent;
    }

    @FXML
    private void deleteFrame(ActionEvent event) {
        try {
            VisuaLigue.domain.deleteCurrentFrame();
        } catch (CantDeleteFrameException e) {
            Dialog popup = new Dialog("Error", e.getMessage(), parent);
        }
    }

    @FXML
    private void previous(ActionEvent event) {
        VisuaLigue.domain.previousFrame();
    }

    @FXML
    private void next(ActionEvent event) {
        try {
            VisuaLigue.domain.nextFrame();
        } catch (MustPlaceAllPlayersOnFieldException e) {
            Dialog popup = new Dialog("Error", e.getMessage(), parent);
        }
    }

    @FXML
    private void addFrame(ActionEvent event) {
        try {
            VisuaLigue.domain.newFrame();
        } catch (MustPlaceAllPlayersOnFieldException e) {
            Dialog popup = new Dialog("Error", e.getMessage(), parent);
        }
    }

    @Override
    public void updateFrames() {
        frameSlider.setMax(VisuaLigue.domain.getTotalFrame());
        frameLabel.setText(String.valueOf(VisuaLigue.domain.getActualFrame()) + " / " + String.valueOf(VisuaLigue.domain.getTotalFrame()));
        frameSlider.setValue(VisuaLigue.domain.getActualFrame());
    }

}
