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
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import visualigue.VisuaLigue;
import visualigue.inter.dto.AccessoryDTO;
import visualigue.inter.dto.ObstacleDTO;
import visualigue.inter.dto.PlayerDTO;
import visualigue.domain.events.FramesListener;
import visualigue.domain.events.SelectionListener;
import visualigue.gui.javafx.fxcontrollers.states.LoginPaneController;
import visualigue.inter.utils.exceptions.CollisionDetectedException;
import visualigue.gui.javafx.helpers.UIMode;
import visualigue.gui.javafx.fxlayouts.CustomWindow;
import visualigue.gui.javafx.fxlayouts.Dialog;
import visualigue.gui.javafx.fxlayouts.FXLoader;
import visualigue.gui.javafx.fxlayouts.InputDialog;
import java.util.List;
import visualigue.inter.utils.Mode;

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

    private ToolBar playerToolbar;
    private PlayerToolbarController playerToolbarController;

    private ToolBar obstacleToolbar;
    private ToolBar accessoryToolbar;

    private final AddObstacleList addObstacleList = new AddObstacleList();

    private HBox addPlayerToolbar;
    private HBox addObstacleToolbar;
    private MainToolbarController mainToolbarController;

    private VisuaLigueBoard board;
    @FXML
    private CheckMenuItem maxPlayerOption;
    @FXML
    private RadioMenuItem showRoleOption;
    @FXML
    private MenuItem undoOption;
    @FXML
    private MenuItem redoOption;
    @FXML
    private RadioMenuItem frameByFrameButton;
    @FXML
    private RadioMenuItem realTimeButton;
    @FXML
    private RadioMenuItem visualisationButton;
    @FXML
    private Menu editMenu;
    @FXML
    private ToggleGroup mode;
    @FXML
    private Menu optionsMenu;
    @FXML
    private Menu viewMenu;
    @FXML
    private Menu fileMenu;
    @FXML
    private Menu ressourcesMenu;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initToolbars();

        showRoleOption.setText("Show player roles");
        showRoleOption.setSelected(VisuaLigue.domain.isShowingRoles());
        maxPlayerOption.setSelected(VisuaLigue.domain.isMaxPlayer());

        board = new VisuaLigueBoard();
        board.heightProperty().bind(root.heightProperty());
        board.setOnMouseClicked((MouseEvent e) -> handleBoardClick(e));
        board.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> trySelecting(e));
        board.addEventHandler(MouseEvent.MOUSE_RELEASED, (e) -> trySelecting(e));
        board.addEventHandler(MouseEvent.MOUSE_DRAGGED, (e) -> handleBoardMouseDrag(e));
        board.addEventHandler(MouseEvent.MOUSE_DRAGGED, (e) -> board.redraw());

        VisuaLigue.domain.addEventListener("draw", board);
        VisuaLigue.domain.addEventListener("select", this);
        resetLayout();

        //force login before anything!
        Platform.runLater(() -> { //Login form auto open
            Node node = FXLoader.getInstance().load("loginPane.fxml");
            LoginPaneController controller = FXLoader.getInstance().getLastController();
            CustomWindow window = new CustomWindow(root, (Parent) node);
            window.setTitle("Login");
            boolean wantToLogin = true;
            do { //loop if want to login
                window.showAndWait();
                controller.clear();
                if (!controller.isValidLogin()) {
                    Dialog popup = new Dialog("Login error", "Please login correctly to create a new game.", root);
                    if (!popup.isConfirmed()) { //close all if not logged
                        wantToLogin = false;
                        Platform.runLater(() -> {
                            VisuaLigue.domain.close();
                            Stage stage = (Stage) root.getScene().getWindow();
                            stage.close();
                        });
                    }
                }
            } while (!controller.isValidLogin() && wantToLogin);
        });
    }

    private void resetLayout() {
        updateMenus();
        updateUndoRedoButtons();
        StackPane node = new StackPane();
        node.getChildren().add(new Label("No Game To Show\nClick on File -> New Game To Start!"));
        root.setCenter(node);
        root.setLeft(null);
        root.setBottom(null);
    }

    private void updateUndoRedoButtons() {
        undoOption.setDisable(!VisuaLigue.domain.canUndo());
        redoOption.setDisable(!VisuaLigue.domain.canRedo());
    }

    private void initToolbars() {
        mainToolbar = (ToolBar) FXLoader.getInstance().load("mainToolbar.fxml");
        mainToolbarController = FXLoader.getInstance().getLastController();
        mainToolbarController.init(this);

        teamsPlayerToolbar = (ScrollPane) FXLoader.getInstance().load("playerChooser.fxml");
        playerChooserController = FXLoader.getInstance().getLastController();

        playerToolbar = (ToolBar) FXLoader.getInstance().load("playerToolbar.fxml");
        playerToolbarController = FXLoader.getInstance().getLastController();

        obstacleToolbar = (ToolBar) FXLoader.getInstance().load("obstacleToolbar.fxml");
        accessoryToolbar = (ToolBar) FXLoader.getInstance().load("accessoryToolbar.fxml");

        addPlayerToolbar = new HBox();
        addPlayerToolbar.getChildren().add(teamsPlayerToolbar);
        addPlayerToolbar.setPrefWidth(HBox.USE_COMPUTED_SIZE);
        addPlayerToolbar.setPrefHeight(HBox.USE_COMPUTED_SIZE);
        addPlayerToolbar.setMaxHeight(Double.MAX_VALUE);

        addObstacleToolbar = new HBox();
        addObstacleToolbar.getChildren().add(addObstacleList);
        addObstacleToolbar.setPrefWidth(HBox.USE_COMPUTED_SIZE);
        addObstacleToolbar.setPrefHeight(HBox.USE_COMPUTED_SIZE);
        addObstacleToolbar.setMaxHeight(Double.MAX_VALUE);
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
                updateUndoRedoButtons();
            }
        }
    }

    @FXML
    private void Undo(ActionEvent event) {
        VisuaLigue.domain.undo();
        updateUndoRedoButtons();
    }

    @FXML
    private void Redo(ActionEvent event) {
        VisuaLigue.domain.redo();
        updateUndoRedoButtons();
    }

    private void defaultLayout() {
        root.setCenter(board);
        showDefaultToolbar();
        changeViewTo(UIMode.FRAME_BY_FRAME);
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
        controller.init(this);
        controller.refreshObstacles();
        CustomWindow window = new CustomWindow(root, (Parent) node);
        window.setTitle("Obstacle List");
        window.showAndWait();
    }

    public void refreshObstacleList() {
        addObstacleList.refreshObstacles();
    }

    @FXML
    private void setToMode1(ActionEvent event) {
        if (VisuaLigue.domain.hasOpenedGame()) {
            frameByFrameButton.setSelected(true);
            changeViewTo(UIMode.FRAME_BY_FRAME);
            showDefaultToolbar();
        }
    }

    @FXML
    private void setToMode2(ActionEvent event) {
        if (VisuaLigue.domain.hasOpenedGame()) {
            realTimeButton.setSelected(true);
            changeViewTo(UIMode.REAL_TIME);
            showDefaultToolbar();
        }
    }

    @FXML
    private void visualize(ActionEvent event) {
        if (VisuaLigue.domain.hasOpenedGame()) {
            visualisationButton.setSelected(true);
            changeViewTo(UIMode.VISUALISATION);
            hideToolbars();
        }
    }

    @FXML
    private void toggleRoles(ActionEvent event) {
        VisuaLigue.domain.toggleRoles();
    }

    public void changeViewTo(UIMode state) {
        if (VisuaLigue.domain.getLoginUser() == 2 && state != UIMode.VISUALISATION) {
            visualisationButton.setSelected(true);
            state = UIMode.VISUALISATION;
            hideToolbars();
        }
        VisuaLigue.domain.changeMode(state.getMode()); //Telling domain that we changed mode
        TitledPane nodeView = (TitledPane) state.getNode();
        FramesListener listenerController = state.getController();
        listenerController.init(root);
        VisuaLigue.domain.addEventListener("frame", listenerController);
        root.setBottom(nodeView);
        Platform.runLater(() -> nodeView.requestLayout()); //Node height bug fixing
        board.heightProperty().bind(root.heightProperty().subtract(nodeView.heightProperty()).subtract(30));
        updateUndoRedoButtons();
        updateMenus();
    }

    private void updateMenus() {
        editMenu.setVisible(VisuaLigue.domain.hasOpenedGame() && !VisuaLigue.domain.isVisualizing());
        optionsMenu.setVisible(VisuaLigue.domain.hasOpenedGame() && !VisuaLigue.domain.isVisualizing());
        viewMenu.setVisible(VisuaLigue.domain.hasOpenedGame());

        fileMenu.setVisible(VisuaLigue.domain.getLoginUser() != 2);
        ressourcesMenu.setVisible(VisuaLigue.domain.getLoginUser() != 2);
        if (VisuaLigue.domain.getLoginUser() == 2) {
            List<MenuItem> items = viewMenu.getItems();
            for (MenuItem item : items) {
                if (item.getId().equals("frameByFrameButton") || item.getId().equals("realTimeButton")) {
                    item.setVisible(false);
                }
            }
        }
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
        Platform.runLater(() -> teamsPlayerToolbar.requestLayout()); //Node width bug fixing
    }

    public void showAddObstacleList() {
        addObstacleList.refreshObstacles();
        if (!addObstacleToolbar.getChildren().contains(mainToolbar)) {
            addObstacleToolbar.getChildren().add(0, mainToolbar);
        }
        root.setLeft(addObstacleToolbar);
        board.widthProperty().bind(root.widthProperty().subtract(addObstacleToolbar.widthProperty()));
        Platform.runLater(() -> addObstacleList.requestLayout()); //Node width bug fixing
    }

    public void hideToolbars() {
        root.setLeft(null);
        board.widthProperty().bind(root.widthProperty());
    }

    public void loadGame(int gameId) {
        VisuaLigue.domain.loadGame(gameId);
        defaultLayout();
        mainToolbarController.selectCursorMode();
        setToMode1(null);
    }

    private void handleBoardClick(MouseEvent e) {
        if (!VisuaLigue.domain.isVisualizing()) {
            if (isAddingPlayer() && e.getButton() == MouseButton.PRIMARY) {
                int playerId = playerChooserController.getSelectedPlayer();
                try {
                    VisuaLigue.domain.addPlayerAt(board.getMetersMousePosition(), playerId);
                    boolean maxPlayer = VisuaLigue.domain.getMaxPlayer();

                    if (maxPlayer) {
                        playerChooserController.disableSelectedPlayer();
                    }
                } catch (CollisionDetectedException ex) {
                    Dialog popup = new Dialog("Error", "This player can't be placed here. Please try another place!", root);
                }
            } else if (isAddingObstacle() && e.getButton() == MouseButton.PRIMARY) {
                int obstacleId = addObstacleList.getSelectedObstacle();
                if (obstacleId > 0) {
                    VisuaLigue.domain.addObstacleAt(board.getMetersMousePosition(), obstacleId);
                }
            } else if (isAddingAccessory() && e.getButton() == MouseButton.PRIMARY) {
                VisuaLigue.domain.addAccessoryAt(board.getMetersMousePosition());
            }
        }
    }

    private void trySelecting(MouseEvent e) {
        if (isInCursorMode() && e.getButton() == MouseButton.PRIMARY) {
            VisuaLigue.domain.selectEntityAt(board.getMetersMousePosition());
        }
        if (isInCursorMode() && e.getButton() == MouseButton.SECONDARY) {
            VisuaLigue.domain.selectEntityForRotationAt(board.getMetersMousePosition());
        }
    }

    private void handleBoardMouseDrag(MouseEvent e) {
        if (isInCursorMode() && e.getButton() == MouseButton.PRIMARY) {
            board.updateMouse(e);

            if (VisuaLigue.domain.hasCurrentEntity()) {
                VisuaLigue.domain.moveCurrentEntityTo(board.getMetersMousePosition());
            }
        }
        if (isInCursorMode() && e.getButton() == MouseButton.SECONDARY) {

            board.updateMouse(e);
            if (VisuaLigue.domain.hasCurrentEntity()) {
                VisuaLigue.domain.rotateCurrentEntityTo(board.getMetersMousePosition());
            }
        }
    }

    @Override
    public void nothingSelected() {
        showDefaultToolbar();
    }

    @Override
    public void playerSelected(PlayerDTO player) {
        playerToolbarController.update(player);
        root.setLeft(playerToolbar);
    }

    @Override
    public void obstacleSelected(ObstacleDTO obstacle) {
        root.setLeft(obstacleToolbar);
    }

    @Override
    public void accessorySelected(AccessoryDTO accessory) {
        root.setLeft(accessoryToolbar);
    }

    @FXML
    private void toggleMaxPlayer(ActionEvent event) {
        VisuaLigue.domain.toggleMaxPlayer();
        if (isAddingPlayer()) {
            showTeamList(); //refresh to show dummy player
        }
    }

}
