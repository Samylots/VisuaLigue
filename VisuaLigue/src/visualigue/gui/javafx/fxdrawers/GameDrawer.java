/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.gui.javafx.fxdrawers;

import java.util.List;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import visualigue.VisuaLigue;
import visualigue.utils.Coords;
import visualigue.gui.javafx.fxcontrollers.VisuaLigueBoard;
import visualigue.dto.*;
import visualigue.utils.Dimension;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

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
        GraphicsContext gc = canvas.getGraphicsContext2D();
        double coordsX = coords.getX();
        double coordsY = coords.getY();
        double width = dim.getWidth();
        double height = dim.getHeight();
        
        gc.drawImage(new Image(entity.picturePath), coordsX, coordsY, width, height);
        
        if (entity instanceof PlayerDTO && VisuaLigue.domain.isShowingRoles()) {
            PlayerDTO player = (PlayerDTO)entity;
            
            gc.setFont(Font.font(14));
            gc.setStroke(Color.BLACK);
            gc.setFill(Color.WHITE);

            gc.strokeText(player.role, coordsX, coordsY+height);
            gc.fillText(player.role, coordsX, coordsY+height);
            
            gc.setFont(Font.font(12));
            gc.strokeText(player.name, coordsX, coordsY+height+16);
            gc.fillText(player.name, coordsX, coordsY+height+16);
        }
    }

    private Coords getPixelPosition(Coords domainCoords) {
        return canvas.getConverter().meterToPixel(domainCoords, canvas.getActualFieldPixelDimension());
    }

}
