/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.gui.javafx.fxcontrollers;

import visualigue.gui.javafx.fxcontrollers.AddObstacleController;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import visualigue.domain.game.Obstacle;
import visualigue.domain.dumies.DomainController;
import visualigue.gui.javafx.fxcontrollers.items.ObstacleListItemController;
import visualigue.gui.javafx.fxlayouts.CustomWindow;
import visualigue.gui.javafx.fxlayouts.FXLoader;

/**
 * FXML Controller class
 *
 * @author Samuel
 */
public class ObstacleListController implements Initializable {

    @FXML
    private VBox root;
    @FXML
    private Button addObstacle;
    @FXML
    private VBox obstacleList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void refreshObstacles() {
        obstacleList.getChildren().clear();
        addObstacleListItems(DomainController.getInstance().getObstacles());
    }

    private void addObstacleListItems(List<Obstacle> obstacles) {
        for (Obstacle obstacle : obstacles) {
            addObstacleListItem(obstacle);
        }
    }

    private void addObstacleListItem(Obstacle obstacle) {
        Node node = FXLoader.getInstance().load("obstacleListItem.fxml");
        ObstacleListItemController itemController = FXLoader.getInstance().getLastController();
        try {
            itemController.init(obstacle.getPicUrl(), obstacle.getName(), obstacle.getId(), this);
        } catch (Exception e) {
            //no pic then...
        }
        obstacleList.getChildren().add(node);
    }

    @FXML
    private void addNewObstacle(ActionEvent event) {
        Node node = FXLoader.getInstance().load("addObstacle.fxml");
        AddObstacleController controller = FXLoader.getInstance().getLastController();
        CustomWindow window = new CustomWindow(root, (Parent) node);
        controller.init((Stage) window);
        window.showAndWait();
        if (controller.isConfirmed()) {
            DomainController.getInstance().addObstacle(controller.getPath(), controller.getName());
            refreshObstacles();
        }
    }

}
