/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.gui.items;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Samuel
 */
public class SportListItemController implements Initializable {

    @FXML
    private BorderPane sportItem;
    @FXML
    private ImageView sportPicture;
    @FXML
    private Label sportTitle;
    @FXML
    private Button selectButton;

    private String sportDomainId;

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
    public void init(String picUrl, String sportName, String domainId) {
        sportTitle.setText(sportName);
        sportPicture.setImage(new Image(picUrl));
        sportDomainId = domainId;
    }

    public void setPicture(File file) {
        sportPicture.setImage(new Image(file.toURI().toString()));
    }

}
