/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.gui.javafx.fxcontrollers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import visualigue.domain.VisuaLigueController;
import visualigue.dto.SportDTO;
import visualigue.gui.javafx.fxcontrollers.items.SportListItemController;
import visualigue.gui.javafx.fxlayouts.FXLoader;

/**
 * FXML Controller class
 *
 * @author Samuel
 */
public class GameListController implements Initializable {

    @FXML
    private VBox root;
    @FXML
    private VBox sportList;

    private Stage stage;
    private VisuaLigueController domain;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void init(VisuaLigueController domain, Stage stage) {
        this.stage = stage;
        this.domain = domain;
    }

    public void refreshSports() {
        sportList.getChildren().clear();
        addSportListItems(domain.getAvailableSports());
    }

    private void addSportListItems(List<SportDTO> sports) {
        sports.stream().forEach((sport) -> {
            addSportListItem(sport);
        });
    }

    private void addSportListItem(SportDTO sport) {
        Node node = FXLoader.getInstance().load("sportListItem.fxml");
        SportListItemController itemController = FXLoader.getInstance().getLastController();
        try {
            //itemController.init(this, sport.getPic(), sport.getName(), sport.getSportId());
        } catch (Exception e) {
            //no pic then...
        }
        sportList.getChildren().add(node);
    }

}
