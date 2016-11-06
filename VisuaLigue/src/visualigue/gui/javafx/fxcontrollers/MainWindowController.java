/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.gui.javafx.fxcontrollers;

import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import visualigue.domain.utils.Mode;
import visualigue.gui.javafx.fxlayouts.CustomWindow;
import visualigue.gui.javafx.fxlayouts.FXLoader;

/**
 * FXML Controller class
 *
 * @author Samuel
 */
public class MainWindowController implements Initializable, Serializable {

    @FXML
    private BorderPane root;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void init() {
        changeViewTo(Mode.FRAME_BY_FRAME);
    }

    @FXML
    private void newGame(ActionEvent event) {
    }

    @FXML
    private void openGame(ActionEvent event) {
    }

    @FXML
    private void ExportGame(ActionEvent event) {
    }

    @FXML
    private void openSportList(ActionEvent event) {
        Node node = FXLoader.getInstance().load("sportList.fxml");
        SportListController controller = FXLoader.getInstance().getLastController();
        controller.refreshSports();
        CustomWindow window = new CustomWindow(root, (Parent) node);
        window.setTitle("Sport List");
        window.showAndWait();
    }

    @FXML
    private void openObstacleList(ActionEvent event) {
        Node node = FXLoader.getInstance().load("obstacleList.fxml");
        ObstacleListController controller = FXLoader.getInstance().getLastController();
        controller.refreshObstacles();
        CustomWindow window = new CustomWindow(root, (Parent) node);
        window.setTitle("Obstacle List");
        window.showAndWait();
    }

    @FXML
    private void setToMode1(ActionEvent event) {
        changeViewTo(Mode.FRAME_BY_FRAME);
    }

    @FXML
    private void setToMode2(ActionEvent event) {
        changeViewTo(Mode.REAL_TIME);
    }

    @FXML
    private void visualize(ActionEvent event) {
        changeViewTo(Mode.VISUALISATION);
    }

    @FXML
    private void toggleRoles(ActionEvent event) {
    }

    @FXML
    private void openOptions(ActionEvent event) {
    }

    public void changeViewTo(Mode state) {
        root.setBottom(state.getNode());
    }

}
