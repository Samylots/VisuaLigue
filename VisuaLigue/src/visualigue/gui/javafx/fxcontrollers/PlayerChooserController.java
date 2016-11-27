/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.gui.javafx.fxcontrollers;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import visualigue.VisuaLigue;
import visualigue.inter.dto.PlayerDTO;
import visualigue.inter.dto.TeamDTO;
import visualigue.inter.utils.ColorConverter;

/**
 * FXML Controller class
 *
 * @author Samuel
 */
public class PlayerChooserController implements Initializable {

    @FXML
    private ScrollPane root;
    @FXML
    private Accordion teams;

    //private Map<Integer, PlayerDTO> players;
    private Map<Integer, Button> buttons = new HashMap<>();

    private int seletedPlayer = 0;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        teams.setMaxHeight(Double.MAX_VALUE);
        root.setMaxHeight(Double.MAX_VALUE);
    }

    public void refreshTeams() {
        teams.getPanes().clear();
        List<TeamDTO> teamsData = VisuaLigue.domain.getCurrentGameTeams();
        teamsData.stream().forEach((team) -> {
            VBox box = new VBox(5);
            box.setAlignment(Pos.TOP_CENTER);
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(true);
            scrollPane.setContent(box);
            TitledPane teamPane = new TitledPane(team.name, scrollPane);
            Color paneColor = Color.web(team.color);
            teamPane.setStyle("-fx-base: #" + ColorConverter.toHex(paneColor.darker()) + ";");
            team.players.stream().forEach((player) -> {
                box.getChildren().add(createPlayerButton(team.color, player));
            });
            teams.getPanes().add(teamPane);
        });
        Platform.runLater(() -> {
            selectNext();
        });
    }

    public Button createPlayerButton(String teamColor, PlayerDTO player) {
        Button playerButton = new Button("#" + Integer.toString(player.number));
        Tooltip tool = new Tooltip("Name: " + player.name + "\n Role: " + player.role);
        playerButton.setTooltip(tool);
        playerButton.setStyle("-fx-base: " + teamColor + ";");
        playerButton.setMaxWidth(Double.MAX_VALUE);
        playerButton.setOnAction((ActionEvent e) -> {
            seletedPlayer = player.id;
        });
        playerButton.setDisable(player.isOnBoard);

        buttons.put(player.id, playerButton);
        return playerButton;
    }

    public void disableSelectedPlayer() {
        if (seletedPlayer > 0) {
            buttons.get(seletedPlayer).setDisable(true);
            selectNext();
        }
    }

    public int getSelectedPlayer() {
        return seletedPlayer;
    }

    private void selectNext() {
        Iterator it = buttons.entrySet().iterator();
        seletedPlayer = 0;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            Button button = (Button) pair.getValue();
            if (!button.isDisabled()) {
                seletedPlayer = (int) pair.getKey();
                try {
                    teams.setExpandedPane(((TitledPane) button.getParent().getParent().getParent().getParent().getParent().getParent()));
                } catch (Exception e) {
                    teams.setExpandedPane(teams.getPanes().get(0));
                }
                Platform.runLater(() -> {
                    button.requestFocus();
                });
                break;
            }
        }
    }

}
