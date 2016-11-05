/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.gui;

import visualigue.gui.items.SportListItemController;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import visualigue.domain.dumies.Sport;
import visualigue.domain.dumies.domainController;
import visualigue.gui.layouts.CustomWindow;
import visualigue.gui.layouts.FXLoader;

/**
 * FXML Controller class
 *
 * @author Samuel
 */
public class SportListController implements Initializable, Serializable {

    @FXML
    private VBox root;
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
    }

    public void refreshSports() {
        sportList.getChildren().clear();
        addSportListItems(domainController.getInstance().getSports());
    }

    private void addSportListItems(List<Sport> sports) {
        for (Sport sport : sports) {
            addSportListItem(sport);
        }
    }

    private void addSportListItem(Sport sport) {
        Node node = FXLoader.getInstance().load("sportListItem.fxml");
        SportListItemController itemController = FXLoader.getInstance().getLastController();
        try {
            itemController.init(sport.getPicUrl(), sport.getName(), sport.getId());
        } catch (Exception e) {
            //no pic then...
        }
        sportList.getChildren().add(node);
    }

    @FXML
    public void addNewSport() {
        System.out.println("Opening new sport window!");
        Node node = FXLoader.getInstance().load("addSport.fxml");
        AddSportController controller = FXLoader.getInstance().getLastController();
        CustomWindow window = new CustomWindow(root, (Parent) node);
        controller.init((Stage) window);
        window.showAndWait();
        if (controller.isConfirmed()) {
            domainController.getInstance().addSport(controller.getFieldPath(), controller.getName());
            refreshSports();
        }
    }

}
