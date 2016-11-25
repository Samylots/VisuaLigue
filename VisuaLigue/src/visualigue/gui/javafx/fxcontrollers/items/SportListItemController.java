/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.gui.javafx.fxcontrollers.items;

import java.io.File;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import visualigue.gui.javafx.fxcontrollers.SportListController;

/**
 * FXML Controller class
 *
 * @author Samuel
 */
public class SportListItemController implements Initializable, Serializable {

    private SportListController parentController;

    @FXML
    private BorderPane sportItem;
    @FXML
    private ImageView sportPicture;
    @FXML
    private Label sportTitle;
    @FXML
    private Button selectButton;

    private int id;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    /**
     * Initializes the item
     *
     * @param picUrl
     * @param sportName
     * @param domainId
     */
    public void init(SportListController controller, Image pic, String sportName, int domainId) {
        parentController = controller;
        sportTitle.setText(sportName);
        id = domainId;

        //As you can see, it's possible to stack handler on each other! This button will do all handlers declared.
        selectButton.setOnMouseClicked((MouseEvent e) -> {
            controller.select(domainId);
        });
        //But it need to be different action. Exemple: this handler will overide the "selectSport()" function declared in the FXML file
        //Comment this to see the "selectSport()" in action!
        /*selectButton.setOnAction((ActionEvent e) -> {
         System.out.println("This is third way to add handler on controls");
         });*/

        //need to be last, because it stop the method if there is no picture...
        sportPicture.setImage(pic);
    }

    public void setPicture(File file) {
        sportPicture.setImage(new Image(file.toURI().toString()));
    }

    @FXML
    public void selectSport() {
        parentController.select(id);
        System.out.println("Sport #" + id + " selected!");
    }

    public void setSelectable(boolean selectable) {
        selectButton.setDisable(!selectable);
    }
}
