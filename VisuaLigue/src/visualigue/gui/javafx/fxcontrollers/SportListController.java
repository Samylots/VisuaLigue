/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.gui.javafx.fxcontrollers;

import visualigue.gui.javafx.fxcontrollers.items.SportListItemController;
import java.io.Serializable;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import visualigue.VisuaLigue;
import visualigue.gui.javafx.fxlayouts.CustomWindow;
import visualigue.gui.javafx.fxlayouts.FXLoader;
import visualigue.dto.*;

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

    private int selectedId;
    private Stage stage;

    private boolean selectable;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void init(Stage stage, boolean selectable) {
        this.selectable = selectable;
        this.stage = stage;
    }

    public void refreshSports() {
        sportList.getChildren().clear();
        addSportListItems(VisuaLigue.domain.getAvailableSports());
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
            itemController.init(this, new Image(sport.fieldPicturePath), sport.name, sport.id);
            itemController.setSelectable(selectable);
        } catch (Exception e) {
            //no pic then...
        }
        sportList.getChildren().add(node);
    }

    @FXML
    public void addNewSport() {
        Node node = FXLoader.getInstance().load("addSport.fxml");
        AddSportController controller = FXLoader.getInstance().getLastController();
        CustomWindow window = new CustomWindow(root, (Parent) node);
        controller.init((Stage) window, (Parent) node);
        window.showAndWait();
        if (controller.isConfirmed()) {
            addSportToDomain(controller);
            //DomainController.getInstance().addSport(controller.getFieldPath(), controller.getName());
            refreshSports();
        }
    }

    public void addSportToDomain(AddSportController sport) {
        int sportId = VisuaLigue.domain.createNewSport(sport.getSportName(), sport.getFieldPath(), (double) sport.getFieldWidth(), (double) sport.getFieldHeight(),
                sport.getAccessoryPath(), sport.getAccessoryWidth(), sport.getAccessoryHeight());
        sport.getTeamsData().stream().forEach((team) -> {
            VisuaLigue.domain.addTeamToSport(team.getName(), team.getColor(), sportId);
        });
        sport.getPlayersData().stream().forEach((player) -> {
            VisuaLigue.domain.addPlayerToSportTeam(player.getName(), player.getRole(), player.getTeam(), sportId);
        });
    }

    public void select(int id) {
        selectedId = id;
        stage.close();
    }

    public int getSelectedId() {
        return selectedId;
    }

}
