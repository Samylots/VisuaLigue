/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.gui.javafx.fxcontrollers.items;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import visualigue.VisuaLigue;
import visualigue.gui.javafx.fxcontrollers.ObstacleController;
import visualigue.gui.javafx.fxcontrollers.ObstacleListController;
import visualigue.gui.javafx.fxlayouts.CustomWindow;
import visualigue.gui.javafx.fxlayouts.FXLoader;
import visualigue.inter.dto.ObstacleDTO;

/**
 * FXML Controller class
 *
 * @author Samuel
 */
public class ObstacleListItemController implements Initializable {

    @FXML
    private BorderPane sportItem;
    @FXML
    private ImageView picture;
    @FXML
    private Label name;
    @FXML
    private ObstacleListController parentController;
    @FXML
    private Button editButton;
    private ObstacleDTO obstacle;
    private Parent root;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    /**
     * Initializes the item
     *
     * @param picUrl
     * @param sportName
     * @param domainId
     * @param controller
     */
    public void init(ObstacleDTO obstacle, ObstacleListController controller, Parent root) {
        parentController = controller;
        name.setText(obstacle.name);
        this.root = root;
        this.obstacle = obstacle;

        //need to be last, because it stops the method if there is no picture...
        picture.setImage(new Image(obstacle.picturePath));
    }

    public void setPicture(File file) {
        picture.setImage(new Image(file.toURI().toString()));
    }

    @FXML
    private void deleteObstacle(ActionEvent event) {
        VisuaLigue.domain.deleteObstacle(obstacle.id);
        parentController.refreshObstacles();
    }

    @FXML
    private void editObstacle(ActionEvent event) {
        //ouvrir fenetre et envoyer dans init de addobstaclecontroller l'objet obstacle
        Node node = FXLoader.getInstance().load("addObstacle.fxml");
        ObstacleController controller = FXLoader.getInstance().getLastController();
        CustomWindow window = new CustomWindow(root, (Parent) node);
        controller.init((Stage) window, (Parent) node);
        controller.initObstacle(obstacle);
        window.showAndWait();
        if (controller.isConfirmed()) {
            parentController.refreshObstacles();
        }
    }

}
