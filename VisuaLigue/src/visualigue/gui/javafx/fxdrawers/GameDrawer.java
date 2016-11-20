/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.gui.javafx.fxdrawers;

import java.util.List;
import java.util.Map;
import javafx.scene.image.Image;
import visualigue.domain.VisuaLigueController;
import visualigue.domain.game.entities.Entity;
import visualigue.domain.game.entities.Obstacle;
import visualigue.domain.game.entities.Player;
import visualigue.domain.game.Position;
import visualigue.domain.utils.Coords;
import visualigue.domain.utils.Dimension;
import visualigue.gui.javafx.fxcontrollers.VisuaLigueBoard;
import java.util.HashMap;

/**
 *
 * @author Samuel
 */
public class GameDrawer {

    private final VisuaLigueBoard canvas;
    private final VisuaLigueController domain;

    public GameDrawer(VisuaLigueBoard canvas, VisuaLigueController domain) {
        this.canvas = canvas;
        this.domain = domain;
    }

    public void drawGame() {
        List<HashMap<String, Object>> positions = domain.getActualPositions();

        for (HashMap<String, Object> position : positions) {
            String type = (String)position.get("type");
        
            if (type == "Player") {
                drawPlayer(position);
            } else if (type == "Obstacle") {
                drawObstacle(position);
            } else if (type == "Accessory") {
                drawEntity(position);
            } else {
                //error or not?
            }
        };
    }

    private void drawPlayer(HashMap<String, Object> position) {
        canvas.getGraphicsContext2D().drawImage(new Image((String)position.get("picturePath")), (double)position.get("x"), (double)position.get("y"), (double)position.get("width"), (double)position.get("height"));
        if (domain.isShowingRoles()) {
            //TODO draw roles/names etc.
        }
    }

    private void drawObstacle(HashMap<String, Object> position) {
        canvas.getGraphicsContext2D().drawImage(new Image((String)position.get("picturePath")), (double)position.get("x"), (double)position.get("y"), (double)position.get("width"), (double)position.get("height"));
    }

    private void drawEntity(HashMap<String, Object> position) {
        canvas.getGraphicsContext2D().drawImage(new Image((String)position.get("picturePath")), (double)position.get("x"), (double)position.get("y"), (double)position.get("width"), (double)position.get("height"));
    }

    private Coords getPixelPosition(Coords domainCoords) {
        return canvas.getConverter().meterToPixel(domainCoords, canvas.getActualFieldDimension());
    }

}
