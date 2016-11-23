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
import visualigue.events.DrawListener;
import visualigue.dto.*;

/**
 *
 * @author Samuel
 */
public class GameDrawer implements DrawListener {

    private final VisuaLigueBoard canvas;
    private final VisuaLigueController domain;

    public GameDrawer(VisuaLigueBoard canvas, VisuaLigueController domain) {
        this.canvas = canvas;
        this.domain = domain;
        
        domain.addEventListener("draw", this);
    }
    
    public void redraw() {
        drawGame();
    }

    public void drawGame() {
        List<PositionDTO> positions = domain.getActualPositions();

        for (PositionDTO position : positions) {
            if (position.entity instanceof PlayerDTO) {
                drawPlayer(position);
            } else if (position.entity instanceof ObstacleDTO) {
                drawObstacle(position);
            } else if (position.entity instanceof AccessoryDTO) {
                drawEntity(position);
            } else {
                //error or not?
            }
        };
    }

    private void drawPlayer(PositionDTO position) {
        canvas.getGraphicsContext2D().drawImage(new Image(position.entity.picturePath), position.coords.x, position.coords.y, position.entity.dimension.width, position.entity.dimension.height);
        if (domain.isShowingRoles()) {
            //TODO draw roles/names etc.
        }
    }

    private void drawObstacle(PositionDTO position) {
        canvas.getGraphicsContext2D().drawImage(new Image(position.entity.picturePath), position.coords.x, position.coords.y, position.entity.dimension.width, position.entity.dimension.height);
    }

    private void drawEntity(PositionDTO position) {
        canvas.getGraphicsContext2D().drawImage(new Image(position.entity.picturePath), position.coords.x, position.coords.y, position.entity.dimension.width, position.entity.dimension.height);
    }

    private Coords getPixelPosition(Coords domainCoords) {
        return canvas.getConverter().meterToPixel(domainCoords, canvas.getActualFieldPixelDimension());
    }

}
