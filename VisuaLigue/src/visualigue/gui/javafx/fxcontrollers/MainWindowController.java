/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.gui.javafx.fxcontrollers;

import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import visualigue.VisuaLigue;
import visualigue.dto.AccessoryDTO;
import visualigue.dto.ObstacleDTO;
import visualigue.dto.PlayerDTO;
import visualigue.events.SelectionListener;
import visualigue.gui.javafx.helpers.UIMode;
import visualigue.gui.javafx.fxlayouts.CustomWindow;
import visualigue.gui.javafx.fxlayouts.Dialog;
import visualigue.gui.javafx.fxlayouts.FXLoader;
import visualigue.gui.javafx.fxlayouts.InputDialog;

/**
 * FXML Controller class
 *
 * @author Samuel
 */
public class MainWindowController implements Initializable, Serializable, SelectionListener {

    @FXML
    private BorderPane root;

    private StackPane fieldLayer;

    private ToolBar mainToolbar;

    private ScrollPane teamsPlayerToolbar;
    private PlayerChooserController playerChooserController;

    private HBox addPlayerToolbar;
    private MainToolbarController mainToolbarController;

    private VisuaLigueBoard board;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mainToolbar = (ToolBar) FXLoader.getInstance().load("mainToolbar.fxml");
        mainToolbarController = FXLoader.getInstance().getLastController();
        mainToolbarController.init(this);

        teamsPlayerToolbar = (ScrollPane) FXLoader.getInstance().load("playerChooser.fxml");
        playerChooserController = FXLoader.getInstance().getLastController();
        addPlayerToolbar = new HBox();
        addPlayerToolbar.getChildren().add(teamsPlayerToolbar);
        addPlayerToolbar.setPrefWidth(HBox.USE_COMPUTED_SIZE);
        addPlayerToolbar.setPrefHeight(HBox.USE_COMPUTED_SIZE);
        teamsPlayerToolbar.setMaxHeight(Double.MAX_VALUE);

        board = new VisuaLigueBoard();
        board.heightProperty().bind(root.heightProperty());
        board.setOnMouseClicked((MouseEvent e) -> handleBoardClick(e));
        board.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> trySelecting(e));
        board.addEventHandler(MouseEvent.MOUSE_DRAGGED, (e) -> handleBoardMouseDrag(e));
        board.addEventHandler(MouseEvent.MOUSE_DRAGGED, (e) -> board.redraw());

        VisuaLigue.domain.addEventListener("draw", board);
        VisuaLigue.domain.addEventListener("select", this);

        StackPane node = new StackPane();
        node.getChildren().add(new Label("No Game To Show\nClick on File -> New Game To Start!"));
        root.setCenter(node);
    }

    @FXML
    private void newGame(ActionEvent event) {
        boolean wantToCreate = true;
        int sportId = 0;

        while (sportId == 0 && wantToCreate) {
            sportId = chooseSport(true);
            if (sportId == 0) {
                Dialog popup = new Dialog("Game creation error", "Please, choose a sport to create a new game.", root);
                wantToCreate = popup.isConfirmed();
            }

        }

        if (wantToCreate) {
            InputDialog input = new InputDialog("New game", "Please name your new game", root);
            if (input.isConfirmed()) {
                int gameId = VisuaLigue.domain.createNewGame(input.getInput(), sportId);
                loadGame(gameId);
            }
        }
    }

    private void initLayout() {
        root.setCenter(board);
        changeViewTo(UIMode.FRAME_BY_FRAME);
        showDefaultToolbar();
    }

    private int chooseSport(boolean isSelectable) {
        Node node = FXLoader.getInstance().load("sportList.fxml");
        SportListController controller = FXLoader.getInstance().getLastController();
        CustomWindow window = new CustomWindow(root, (Parent) node);
        controller.init(window, isSelectable);
        controller.refreshSports();
        window.setTitle("Sport List");
        window.showAndWait();
        return controller.getSelectedId();
    }

    @FXML
    private void openGame(ActionEvent event) {
        Node node = FXLoader.getInstance().load("gameList.fxml");
        GameListController controller = FXLoader.getInstance().getLastController();
        CustomWindow window = new CustomWindow(root, (Parent) node);
        controller.init(window);
        controller.refreshGames();
        window.setTitle("Game List");
        window.showAndWait();

        int gameId = controller.getSelectedId();
        if (gameId > 0) {
            if (VisuaLigue.domain.hasOpenedGame()) {
                Dialog popup = new Dialog("Game Switching", "Are you sure you wan to change current game?", root);
                if (popup.isConfirmed()) {
                    loadGame(gameId);
                }
            } else {
                loadGame(gameId);
            }
        }

        //ask if sure to change game etc..
        /*Dialog popup = new Dialog("Game Loader", "This game has been successfully loaded!", root);
         newGame(event);*/
    }

    @FXML
    private void ExportGame(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.showSaveDialog(root.getScene().getWindow());
        Dialog popup = new Dialog("Game Exportation", "This game has been successfully exported!", root);
    }

    @FXML
    private void openSportList(ActionEvent event) {
        int sportId = chooseSport(VisuaLigue.domain.hasOpenedGame());
        if (sportId > 0) {
            //TODO make sure user want to change currentGame sport
            //make changes by wiping all elements in current game but keep game name
        }
    }

    @FXML
    private void openObstacleList(ActionEvent event) {
        Node node = FXLoader.getInstance().load("obstacleList.fxml");
        ObstacleListController controller = FXLoader.getInstance().getLastController();
        controller.refreshObstacles();
        CustomWindow window = new CustomWindow(root, (Parent) node);
        window.setTitle("Obstacle List");
        window.showAndWait();
    }

    @FXML
    private void setToMode1(ActionEvent event) {
        changeViewTo(UIMode.FRAME_BY_FRAME);
    }

    @FXML
    private void setToMode2(ActionEvent event) {
        changeViewTo(UIMode.REAL_TIME);
    }

    @FXML
    private void visualize(ActionEvent event) {
        changeViewTo(UIMode.VISUALISATION);
    }

    @FXML
    private void toggleRoles(ActionEvent event) {
        VisuaLigue.domain.toggleRoles();
    }

    @FXML
    private void openOptions(ActionEvent event) {
        //is there any options??
        //Hum maybe frames time option?
    }

    public void changeViewTo(UIMode state) {
        VisuaLigue.domain.changeMode(state.getMode()); //Telling domain that we changed mode
        TitledPane nodeView = (TitledPane) state.getNode();
        root.setBottom(nodeView);
        Platform.runLater(() -> nodeView.requestLayout()); //Node height bug fixing
        board.heightProperty().bind(root.heightProperty().subtract(nodeView.heightProperty()).subtract(30));
    }

    public boolean isAddingPlayer() {
        return mainToolbarController.getEditMode() == MainToolbarController.EditMode.ADD_PLAYER;
    }

    public boolean isAddingObstacle() {
        return mainToolbarController.getEditMode() == MainToolbarController.EditMode.ADD_OBSTACLE;
    }

    public boolean isAddingAccessory() {
        return mainToolbarController.getEditMode() == MainToolbarController.EditMode.ADD_ACCESSORY;
    }

    public boolean isInCursorMode() {
        return mainToolbarController.getEditMode() == MainToolbarController.EditMode.CURSOR;
    }

    public void showDefaultToolbar() {
        board.widthProperty().bind(root.widthProperty().subtract(mainToolbar.widthProperty()));
        root.setLeft(mainToolbar);
    }

    public void showTeamList() {
        playerChooserController.refreshTeams();
        if (!addPlayerToolbar.getChildren().contains(mainToolbar)) {
            addPlayerToolbar.getChildren().add(0, mainToolbar);
        }
        root.setLeft(addPlayerToolbar);
        board.widthProperty().bind(root.widthProperty().subtract(addPlayerToolbar.widthProperty()));
        Platform.runLater(() -> addPlayerToolbar.requestLayout()); //Node width bug fixing
    }

    public void loadGame(int gameId) {
        VisuaLigue.domain.loadGame(gameId);
        initLayout();
    }

    private void handleBoardClick(MouseEvent e) {
        if (isAddingPlayer() && e.getButton() == MouseButton.PRIMARY) {
            int playerId = playerChooserController.getSelectedPlayer();
            VisuaLigue.domain.addPlayerAt(board.getConvertedMousePosition(), playerId);
            playerChooserController.disableSelectedPlayer();
        }
        trySelecting(e);
    }

    private void trySelecting(MouseEvent e) {
        if (isInCursorMode() && e.getButton() == MouseButton.PRIMARY) {
            VisuaLigue.domain.selectEntityAt(board.getConvertedMousePosition());
        }
    }

    private void handleBoardMouseDrag(MouseEvent e) {
        if (isInCursorMode() && e.getButton() == MouseButton.PRIMARY) {
            //VisuaLigue.domain.selectEntityAt(board.getConvertedMousePosition());
            board.updateMouse(e);
            if (VisuaLigue.domain.hasCurrentEntity()) {
                VisuaLigue.domain.moveCurrentEntityTo(board.getConvertedMousePosition());
            }
        }
    }

    @Override
    public void selectNothing() {
        showDefaultToolbar();
    }

    @Override
    public void selectPlayer(PlayerDTO player) {
        System.out.println("Selected Player #" + player.id);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void selectObstacle(ObstacleDTO obstacle) {
        System.out.println("Selected Obstacle #" + obstacle.id);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void selectAccessory(AccessoryDTO accessory) {
        System.out.println("Selected accessory #" + accessory.id);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
