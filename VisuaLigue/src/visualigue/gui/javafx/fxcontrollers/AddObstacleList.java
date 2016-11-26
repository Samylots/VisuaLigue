/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.gui.javafx.fxcontrollers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import visualigue.VisuaLigue;
import visualigue.inter.dto.ObstacleDTO;

/**
 *
 * @author Samuel
 */
public class AddObstacleList extends ScrollPane {

    private final VBox obstaclesContainer = new VBox();

    private int currentObstacle;

    private final List<ToggleButton> buttons = new ArrayList<>();

    public AddObstacleList() {
        setFitToWidth(true);
        setFitToHeight(true);
        obstaclesContainer.setPrefWidth(USE_COMPUTED_SIZE);
        obstaclesContainer.setPrefHeight(USE_COMPUTED_SIZE);
        setContent(obstaclesContainer);
    }

    public void refreshObstacles() {
        buttons.clear();
        obstaclesContainer.getChildren().clear();
        List<ObstacleDTO> obstacles = VisuaLigue.domain.getAvailableObstacles();
        obstacles.stream().forEach((obstacle) -> {
            addObstacleButton(obstacle);
        });
        if (buttons.size() > 0) {
            currentObstacle = obstacles.get(0).id;
            buttons.get(0).setSelected(true);
            buttons.get(0).requestFocus();
        }
    }

    private void addObstacleButton(ObstacleDTO obstacle) {
        ImageView pic = new ImageView(obstacle.picturePath);
        pic.setFitWidth(40);
        pic.setFitHeight(40);
        Tooltip tip = new Tooltip(obstacle.name);
        ToggleButton button = new ToggleButton("", pic);
        button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        button.setTooltip(tip);
        button.setOnAction((ActionEvent e) -> {
            currentObstacle = obstacle.id;
            unToggleOthers((ToggleButton) e.getSource());
        });
        buttons.add(button);
        obstaclesContainer.getChildren().add(button);
    }

    private void unToggleOthers(ToggleButton source) {
        for (ToggleButton button : buttons) {
            if (source != button) {
                button.setSelected(false);
            }
        }
    }

    public int getSelectedObstacle() {
        return currentObstacle;
    }

}
