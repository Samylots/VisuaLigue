/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.gui.javafx.helpers;

import java.io.Serializable;
import javafx.scene.Node;
import visualigue.utils.Mode;
import visualigue.gui.javafx.fxcontrollers.ObstacleListController;
import visualigue.gui.javafx.fxlayouts.FXLoader;

/**
 *
 * @author Samuel
 */
public enum UIMode implements Serializable {

    FRAME_BY_FRAME("frameByFrameBoard.fxml", Mode.FRAME_BY_FRAME),
    REAL_TIME("realTimeBoard.fxml", Mode.REAL_TIME),
    VISUALISATION("visualizationBoard.fxml", Mode.VISUALISATION);

    private final String fxmlFile;
    private final Mode mode;

    UIMode(String file, Mode mode) {
        this.fxmlFile = file;
        this.mode = mode;
    }

    public Node getNode() {
        Node node = FXLoader.getInstance().load(fxmlFile);
        return node;
    }

    public Mode getMode() {
        return mode;
    }

    public <T> T getController() {
        T controller = FXLoader.getInstance().getLastController();
        return controller;
    }
}
