/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.gui.javafx.fxdrawers;

import java.util.List;
import javafx.scene.image.Image;
import visualigue.VisuaLigue;
import visualigue.utils.Coords;
import visualigue.gui.javafx.fxcontrollers.VisuaLigueBoard;
import visualigue.events.DrawListener;
import visualigue.dto.*;

/**
 *
 * @author Samuel
 */
public class GameDrawer implements DrawListener {

    private final VisuaLigueBoard canvas;

    public GameDrawer(VisuaLigueBoard canvas) {
        this.canvas = canvas;

        VisuaLigue.domain.addEventListener("draw", this);
    }

    @Override
    public void redraw() {
        drawGame();
    }

    public void drawGame() {
        List<PositionDTO> positions = VisuaLigue.domain.getActualPositions();

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
        canvas.getGraphicsContext2D().drawImage(new Image(position.entity.picturePath), position.coords.getX(), position.coords.getY(), position.entity.dimension.getWidth(), position.entity.dimension.getHeight());
        if (VisuaLigue.domain.isShowingRoles()) {
            //TODO draw roles/names etc.
        }
    }

    private void drawObstacle(PositionDTO position) {
        canvas.getGraphicsContext2D().drawImage(new Image(position.entity.picturePath), position.coords.getX(), position.coords.getY(), position.entity.dimension.getWidth(), position.entity.dimension.getHeight());
    }

    private void drawEntity(PositionDTO position) {
        canvas.getGraphicsContext2D().drawImage(new Image(position.entity.picturePath), position.coords.getX(), position.coords.getY(), position.entity.dimension.getWidth(), position.entity.dimension.getHeight());
    }

    private Coords getPixelPosition(Coords domainCoords) {
        return canvas.getConverter().meterToPixel(domainCoords, canvas.getActualFieldPixelDimension());
    }

}
