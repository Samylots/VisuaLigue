/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.gui.javafx.fxcontrollers;

import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import visualigue.domain.utils.Mode;
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

    private MainToolbarController toolbar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void init() {
        StackPane node = new StackPane();
        node.getChildren().add(new Label("No Game To Show\nClick on File -> New Game To Start!"));
        root.setCenter(node);
    }

    @FXML
    private void newGame(ActionEvent event) {
        StackPane pane = new StackPane();
        fieldLayer = pane;
        root.setCenter(pane);
        ImageView field = new ImageView(getClass().getResource("/visualigue/gui/javafx/fxlayouts/icons/field.jpg").toString());
        /*field.fitWidthProperty().bind(pane.widthProperty());
         field.fitWidthProperty().bind(pane.widthProperty());*/
        pane.getChildren().add(field);
        pane.setOnMouseClicked((MouseEvent me) -> {
            doActions(me.getX(), me.getY());
        });

        changeViewTo(Mode.FRAME_BY_FRAME);

        Node node = FXLoader.getInstance().load("mainToolbar.fxml");
        toolbar = FXLoader.getInstance().getLastController();
        root.setLeft(node);
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
        controller.refreshSports();
        CustomWindow window = new CustomWindow(root, (Parent) node);
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
        changeViewTo(Mode.FRAME_BY_FRAME);
    }

    @FXML
    private void setToMode2(ActionEvent event) {
        changeViewTo(Mode.REAL_TIME);
    }

    @FXML
    private void visualize(ActionEvent event) {
        changeViewTo(Mode.VISUALISATION);
    }

    @FXML
    private void toggleRoles(ActionEvent event) {
    }

    @FXML
    private void openOptions(ActionEvent event) {
    }

    public void changeViewTo(Mode state) {
        root.setBottom(state.getNode());
    }

    private void doActions(double x, double y) {
        if (fieldLayer != null) {
            ImageView entity;
            double realx = -(fieldLayer.getWidth() / 2) + x;
            double realy = -(fieldLayer.getHeight() / 2) + y;
            switch (toolbar.getEditMode()) {

                case ADD_ACCESSORY:
                    entity = new ImageView(new Image(getClass().getResource("/visualigue/gui/javafx/fxlayouts/icons/accessory.png").toString()));
                    entity.setFitWidth(20);
                    entity.setFitHeight(20);
                    entity.translateXProperty().set(realx);
                    entity.translateYProperty().set(realy);
                    fieldLayer.getChildren().add(entity);
                    break;
                case ADD_OBSTACLE:
                    entity = new ImageView(new Image(getClass().getResource("/visualigue/gui/javafx/fxlayouts/icons/obstacle.png").toString()));
                    entity.setFitWidth(20);
                    entity.setFitHeight(20);
                    entity.translateXProperty().set(realx);
                    entity.translateYProperty().set(realy);
                    fieldLayer.getChildren().add(entity);
                    break;
                case ADD_PLAYER:
                    entity = new ImageView(new Image(getClass().getResource("/visualigue/gui/javafx/fxlayouts/icons/player.png").toString()));
                    entity.setFitWidth(20);
                    entity.setFitHeight(20);
                    entity.translateXProperty().set(realx);
                    entity.translateYProperty().set(realy);
                    fieldLayer.getChildren().add(entity);
                    break;
                default:
                case CURSOR:
                    break;
            }
        }
    }

}
