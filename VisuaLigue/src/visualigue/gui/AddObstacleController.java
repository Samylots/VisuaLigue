/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.gui;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Samuel
 */
public class AddObstacleController implements Initializable {

    @FXML
    private ImageView preview;
    @FXML
    private TextField name;

    private boolean state = false;
    private Stage owner;
    private StringBuilder path;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void init(Stage owner) {
        this.owner = owner;
    }

    @FXML
    private void importPicture(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        fileChooser.setTitle("Open picture");
        File file = fileChooser.showOpenDialog(owner);
        if (file != null) {
            path.append(file.toURI().toString());
            System.out.println(path);
            preview.setImage(new Image(path.toString()));
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
        owner.close();
    }

    @FXML
    private void confirm(ActionEvent event) {
        state = true;
        owner.close();
    }

    public boolean isConfirmed() {
        return state;
    }

}
