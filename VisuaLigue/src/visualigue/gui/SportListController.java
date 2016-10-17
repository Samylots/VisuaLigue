/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.gui;

import visualigue.gui.items.SportListItemController;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import visualigue.domain.dumies.Sport;
import visualigue.gui.layouts.FXLoader;

/**
 * FXML Controller class
 *
 * @author Samuel
 */
public class SportListController implements Initializable {

    FXLoader loader = new FXLoader();
    @FXML
    private Button addSport;
    @FXML
    private VBox sportList;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //call domaine to get sports
    }

    public void init(List<Sport> sports) {
        addSportListItems(sports);
    }

    private void addSportListItems(List<Sport> sports) {
        for (Sport sport : sports) {
            addSportListItem(sport);
        }
    }

    private void addSportListItem(Sport sport) {
        Node node = loader.load("sportListItem.fxml");
        SportListItemController itemController = loader.getLastController();
        itemController.init(sport.getPicUrl(), sport.getName(), sport.getId());
        sportList.getChildren().add(node);
    }

}
