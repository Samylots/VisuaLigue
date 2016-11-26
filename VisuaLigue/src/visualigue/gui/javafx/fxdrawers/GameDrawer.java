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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author Samuel
 */
public class GameDrawer {

    private final VisuaLigueBoard canvas;
    private GraphicsContext gc;

    private Image playerImage;
    private Image obstacleImage;
    private Image accessoryImage;
    private String playerPicPath = "";
    private String obstaclePicPath = "";
    private String accessoryPicPath = "";

    public GameDrawer(VisuaLigueBoard canvas) {
        this.canvas = canvas;
        gc = canvas.getGraphicsContext2D();
    }

    public void drawGame() {
        List<PositionDTO> positions = VisuaLigue.domain.getActualPositions();

        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.TOP);
        gc.setStroke(Color.BLACK);
        gc.setFill(Color.WHITE);

        positions.stream().forEach((position) -> {
            if (position.entity instanceof PlayerDTO) {
                drawEntity(getPixelPosition(position.coords), position.entity, createPlayerImage(position.entity.picturePath));
            } else if (position.entity instanceof ObstacleDTO) {
                drawEntity(getPixelPosition(position.coords), position.entity, createObstacleImage(position.entity.picturePath));
            } else if (position.entity instanceof AccessoryDTO) {
                drawEntity(getPixelPosition(position.coords), position.entity, createAccessoryImage(position.entity.picturePath));
            } else {
                //error or not?
            }
        });
    }

    private Image createPlayerImage(String picPath) {
        if (!playerPicPath.equals(picPath)) {
            playerPicPath = picPath;
            playerImage = new Image(picPath);
        }
        return playerImage;
    }

    private Image createObstacleImage(String picPath) {
        if (!obstaclePicPath.equals(picPath)) {
            obstaclePicPath = picPath;
            obstacleImage = new Image(picPath);
        }
        return obstacleImage;
    }

    private Image createAccessoryImage(String picPath) {
        if (!accessoryPicPath.equals(picPath)) {
            accessoryPicPath = picPath;
            accessoryImage = new Image(picPath);
        }
        return accessoryImage;
    }

    private void drawEntity(Coords coords, EntityDTO entity, Image img) {
        Dimension dim = getPixelDimension(entity.dimension);
        double coordsX = coords.getX();
        double coordsY = coords.getY();
        double width = dim.getWidth();
        double height = dim.getHeight();

        gc.drawImage(img, coordsX, coordsY, width, height);

        if (entity instanceof PlayerDTO && VisuaLigue.domain.isShowingRoles()) {
            PlayerDTO player = (PlayerDTO) entity;
            writeTexte(player.role, 16, coordsX + width / 2, coordsY + height);
            writeTexte(player.name, 14, coordsX + width / 2, coordsY + height + 16);
        }
    }

    private void writeTexte(String text, double size, double x, double y) {
        gc.setFont(Font.font(size));
        gc.strokeText(text, x, y);
        gc.fillText(text, x, y);
    }

    private Coords getPixelPosition(Coords domainCoords) {
        return canvas.getConverter().meterToPixel(domainCoords, canvas.getActualFieldPixelDimension());
    }

    private Dimension getPixelDimension(Dimension domainDim) {
        return canvas.getConverter().meterToPixel(domainDim, canvas.getActualFieldPixelDimension());
    }

}
