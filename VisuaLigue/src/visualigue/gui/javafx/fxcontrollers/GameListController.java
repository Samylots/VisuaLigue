/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.gui.javafx.fxcontrollers;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import visualigue.VisuaLigue;
import visualigue.inter.dto.GameDTO;
import visualigue.inter.dto.SportDTO;
import visualigue.gui.javafx.fxcontrollers.items.GameListItemController;
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
    private Accordion sportList;

    private Stage stage;
    @FXML
    private Button addSport;

    private final Map<Integer, VBox> sports = new HashMap<>();

    private TitledPane paneToOpen;
    private int selectedId;

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

    public void init(Stage stage) {
        this.stage = stage;
    }

    public void refreshGames() {
        sportList.getPanes().clear();
        addSportItems(VisuaLigue.domain.getAvailableSports());
        addGameItems(VisuaLigue.domain.getAvailableGames());
    }

    private void addSportItems(List<SportDTO> sports) {
        sports.stream().forEach((sport) -> {
            if (VisuaLigue.domain.sportHasGames(sport.id)) {
                createSportTitledPane(sport);
            }
        });
        sportList.setExpandedPane(paneToOpen);
    }

    private void createSportTitledPane(SportDTO sport) {
        VBox box = new VBox(5);
        box.setAlignment(Pos.TOP_CENTER);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setContent(box);
        TitledPane teamPane = new TitledPane(sport.name, scrollPane);
        ImageView sportPic = new ImageView(new Image(sport.fieldPicturePath));
        sportPic.setFitWidth(50);
        sportPic.setFitHeight(30);
        sportPic.minHeight(30);
        sportPic.setPreserveRatio(true);
        teamPane.setGraphic(sportPic);
        sports.put(sport.id, box);
        if (sportList.getPanes().size() == 0) {
            paneToOpen = teamPane;
        }
        sportList.getPanes().add(teamPane);
    }

    private void addGameItems(List<GameDTO> games) {
        games.stream().forEach((game) -> addGameItem(game));
    }

    private void addGameItem(GameDTO game) {
        Node node = FXLoader.getInstance().load("gameListItem.fxml");
        GameListItemController itemController = FXLoader.getInstance().getLastController();
        try {
            itemController.init(this, new Image(game.picPath), game.name, game.id);
        } catch (Exception e) {
            //no pic then...
        }
        sports.get(game.sportId).getChildren().add(node);
    }

    public void select(int id) {
        selectedId = id;
        stage.close();
    }

    public int getSelectedId() {
        return selectedId;
    }
}
