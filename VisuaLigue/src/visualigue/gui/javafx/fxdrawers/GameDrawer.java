/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.gui.javafx.fxdrawers;

import visualigue.inter.dto.ObstacleDTO;
import visualigue.inter.dto.PlayerDTO;
import visualigue.inter.dto.PositionDTO;
import visualigue.inter.dto.AccessoryDTO;
import visualigue.inter.dto.EntityDTO;
import java.util.List;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import visualigue.VisuaLigue;
import visualigue.inter.utils.Coords;
import visualigue.gui.javafx.fxcontrollers.VisuaLigueBoard;
import visualigue.inter.utils.Dimension;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author Samuel
 */
public class GameDrawer {

    private final static double UNMOVED_TRANSPARENCY = 0.3;
    private final static double SELECTION_OFFSET = 5;
    private final static Color SELECTION_COLOR = Color.DEEPSKYBLUE;

    private final VisuaLigueBoard canvas;
    private final GraphicsContext gc;

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
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.TOP);
        gc.setStroke(Color.BLACK);
        gc.setFill(Color.WHITE);
        gc.save();
        List<PositionDTO> positions = VisuaLigue.domain.getActualPositions();
        drawPositions(positions, 1);
        if (!VisuaLigue.domain.isVisualizing()) {
            positions = VisuaLigue.domain.getLastPositions();
            drawPositions(positions, UNMOVED_TRANSPARENCY);
        }

    }

    private void drawPositions(List<PositionDTO> positions, double opactity) {
        for (PositionDTO position : positions) {
            if (!position.isMoved && !VisuaLigue.domain.isVisualizing()) {
                opactity = UNMOVED_TRANSPARENCY;
            }
            if (VisuaLigue.domain.isCurrentEntity(position.entity.id)) {
                drawSelection(getPixelPosition(position.coords), position.entity);
            }
            if (position.entity instanceof PlayerDTO) {
                PlayerDTO player = (PlayerDTO) position.entity;
                drawPlayer(getPixelPosition(position.coords), player, opactity);
            } else if (position.entity instanceof ObstacleDTO) {
                drawEntity(getPixelPosition(position.coords), position.entity, createObstacleImage(position.entity.picturePath), 1); //Always there even if it don't move
            } else if (position.entity instanceof AccessoryDTO) {
                drawEntity(getPixelPosition(position.coords), position.entity, createAccessoryImage(position.entity.picturePath), opactity);
            } else {
                //error or not?
            }
        }
    }

    private void drawSelection(Coords coords, EntityDTO entity) {
        Dimension dim = getPixelDimension(entity.dimension);
        gc.setFill(SELECTION_COLOR);
        gc.fillOval(coords.getX() - SELECTION_OFFSET, coords.getY() - SELECTION_OFFSET, dim.getWidth() + SELECTION_OFFSET * 2, dim.getHeight() + SELECTION_OFFSET * 2);
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

    private void drawPlayer(Coords coords, PlayerDTO entity, double opacity) {
        Dimension dim = getPixelDimension(entity.dimension);
        double coordsX = coords.getX();
        double coordsY = coords.getY();
        double width = dim.getWidth();
        double height = dim.getHeight();

        gc.save();
        gc.setGlobalAlpha(opacity);
        gc.setStroke(Color.BLACK);
        gc.setFill(Color.web(entity.color));
        gc.fillOval(coordsX, coordsY, width, height);
        gc.strokeOval(coordsX, coordsY, width, height);

        if (opacity != UNMOVED_TRANSPARENCY) {
            gc.setTextAlign(TextAlignment.CENTER);
            gc.setTextBaseline(VPos.CENTER);
            gc.setStroke(Color.BLACK);
            gc.setFill(Color.WHITE);
            writeText("#" + String.valueOf(entity.number), 16, coordsX + width / 2, coordsY + height / 2);
        }
        gc.restore();

        if (VisuaLigue.domain.isShowingRoles() && opacity != UNMOVED_TRANSPARENCY) {
            writeText(entity.role, 16, coordsX + width / 2, coordsY + height);
            writeText(entity.name, 14, coordsX + width / 2, coordsY + height + 16);
        }
    }

    private void drawEntity(Coords coords, EntityDTO entity, Image img, double opacity) {
        Dimension dim = getPixelDimension(entity.dimension);
        double coordsX = coords.getX();
        double coordsY = coords.getY();
        double width = dim.getWidth();
        double height = dim.getHeight();

        gc.save();
        gc.setGlobalAlpha(opacity);
        gc.drawImage(img, coordsX, coordsY, width, height);
        gc.restore();
    }

    private void writeText(String text, double size, double x, double y) {
        gc.setFont(Font.font(size));
        gc.strokeText(text, x, y);
        gc.fillText(text, x, y);
    }

    private Coords getPixelPosition(Coords domainCoords) {
        return canvas.getPixelCoords(domainCoords);
    }

    private Dimension getPixelDimension(Dimension domainDim) {
        return canvas.getPixelDimension(domainDim);
    }

}
