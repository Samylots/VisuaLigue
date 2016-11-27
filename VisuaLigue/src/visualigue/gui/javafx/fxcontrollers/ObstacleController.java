/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.gui.javafx.fxcontrollers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import visualigue.gui.javafx.fxlayouts.Dialog;
import visualigue.gui.javafx.helpers.Utils;
import visualigue.inter.dto.ObstacleDTO;

/**
 * FXML Controller class
 *
 * @author Samuel
 */
public class ObstacleController implements Initializable {

    @FXML
    private ImageView preview;
    @FXML
    private TextField name;
    private boolean state = false;
    private Stage owner;
    private Parent parent;
    private StringBuilder path = new StringBuilder();
    @FXML
    private TextField obstacleWidth;
    @FXML
    private TextField obstacleHeight;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void init(Stage owner, Parent parent) {
        this.owner = owner;
        this.parent = parent;
    }
    
    public void initObstacle(ObstacleDTO obstacle) {
        obstacleWidth.setText(String.valueOf(obstacle.dimension.getWidth()));
        obstacleHeight.setText(String.valueOf(obstacle.dimension.getHeight()));
        name.setText(obstacle.name);
        preview.setImage(new Image(obstacle.picturePath));
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
        if (isValid()) {
            state = true;
            owner.close();
        } else {
            Dialog popup = new Dialog("New Obstacle Error", "Please fill all fields before creating a new obstacle.", parent);
        }
    }

    public boolean isConfirmed() {
        return state;
    }

    private boolean isValid() {
        boolean isValid = true;
        isValid &= !name.getText().equals("");
        isValid &= !path.toString().equals("");
        isValid &= !obstacleWidth.getText().equals("");
        isValid &= !obstacleHeight.getText().equals("");
        isValid &= Utils.isNumeric(obstacleWidth.getText());
        isValid &= Utils.isNumeric(obstacleHeight.getText());
        return isValid;
    }

    public String getName() {
        return name.getText();
    }

    public String getPath() {
        return path.toString();
    }

    public double getWidth() {
        return Double.parseDouble(obstacleWidth.getText());
    }

    public double getHeight() {
        return Double.parseDouble(obstacleHeight.getText());
    }
}
