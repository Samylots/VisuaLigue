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
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import visualigue.domain.VisuaLigueController;
import visualigue.gui.javafx.helpers.UIMode;
import visualigue.gui.javafx.fxlayouts.CustomWindow;
import visualigue.gui.javafx.fxlayouts.Dialog;
import visualigue.gui.javafx.fxlayouts.FXLoader;

/**
 * FXML Controller class
 *
 * @author Samuel
 */
public class MainWindowController implements Initializable, Serializable {

    @FXML
    private BorderPane root;

    private StackPane fieldLayer;

    private ToolBar mainToolbar;
    private MainToolbarController mainToolbarController;

    private VisuaLigueBoard board;

    private VisuaLigueController domainController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void init(VisuaLigueController domainController) {
        this.domainController = domainController;

        mainToolbar = (ToolBar) FXLoader.getInstance().load("mainToolbar.fxml");
        mainToolbarController = FXLoader.getInstance().getLastController();

        board = new VisuaLigueBoard(domainController, this);
        board.widthProperty().bind(root.widthProperty().subtract(mainToolbar.widthProperty()));
        board.heightProperty().bind(root.heightProperty());

        StackPane node = new StackPane();
        node.getChildren().add(new Label("No Game To Show\nClick on File -> New Game To Start!"));
        root.setCenter(node);
    }

    @FXML
    private void newGame(ActionEvent event) {
        //open sport selection first
        //boardController.setSport () etc.

        int sportId = chooseSport();
        if (sportId != 0) {
            domainController.createNewGame();
            domainController.setSport(sportId);

            root.setCenter(board);
            changeViewTo(UIMode.FRAME_BY_FRAME);
            root.setLeft(mainToolbar);
        } else {
            Dialog popup = new Dialog("Game creation error", "Please, choose a sport to create a new game.", root);
        }
    }

    private int chooseSport() {
        Node node = FXLoader.getInstance().load("sportList.fxml");
        SportListController controller = FXLoader.getInstance().getLastController();
        CustomWindow window = new CustomWindow(root, (Parent) node);
        controller.init(domainController, window);
        controller.refreshSports();
        window.setTitle("Choose game sport");
        window.showAndWait();
        return controller.getSelectedId();
    }

    @FXML
    private void openGame(ActionEvent event) {
        Node node = FXLoader.getInstance().load("gameList.fxml");
        GameListController controller = FXLoader.getInstance().getLastController();
        CustomWindow window = new CustomWindow(root, (Parent) node);
        window.setTitle("Game List");
        window.showAndWait();
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
        Node node = FXLoader.getInstance().load("sportList.fxml");
        SportListController controller = FXLoader.getInstance().getLastController();
        CustomWindow window = new CustomWindow(root, (Parent) node);
        controller.init(domainController, window);
        controller.refreshSports();
        window.setTitle("Sport List");
        window.showAndWait();
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
        domainController.toggleRoles();
    }

    @FXML
    private void openOptions(ActionEvent event) {
        //is there any options??
        //Hum maybe frames time option?
    }

    public void changeViewTo(UIMode state) {
        domainController.changeMode(state.getMode()); //Telling domain that we changed mode
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

}
