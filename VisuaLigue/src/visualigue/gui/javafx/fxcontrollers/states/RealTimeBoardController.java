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
import visualigue.gui.javafx.fxlayouts.Dialog;
import visualigue.inter.utils.exceptions.CantDeleteFrameException;
import visualigue.inter.utils.exceptions.MustPlaceAllPlayersOnFieldException;

/**
 * FXML Controller class
 *
 * @author Samuel
 */
public class RealTimeBoardController implements Initializable, FramesListener {

    @FXML
    private Label frameLabel;
    @FXML
    private Slider frameSlider;
    
    private Parent parent;
    
    @Override
    public void init(Parent parent) {
        this.parent = parent;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        frameSlider.setMin(1);
        frameSlider.setDisable(true);
    }

    @Override
    public void updateFrames() {
        frameSlider.setMax(VisuaLigue.domain.getTotalFrame());
        frameLabel.setText(String.valueOf(VisuaLigue.domain.getActualFrame()) + " / " + String.valueOf(VisuaLigue.domain.getTotalFrame()));
        frameSlider.setValue(VisuaLigue.domain.getActualFrame());
    }
}
