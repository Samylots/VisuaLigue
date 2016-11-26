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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import visualigue.VisuaLigue;
import visualigue.gui.javafx.fxcontrollers.ObstacleListController;

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
    private Button selectButton;

    private int id;
    private ObstacleListController parentController;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        selectButton.setVisible(false); //Faster that editing the fxml file and fixing it...
    }

    /**
     * Initializes the item
     *
     * @param picUrl
     * @param sportName
     * @param domainId
     * @param controller
     */
    public void init(String picUrl, String sportName, int domainId, ObstacleListController controller) {
        parentController = controller;
        name.setText(sportName);
        id = domainId;

        //need to be last, because it stop the method if there is no picture...
        picture.setImage(new Image(picUrl));
    }

    public void setPicture(File file) {
        picture.setImage(new Image(file.toURI().toString()));
    }

    @FXML
    private void deleteObstacle(ActionEvent event) {
        VisuaLigue.domain.deleteObstacle(id);
        parentController.refreshObstacles();
    }

    @FXML
    private void selectObstacle(ActionEvent event) {
        parentController.selectObstacle(id);
    }

}
