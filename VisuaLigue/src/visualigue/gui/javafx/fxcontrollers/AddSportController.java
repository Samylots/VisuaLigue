/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.gui.javafx.fxcontrollers;

import java.io.File;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import visualigue.gui.javafx.fxlayouts.Dialog;
import visualigue.gui.javafx.helpers.Utils;
import visualigue.gui.javafx.models.PlayerTableItem;
import visualigue.gui.javafx.models.TeamTableItem;

/**
 * FXML Controller class
 *
 * @author Samuel
 */
public class AddSportController implements Initializable, Serializable {

    @FXML
    private TextField sportName;
    @FXML
    private TextField accessoryWidth;
    @FXML
    private TextField accessoryHeight;

    @FXML
    private TextField fieldWidth;
    @FXML
    private TextField fieldHeigth;
    @FXML
    private ImageView accessoryPreview;
    @FXML
    private ImageView fieldPreview;

    private StringBuilder accessoryPath;
    private StringBuilder fieldPath;

    @FXML
    private TextField newTeamName;
    @FXML
    private ColorPicker newTeamColor;
    @FXML
    private TextField newPlayerName;
    @FXML
    private TextField newPlayerRole;
    @FXML
    private ChoiceBox newPlayerTeam;

    @FXML
    private TableColumn playerNameCol;
    @FXML
    private TableColumn playerRoleCol;
    @FXML
    private TableColumn playerTeamCol;
    @FXML
    private TableColumn teamNameCol;
    @FXML
    private TableColumn teamColorCol;
    @FXML
    private TableView players;
    @FXML
    private TableView teams;

    @FXML
    private Button deleteTeamButton;
    @FXML
    private Button deletePlayerButton;

    private final ObservableList<PlayerTableItem> playersData = FXCollections.observableArrayList();
    private final ObservableList<TeamTableItem> teamsData = FXCollections.observableArrayList();

    private Stage owner;
    private Parent parent;
    private boolean state = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        accessoryPath = new StringBuilder();
        fieldPath = new StringBuilder();
        players.setEditable(true);
        teams.setEditable(true);
        players.setItems(playersData);
        teams.setItems(teamsData);
        playerNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        playerRoleCol.setCellValueFactory(new PropertyValueFactory<>("role"));
        playerTeamCol.setCellValueFactory(new PropertyValueFactory<>("team"));
        teamNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        teamColorCol.setCellValueFactory(new PropertyValueFactory<>("color"));
        enablePlayerAdd(false);
        deleteTeamButton.setDisable(true);
        deletePlayerButton.setDisable(true);
    }

    private void enablePlayerAdd(boolean value) {
        newPlayerName.setDisable(!value);
        newPlayerRole.setDisable(!value);
        newPlayerTeam.setDisable(!value);
    }

    public void init(Stage owner, Parent parent) {
        this.owner = owner;
        this.parent = parent;
    }

    @FXML
    private void importAccessory(ActionEvent event) {
        loadPictureTo(accessoryPreview, accessoryPath);
    }

    @FXML
    private void importField(ActionEvent event) {
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
            path.delete(0, path.length());
            path.append(file.toURI().toString());
            System.out.println(path);
            preview.setImage(new Image(path.toString()));
        }
    }

    @FXML
    private void addNewTeam(ActionEvent event) {
        if (!newTeamName.getText().equals("")) {
            String colorHex = Integer.toHexString(newTeamColor.getValue().hashCode());
            teamsData.add(new TeamTableItem(newTeamName.getText(), "#" + colorHex.substring(0, Math.min(colorHex.length(), 6))));
            newTeamName.clear();
            teamUpdate();
            deleteTeamButton.setDisable(false);
        }
    }

    @FXML
    private void deleteSelectedTeam(ActionEvent event) {
        if (teams.getSelectionModel().getSelectedItem() != null) {
            TeamTableItem teamToDelete = (TeamTableItem) teams.getSelectionModel().getSelectedItem();
            if (!teamHasPlayerInIt(teamToDelete)) {
                Dialog popup = new Dialog("Team deleting", "Are you sure you want to delete this team?", parent);
                if (popup.isConfirmed()) {
                    teamsData.remove(teamToDelete);
                }
            } else {
                Dialog popup = new Dialog("Team deleting error", "Make sure to delete all players in this team before deleting it!", parent);
            }
        }
    }

    private boolean teamHasPlayerInIt(TeamTableItem team) {
        boolean hasPlayer = false;
        for (PlayerTableItem player : playersData) {
            if (player.getTeam().equals(team.getName())) {
                hasPlayer = true;
            }
        }
        return hasPlayer;
    }

    private void teamUpdate() {
        if (teamsData.size() > 0) {
            enablePlayerAdd(true);
        }
        populateTeamChoice();
    }

    private void populateTeamChoice() {
        newPlayerTeam.getItems().clear();
        teamsData.stream().forEach((team) -> {
            newPlayerTeam.getItems().add(team.getName());
        });
        newPlayerTeam.getSelectionModel().selectFirst();
    }

    @FXML
    private void cancel(ActionEvent event) {
        owner.close();
    }

    @FXML
    private void addNewPlayer(ActionEvent event) {
        if (!newPlayerName.getText().equals("") && !newPlayerRole.getText().equals("") && newPlayerTeam.getValue() != null) {
            playersData.add(new PlayerTableItem(newPlayerName.getText(), newPlayerRole.getText(), newPlayerTeam.getValue().toString()));
            newPlayerName.clear();
            newPlayerRole.clear();
            newPlayerTeam.getSelectionModel().selectFirst();
            deletePlayerButton.setDisable(false);
        }
    }

    @FXML
    private void deleteSelectedPlayer(ActionEvent event) {
        if (players.getSelectionModel().getSelectedItem() != null) {
            Dialog popup = new Dialog("Player deleting", "Are you sure you want to delete this player?", parent);
            if (popup.isConfirmed()) {
                playersData.remove(players.getSelectionModel().getSelectedItem());
            }
            if (playersData.size() == 0) {
                deletePlayerButton.setDisable(true);
            }
        }

    }

    @FXML
    private void confirm(ActionEvent event) {
        if (isValidSport()) {
            state = true;
            owner.close();
        } else {
            Dialog popup = new Dialog("New Sport Error", "Please verify to fill all field before creating a new sport.", parent);
        }
    }

    public boolean isValidSport() {
        boolean isValid = true;
        isValid &= !sportName.getText().equals("");
        isValid &= !fieldPath.toString().equals("");
        isValid &= !accessoryPath.toString().equals("");
        isValid &= !fieldWidth.getText().equals("");
        isValid &= !fieldHeigth.getText().equals("");
        isValid &= Utils.isNumeric(fieldWidth.getText());
        isValid &= Utils.isNumeric(fieldHeigth.getText());
        isValid &= !accessoryWidth.getText().equals("");
        isValid &= !accessoryHeight.getText().equals("");
        isValid &= Utils.isNumeric(accessoryWidth.getText());
        isValid &= Utils.isNumeric(accessoryHeight.getText());
        isValid &= teamsData.size() > 0;
        isValid &= playersData.size() > 0;
        return isValid;
    }

    public boolean isConfirmed() {
        return state;
    }

    public String getSportName() {
        return sportName.getText();
    }

    public int getFieldWidth() {
        return Integer.parseInt(fieldWidth.getText());
    }

    public int getFieldHeight() {
        return Integer.parseInt(fieldHeigth.getText());
    }

    public String getAccessoryPath() {
        return accessoryPath.toString();
    }

    public String getFieldPath() {
        return fieldPath.toString();
    }

    public double getAccessoryWidth() {
        return Double.parseDouble(accessoryWidth.getText());
    }

    public double getAccessoryHeight() {
        return Double.parseDouble(accessoryHeight.getText());
    }

    public ObservableList<PlayerTableItem> getPlayersData() {
        return playersData;
    }

    public ObservableList<TeamTableItem> getTeamsData() {
        return teamsData;
    }

}
