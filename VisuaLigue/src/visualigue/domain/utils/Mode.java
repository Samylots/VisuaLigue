/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.domain.utils;

import javafx.scene.Node;
import visualigue.gui.javafx.fxcontrollers.ObstacleListController;
import visualigue.gui.javafx.fxlayouts.FXLoader;

/**
 *
 * @author Samuel
 */
public enum Mode {

    FRAME_BY_FRAME("frameByFrameBoard.fxml"),
    REAL_TIME("realTimeBoard.fxml"),
    VISUALISATION("visualizationBoard.fxml");

    private final String fxmlFile;

    Mode(String file) {
        fxmlFile = file;
    }

    public Node getNode() {
        Node node = FXLoader.getInstance().load(fxmlFile);
        return node;
    }

    public <T> T getController() {
        Node node = FXLoader.getInstance().load(fxmlFile);
        ObstacleListController controller = FXLoader.getInstance().getLastController();
        return (T) controller;
    }
}
