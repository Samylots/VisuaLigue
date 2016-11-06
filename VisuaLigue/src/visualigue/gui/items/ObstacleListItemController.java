/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.gui.items;

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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

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

    /**
     * Initializes the controller class.
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
    public void init(String picUrl, String sportName, String domainId) {
        sportTitle.setText(sportName);
        sportDomainId = domainId;

        //As you can see, it's possible to stack handler on each other! This button will do all handlers declared.
        selectButton.setOnMouseClicked((MouseEvent e) -> {
            System.out.println("This is another way to add handler on controls");
        });
        //But it need to be different action. Exemple: this handler will overide the "selectSport()" function declared in the FXML file
        //Comment this to see the "selectSport()" in action!
        /*selectButton.setOnAction((ActionEvent e) -> {
         System.out.println("This is third way to add handler on controls");
         });*/

        //need to be last, because it stop the method if there is no picture...
        System.out.println("TRYING " + picUrl);
        picture.setImage(new Image(picUrl));
    }

    public void setPicture(File file) {
        picture.setImage(new Image(file.toURI().toString()));
    }

    @FXML
    private void deleteObstacle(ActionEvent event) {
    }

    @FXML
    private void selectObstacle(ActionEvent event) {
    }

}
