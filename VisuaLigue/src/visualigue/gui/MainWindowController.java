/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.gui;

import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import visualigue.domain.dumies.domainController;
import visualigue.gui.layouts.CustomWindow;
import visualigue.gui.layouts.FXLoader;

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
    }

    @FXML
    private void setToMode1(ActionEvent event) {
    }

    @FXML
    private void setToMode2(ActionEvent event) {
    }

    @FXML
    private void visualize(ActionEvent event) {
    }

    @FXML
    private void toggleRoles(ActionEvent event) {
    }

    @FXML
    private void openOptions(ActionEvent event) {
    }

}
