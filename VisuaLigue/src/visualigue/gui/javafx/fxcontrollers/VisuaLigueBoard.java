/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.gui.javafx.fxcontrollers;

import java.io.Serializable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import visualigue.domain.Converter;
import visualigue.domain.VisuaLigueController;
import visualigue.domain.utils.Coords;

/**
 * FXML Controller class
 *
 * @author samap
 */
public class VisuaLigueBoard extends Canvas implements Serializable {

    private static final double ZOOM_DELTA = 1.15;

    private MainWindowController parentController;

    private boolean isShowingRoles = true;

    private GraphicsContext gc = getGraphicsContext2D(); //Quicker access to it's graphic context
    private final Coords origin; //Field board's origin on board's space
    private double zoomFactor;
    private final Converter converter;
    private VisuaLigueController domain;
    //TODO change it to be the sport's game field picture (on ini, getting it from domain)
    private Image fieldPicture = new Image(getClass().getResource("/visualigue/gui/javafx/fxlayouts/icons/accessory.png").toString(), 500, 300, false, true);

    private Coords oldMousePos; //Previous recorded mouse position
    private final DoubleProperty mouseX = new SimpleDoubleProperty(); //Actual mouse X position
    private final DoubleProperty mouseY = new SimpleDoubleProperty(); //Actual mouse Y position

    VisuaLigueBoard(VisuaLigueController domainController, MainWindowController parentController) {
        super(500, 300);
        zoomFactor = 1;
        origin = new Coords();
        domain = domainController;
        this.parentController = parentController;
        converter = new Converter(domainController);
        setOnScroll((ScrollEvent event) -> {
            if (event.getDeltaY() > 0) {
                zoom(ZOOM_DELTA);
            } else {
                zoom(1 / ZOOM_DELTA);
            }
            drawAll();
        });

        setOnMouseDragged((MouseEvent event) -> {
            if (event.isSecondaryButtonDown()) {
                updateMousePos(event.getX(), event.getY());
                Coords newMousePos = getMousePosition();
                translate(newMousePos.getX() - oldMousePos.getX(), newMousePos.getY() - oldMousePos.getY());
                drawAll();
            }
        });
        setOnMouseMoved((final MouseEvent event) -> {
            updateMousePos(event);
            drawAll();
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
        zoomFactor *= delta;
        newCoords.setX(newCoords.getX() * delta);
        newCoords.setY(newCoords.getY() * delta);
        translate(oldCoords.getX() - newCoords.getX(), oldCoords.getY() - newCoords.getY());
        setMousePos((oldCoords.getX() * delta), (oldCoords.getY() * delta));
    }

    private void updateMousePos(MouseEvent event) {
        oldMousePos = getMousePosition();
        mouseX.set(event.getX() - origin.getX());
        mouseY.set(event.getY() - origin.getY());
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

    }

    /**
     * Get last recorded moude position
     *
     * @return
     */
    private Coords getMousePosition() {
        return new Coords(mouseX.doubleValue(), mouseY.doubleValue());
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
        gc.setFill(Color.WHITE);
        gc.clearRect(0 - origin.getX(), 0 - origin.getY(), this.getWidth(), this.getHeight());
    }

    /**
     * Drawing all elements on board
     */
    private void drawAll() {
        clear();
        gc.setFill(Color.CYAN);
        gc.fillRect(0, 0, fieldPicture.getWidth() * zoomFactor, fieldPicture.getHeight() * zoomFactor);
        gc.drawImage(fieldPicture, 0, 0, fieldPicture.getWidth() * zoomFactor, fieldPicture.getHeight() * zoomFactor);
        gc.setStroke(Color.RED);
        gc.setLineWidth(2);
        gc.strokeRect(0, 0, fieldPicture.getWidth() * zoomFactor, fieldPicture.getHeight() * zoomFactor);
        if (isShowingRoles) {
            drawPos();
        }
    }

    /**
     * Drawing mouse position info on board
     */
    private void drawPos() {
        gc.setLineWidth(1.5);
        gc.setStroke(Color.BLACK);
        gc.strokeText(mouseX + "," + mouseY, 10 - origin.getX(), 10 - origin.getY());
        gc.setFill(Color.WHITE);
        gc.fillText(mouseX + "," + mouseY, 10 - origin.getX(), 10 - origin.getY());
    }

    public void toggleRoles() {
        isShowingRoles = !isShowingRoles;
    }
}
