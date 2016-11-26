/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.gui.javafx.fxcontrollers;

import java.io.Serializable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import visualigue.VisuaLigue;
import visualigue.events.DrawListener;
import visualigue.utils.Converter;
import visualigue.utils.Coords;
import visualigue.gui.javafx.fxdrawers.GameDrawer;
import visualigue.utils.Dimension;

/**
 * FXML Controller class
 *
 * @author samap
 */
public class VisuaLigueBoard extends Canvas implements Serializable, DrawListener {

    private static final double ZOOM_DELTA = 1.15;
    private static final double MINIMAP_MAX_WIDTH = 100;
    private static final double MINIMAP_MAX_HEIGHT = 60;

    private boolean isOnBoard = false;

    private final GraphicsContext gc = getGraphicsContext2D(); //Quicker access to it's graphic context
    private final Coords origin; //Field board's origin on board's space
    private double zoomFactor;
    private final Converter converter;
    private final GameDrawer drawer;

    private Image fieldPicture;
    private String actualFieldPictureURL = "";

    private Coords oldMousePos; //Previous recorded mouse position
    private final DoubleProperty mouseX = new SimpleDoubleProperty(); //Actual mouse X position
    private final DoubleProperty mouseY = new SimpleDoubleProperty(); //Actual mouse Y position

    VisuaLigueBoard() {
        super(500, 300);
        zoomFactor = 1;
        origin = new Coords();
        drawer = new GameDrawer(this);
        converter = new Converter();
        setOnScroll((ScrollEvent event) -> {
            if (event.getDeltaY() > 0) {
                zoom(ZOOM_DELTA);
            } else {
                zoom(1 / ZOOM_DELTA);
            }
            drawAll();
        });

        setOnMouseDragged((MouseEvent event) -> {
            if (event.isSecondaryButtonDown() && isOnBoard) {
                updateMouse(event);
                Coords newMousePos = getMousePosition();
                translate(newMousePos.getX() - oldMousePos.getX(), newMousePos.getY() - oldMousePos.getY());
            }
        });
        setOnMouseEntered((MouseEvent e) -> {
            isOnBoard = true;
        });
        setOnMouseExited((MouseEvent e) -> {
            isOnBoard = false;
        });
        setOnMouseMoved((final MouseEvent event) -> {
            if (isOnBoard) {
                updateMousePos(event);
                drawAll();
            }
        });
    }

    /**
     * Zooming in/ou board in view
     *
     * @param delta
     */
    private void zoom(double delta) {
        Coords oldCoords = getMousePosition();
        Coords newCoords = getMousePosition();
        if (zoomFactor * delta < 27000) {
            zoomFactor *= delta;
            newCoords.setX(newCoords.getX() * delta);
            newCoords.setY(newCoords.getY() * delta);
            translate(oldCoords.getX() - newCoords.getX(), oldCoords.getY() - newCoords.getY());
            setMousePos((oldCoords.getX() * delta), (oldCoords.getY() * delta));
        }
    }

    private void updateMousePos(MouseEvent event) {
        oldMousePos = getMousePosition();
        mouseX.set(event.getX() - origin.getX());
        mouseY.set(event.getY() - origin.getY());
    }

    public void updateMouse(MouseEvent e) {
        updateMousePos(e.getX(), e.getY());
    }

    private void updateMousePos(double x, double y) {
        mouseX.set(x - origin.getX());
        mouseY.set(y - origin.getY());
    }

    /**
     * This is used while zooming. The Scroll event can't update the mouse
     * position. We have to update it manually.
     *
     * @param x
     * @param y
     */
    private void setMousePos(double x, double y) {
        mouseX.set(x);
        mouseY.set(y);
        oldMousePos = getMousePosition();

    }

    /**
     * Get last recorded moude position
     *
     * @return
     */
    private Coords getMousePosition() {
        return new Coords(mouseX.doubleValue(), mouseY.doubleValue());
    }

    public Coords getConvertedMousePosition() {
        return converter.pixelToMeter(getMousePosition(), getActualFieldPixelDimension());
    }

    /**
     * This let us to change board's origin in MainWindow
     *
     * @param x
     * @param y
     */
    private void translate(double x, double y) {
        gc.translate(x, y);
        origin.setX(origin.getX() + x);
        origin.setY(origin.getY() + y);
    }

    /**
     * Clearing the board to draw again on a clean board
     */
    private void clear() {
        gc.clearRect(0 - origin.getX(), 0 - origin.getY(), this.getWidth(), this.getHeight());
    }

    @Override
    public synchronized void redraw() {
        drawAll();
    }

    /**
     * Drawing all elements on board
     */
    private void drawAll() {
        clear();
        String fieldPic = VisuaLigue.domain.getFieldPicPath();
        if (!actualFieldPictureURL.equals(fieldPic)) {
            actualFieldPictureURL = fieldPic;
            fieldPicture = new Image(fieldPic);
        }
        drawField();
        drawer.drawGame();
        drawPos();
        drawMiniMap();
    }

    private void drawField() {
        gc.drawImage(fieldPicture, 0, 0, getActualFieldWidth(), getActualFieldHeight());
    }

    /**
     * Drawing mouse position info on board in real proportions
     */
    private void drawPos() {

        gc.setTextAlign(TextAlignment.LEFT);
        gc.setTextBaseline(VPos.BOTTOM);

        gc.setFont(Font.font(20));
        gc.setLineWidth(3);
        gc.setStroke(Color.BLACK);
        Coords coords = getConvertedMousePosition();
        //Coords coords = getMousePosition();
        gc.strokeText("X: " + coords.getX() + " m, Y: " + coords.getY() + " m", -origin.getX(), getHeight() - origin.getY());
        gc.setFill(Color.WHITE);
        gc.fillText("X: " + coords.getX() + " m, Y: " + coords.getY() + " m", -origin.getX(), getHeight() - origin.getY());
    }

    /**
     * Drawing minimap of actual view of game field on board
     */
    private void drawMiniMap() {
        double widthReduction = fieldPicture.getWidth() / MINIMAP_MAX_WIDTH;
        double heigtReduction = fieldPicture.getHeight() / MINIMAP_MAX_HEIGHT;
        double reduction;
        reduction = Math.max(widthReduction, heigtReduction);
        double finalWidth = fieldPicture.getWidth() / reduction;
        double finalHeight = fieldPicture.getHeight() / reduction;
        gc.setFill(Color.WHITE);
        gc.fillRect(0 - origin.getX(), 0 - origin.getY(), finalWidth, finalHeight);
        gc.drawImage(fieldPicture, 0 - origin.getX(), 0 - origin.getY(), finalWidth, finalHeight);
        drawViewOnMinimap(reduction);
    }

    private void drawViewOnMinimap(double reduction) {
        double viewWidth = fieldPicture.getWidth() / reduction;
        double viewHeight = fieldPicture.getHeight() / reduction;
        double leftHiddenZone = -(origin.getX() / reduction / zoomFactor);
        double topHiddenZone = -(origin.getY() / reduction / zoomFactor);
        Coords viewOrigin = origin.invert(); //Undo board translation to always print it at top left
        //view width
        if (!isRightBoundInView()) {
            double rightHiddenZone = -((getWidth() - (getActualFieldWidth() + origin.getX())) / zoomFactor / reduction);
            viewWidth -= rightHiddenZone;
        }
        //view height
        if (!isBottomBoundInView()) {
            double bottomHiddenZone = -((getHeight() - (getActualFieldHeight() + origin.getY())) / zoomFactor / reduction);
            viewHeight -= bottomHiddenZone;
        }

        if (!isLeftBoundInView()) {
            viewWidth -= leftHiddenZone;
            viewOrigin.addX(leftHiddenZone);
        }
        if (!isTopBoundInView()) {
            viewHeight -= topHiddenZone;
            viewOrigin.addY(topHiddenZone);
        }
        gc.setStroke(Color.RED);
        gc.setLineWidth(2);
        gc.strokeRect(viewOrigin.getX(), viewOrigin.getY(), viewWidth, viewHeight);
    }

    private boolean isLeftBoundInView() {
        return origin.getX() > 0;
    }

    private boolean isTopBoundInView() {
        return origin.getY() > 0;
    }

    private boolean isRightBoundInView() {
        return getActualFieldWidth() + origin.getX() < getWidth();
    }

    private boolean isBottomBoundInView() {
        return getActualFieldHeight() + origin.getY() < getHeight();
    }

    private double getActualFieldWidth() {
        return fieldPicture.getWidth() * zoomFactor;
    }

    private double getActualFieldHeight() {
        return fieldPicture.getHeight() * zoomFactor;
    }

    public Dimension getActualFieldPixelDimension() {
        return converter.pixelToDimension(getActualFieldWidth(), getActualFieldHeight());
    }

    public Converter getConverter() {
        return converter;
    }

    public double getZoomFactor() {
        return zoomFactor;
    }

}
