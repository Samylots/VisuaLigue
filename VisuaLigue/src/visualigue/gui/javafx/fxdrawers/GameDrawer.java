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
import visualigue.utils.Dimension;

/**
 *
 * @author Samuel
 */
public class GameDrawer {

    private final VisuaLigueBoard canvas;

    public GameDrawer(VisuaLigueBoard canvas) {
        this.canvas = canvas;
    }

    public void drawGame() {
        List<PositionDTO> positions = VisuaLigue.domain.getActualPositions();

        positions.stream().forEach((position) -> {
            if (position.entity instanceof PlayerDTO) {
                drawEntity(getPixelPosition(position.coords), position.entity);
                if (VisuaLigue.domain.isShowingRoles()) {
                    //TODO draw roles/names etc.
                }
            } else if (position.entity instanceof ObstacleDTO) {
                drawEntity(getPixelPosition(position.coords), position.entity);
            } else if (position.entity instanceof AccessoryDTO) {
                drawEntity(getPixelPosition(position.coords), position.entity);
            } else {
                //error or not?
            }
        });
    }

    private void drawEntity(Coords coords, EntityDTO entity) {
        Dimension dim = canvas.getConverter().meterToPixel(entity.dimension, canvas.getActualFieldPixelDimension());
        canvas.getGraphicsContext2D().drawImage(new Image(entity.picturePath), coords.getX(), coords.getY(), dim.getWidth(), dim.getHeight());
    }

    private Coords getPixelPosition(Coords domainCoords) {
        return canvas.getConverter().meterToPixel(domainCoords, canvas.getActualFieldPixelDimension());
    }

}
