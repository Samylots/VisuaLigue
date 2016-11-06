/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.gui;

import java.io.File;
import java.io.Serializable;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
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
public class AddSportController implements Initializable, Serializable {

    @FXML
    private TextField name;
    @FXML
    private TextField totalPlayer;
    @FXML
    private TextField width;
    @FXML
    private TextField height;
    @FXML
    private TextArea categories;
    @FXML
    private ImageView accessoryPreview;
    @FXML
    private ImageView fieldPreview;

    private StringBuilder accessoryPath;
    private StringBuilder fieldPath;

    private Stage owner;
    private boolean state = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        accessoryPath = new StringBuilder();
        fieldPath = new StringBuilder();
    }

    public void init(Stage owner) {
        this.owner = owner;
    }

    @FXML
    private void importAccessory(ActionEvent event) {
        loadPictureTo(accessoryPreview, accessoryPath);
    }

    @FXML
    private void importFieldPicture(ActionEvent event) {
        loadPictureTo(fieldPreview, fieldPath);
    }

    private void loadPictureTo(ImageView preview, StringBuilder path) {
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

    public String getName() {
        return name.getText();
    }

    public int getTotal() {
        return Integer.parseInt(totalPlayer.getText());
    }

    public int getWidth() {
        return Integer.parseInt(width.getText());
    }

    public int getHeight() {
        return Integer.parseInt(height.getText());
    }

    public List<String> getCategories() {
        return Arrays.asList(categories.getText().split("\n"));
    }

    public String getAccessoryPath() {
        return accessoryPath.toString();
    }

    public String getFieldPath() {
        return fieldPath.toString();
    }

}
