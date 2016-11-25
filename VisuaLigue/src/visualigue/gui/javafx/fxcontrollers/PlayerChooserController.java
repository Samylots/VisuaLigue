/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.gui.javafx.fxcontrollers;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import visualigue.VisuaLigue;
import visualigue.domain.VisuaLigueController;
import visualigue.dto.PlayerDTO;
import visualigue.dto.TeamDTO;

/**
 * FXML Controller class
 *
 * @author Samuel
 */
public class PlayerChooserController implements Initializable, Serializable {

    @FXML
    private ScrollPane root;
    @FXML
    private Accordion teams;

    //private Map<Integer, PlayerDTO> players;
    private List<Button> buttons = new ArrayList<>();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void refreshTeams() {
        teams.getPanes().clear();
        List<TitledPane> panes = new ArrayList<>();
        List<TeamDTO> teamsData = VisuaLigue.domain.getCurrentGameTeams();
        teamsData.stream().forEach((team) -> {
            VBox box = new VBox(5);
            box.setAlignment(Pos.TOP_CENTER);
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(true);
            scrollPane.setContent(box);
            TitledPane teamPane = new TitledPane(team.name, scrollPane);
            team.players.stream().forEach((player) -> {
                box.getChildren().add(createPlayerButton(team.color, player));
            });
            teams.getPanes().add(teamPane);
            panes.add(teamPane);
        });
        teams.setExpandedPane(panes.get(0));
    }

    public Button createPlayerButton(String teamColor, PlayerDTO player) {
        Button playerButton = new Button("#" + Integer.toString(player.number));
        Tooltip tool = new Tooltip("Name: " + player.name + "\n Role: " + player.role);
        playerButton.setTooltip(tool);
        playerButton.setStyle("-fx-base: " + teamColor + ";");
        playerButton.setMaxWidth(Double.MAX_VALUE);
        buttons.add(playerButton);
        return playerButton;
    }

}
