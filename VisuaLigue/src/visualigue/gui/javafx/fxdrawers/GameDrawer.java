/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.gui.javafx.fxdrawers;

import java.awt.image.RenderedImage;
import java.util.ArrayList;
import visualigue.inter.dto.ObstacleDTO;
import visualigue.inter.dto.PlayerDTO;
import visualigue.inter.dto.PositionDTO;
import visualigue.inter.dto.AccessoryDTO;
import visualigue.inter.dto.EntityDTO;
import java.util.List;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.VPos;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import visualigue.VisuaLigue;
import visualigue.inter.utils.Coords;
import visualigue.gui.javafx.fxcontrollers.VisuaLigueBoard;
import visualigue.inter.utils.Dimension;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import visualigue.inter.utils.exceptions.CantGenerateEmptyGameException;

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
    private Image arrowImage;
    private String playerPicPath = "";
    private String obstaclePicPath = "";
    private String accessoryPicPath = "";
    private String arrowPicPath = "";

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
        boolean canDoOpacity = !VisuaLigue.domain.isVisualizing();
        if (!VisuaLigue.domain.isVisualizing()) {
            drawPositions(VisuaLigue.domain.getLastPositions(), UNMOVED_TRANSPARENCY, canDoOpacity);
        }
        drawPositions(VisuaLigue.domain.getActualPositions(), 1, canDoOpacity);
    }

    private void drawPositions(List<PositionDTO> positions, double opacity, boolean canDoOpacity) {
        Paint previousColor = Color.WHITE;
        for (PositionDTO position : positions) {
            gc.setFill(previousColor);
            double posOpacity = opacity;

            if (!position.isMoved && canDoOpacity) {
                posOpacity = UNMOVED_TRANSPARENCY;
            }
            if (VisuaLigue.domain.isCurrentEntity(position.entity.id) && canDoOpacity) {
                previousColor = gc.getFill();
                drawSelection(getPixelPosition(position.coords), position.entity);
            }
            if (position.entity instanceof PlayerDTO) {
                PlayerDTO player = (PlayerDTO) position.entity;
                drawPlayer(getPixelPosition(position.coords), player, posOpacity, position.direction);
            } else if (position.entity instanceof ObstacleDTO) {
                drawEntity(getPixelPosition(position.coords), position.entity, createObstacleImage(position.entity.picturePath), posOpacity);
            } else if (position.entity instanceof AccessoryDTO) {
                drawEntity(getPixelPosition(position.coords), position.entity, createAccessoryImage(position.entity.picturePath), posOpacity);
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

    private Image createArrowImage(String picPath) {
        if (!arrowPicPath.equals(picPath)) {
            arrowPicPath = picPath;
            arrowImage = new Image(picPath);
        }
        return arrowImage;
    }

    private Image createAccessoryImage(String picPath) {
        if (!accessoryPicPath.equals(picPath)) {
            accessoryPicPath = picPath;
            accessoryImage = new Image(picPath);
        }
        return accessoryImage;
    }

    private void drawPlayer(Coords coords, PlayerDTO entity, double opacity, double direction) {
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
        Image arrowImg = createArrowImage("visualigue/gui/javafx/fxlayouts/icons/arrow.png");

        Rotate r = new Rotate(direction, coordsX + (width / 2), coordsY + (height / 2));
        Coords origin = canvas.getOrigin();
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx() + origin.getX(), r.getTy() + origin.getY());
        gc.drawImage(arrowImg, coordsX + width, coordsY + (height / 2) - 10, 20, 20);

        gc.restore();

        if (VisuaLigue.domain.isShowingRoles() && opacity != UNMOVED_TRANSPARENCY) {
            writeText(entity.role, 16, coordsX + width / 2, coordsY + height);
            writeText(entity.name, 14, coordsX + width / 2, coordsY + height + 16);
        }
    }

    private void drawEntity(Coords coords, EntityDTO entity, Image img, double opacity) {
        Dimension dim = getPixelDimension(entity.dimension);
        gc.save();
        gc.setGlobalAlpha(opacity);
        gc.drawImage(img, coords.getX(), coords.getY(), dim.getWidth(), dim.getHeight());
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

    private void drawCompleteGame() {
        List<List<PositionDTO>> framesPositions = VisuaLigue.domain.getAllFramePositions();
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.TOP);
        gc.setStroke(Color.BLACK);
        gc.setFill(Color.WHITE);
        gc.save();
        drawTrajectories(framesPositions);
        drawPositions(framesPositions.get(0), 1, false);
        drawPositions(framesPositions.get(framesPositions.size() - 1), 1, false);
    }

    private void drawTrajectories(List<List<PositionDTO>> framesPositions) {
        List<Integer> entities = getEntitiesId(framesPositions.get(0));
        Coords actualPixelPos;
        Dimension actualPixelDim;
        PositionDTO actualPos;
        for (int actualId : entities) {
            gc.beginPath();
            actualPos = getEntityPosition(actualId, framesPositions.get(0));
            actualPixelPos = getPixelPosition(actualPos.coords);
            actualPixelDim = getPixelDimension(actualPos.entity.dimension);
            gc.moveTo(actualPixelPos.getX() + actualPixelDim.getWidth() / 2, actualPixelPos.getY() + actualPixelDim.getHeight() / 2);
            for (List<PositionDTO> positions : framesPositions) { //for each frames
                actualPos = getEntityPosition(actualId, positions);
                actualPixelPos = getPixelPosition(actualPos.coords);
                actualPixelDim = getPixelDimension(actualPos.entity.dimension);
                gc.lineTo(actualPixelPos.getX() + actualPixelDim.getWidth() / 2, actualPixelPos.getY() + actualPixelDim.getHeight() / 2);
            }
            gc.stroke();
        }
    }

    private PositionDTO getEntityPosition(int entity, List<PositionDTO> positions) {
        for (PositionDTO pos : positions) {
            if (pos.entity.id == entity) {
                return pos;
            }
        }
        return null;
    }

    private List<Integer> getEntitiesId(List<PositionDTO> framePositions) {
        List<Integer> ids = new ArrayList<>();
        for (PositionDTO pos : framePositions) {
            if (!ids.contains(pos.entity.id)) {
                ids.add(pos.entity.id);
            }
        }
        return ids;
    }

    public RenderedImage generatePreview() {
        if (VisuaLigue.domain.hasOpenedGame()) {
            canvas.saveViewState();
            gc.save();
            //draw things
            canvas.drawExportBase();
            drawCompleteGame();
            WritableImage image = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
            //canvas.snapshot(null, image);
            WritableImage snapshot = canvas.snapshot(new SnapshotParameters(), image);
            gc.restore();
            canvas.restoreViewState();
            return SwingFXUtils.fromFXImage(snapshot, null);
        }
        throw new CantGenerateEmptyGameException("There is no game opened yet");
    }

}
