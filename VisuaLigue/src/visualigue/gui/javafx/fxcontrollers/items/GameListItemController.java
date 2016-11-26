/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.gui.javafx.fxcontrollers.items;

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
import visualigue.gui.javafx.fxcontrollers.GameListController;
import visualigue.gui.javafx.fxlayouts.Dialog;

/**
 * FXML Controller class
 *
 * @author Samuel
 */
public class GameListItemController implements Initializable {

    @FXML
    private BorderPane gameItem;
    @FXML
    private ImageView gamePicture;
    @FXML
    private Label gameTitle;
    @FXML
    private Button selectButton;

    private GameListController parentController;
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
     * @param controller
     * @param pic
     * @param gameName
     * @param gameId
     */
    public void init(GameListController controller, Image pic, String gameName, int gameId) {
        parentController = controller;
        gameTitle.setText(gameName);
        id = gameId;

        //need to be last, because it stop the method if there is no picture...
        gamePicture.setImage(pic);
    }

    @FXML
    private void selectGame(ActionEvent event) {
        parentController.select(id);
    }

    @FXML
    private void deleteGame(ActionEvent event) {
        Dialog popup = new Dialog("Deleting Game", "Are you sure you want to delete this game?\nThis process can't be reversed!", gameItem);
        if (popup.isConfirmed()) {
            VisuaLigue.domain.deleteGame(id);
            parentController.refreshGames();
        }
    }

}
