/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.domain;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import javafx.geometry.Dimension2D;
import visualigue.domain.game.Action;
import visualigue.domain.game.Game;
import visualigue.domain.game.Obstacle;
import visualigue.domain.game.Sport;
import visualigue.domain.utils.Coords;
import visualigue.domain.utils.Entity;
import visualigue.domain.utils.Mode;
import visualigue.services.exporters.GameExporter;
import visualigue.services.exporters.GameExporterFactory;
import visualigue.services.persistence.Serializer;

/**
 *
 * @author Samuel
 */
public class VisuaLigueController implements Serializable {

    private String folder;
    private List<Game> games;
    private transient Serializer serializer;
    private List<Sport> availableSports;
    private List<Action> availableActions;
    private List<Obstacle> availableObstacles;
    private double actualTime;
    private double frameTimeEquiv;
    private double zoomFactor; //1 = default?
    private GameExporter exporter;
    private double stepTime;
    private Mode currentMode;
    private Game currentGame;
    private boolean showingRoles;

    public VisuaLigueController() {
        this.showingRoles = true;
        this.currentMode = Mode.FRAME_BY_FRAME;
        this.folder = folder;
        this.serializer = new Serializer(this);
    }

    public void createNewObstacle(String name, Coords coord, Dimension2D dimension) {
        availableObstacles.add(new Obstacle(name, coord, dimension));
    }

    public void createNewSport(String name, int limit, Dimension2D fieldDimension, Entity accessory, List<String> categories) {
        Entity field = new Entity(new Coords(), fieldDimension);
        availableSports.add(new Sport(name, limit, field, accessory, categories));
    }

    public void editObstacle(String oldName, String newName, Coords coord, Dimension2D dimension) {
        //TODO find obstacle by old name and edit it
    }

    public void deleteObstacle(String name) {
        //find obstacle by name and delete it
    }

    public void createNewGame() {
        //createNewGame...
    }

    public void loadGame(Game game) {
        //load game etc..
    }

    public void startNewMovement() {
        //createNewMovement and start recording
    }

    public void recordMovement(Coords coord) {
        //createNewMovement and start recording
    }

    public void stopRecordingMovement() {

    }

    public void exportGame(String path) {
        //get path extention;
        String type = path;
        exporter = GameExporterFactory.getExporter(type);
        exporter.export(new File(path));
    }

    public void startGame() {

    }

    public void pauseGame() {

    }

    public void foward() {

    }

    public void backward() {

    }

    public void stepFoward() {

    }

    public void stepBackward() {

    }

    public void configStepTime(double step) {
        this.stepTime = step;
    }

    public void changeMode(Mode mode) {
        this.currentMode = mode;
    }

    public void zoom(double factor) {
        //mous wheel factor for zoom
    }

    public void undo() {

    }

    public void redo() {

    }

    public void togglePlayerRoles() {
        this.showingRoles = !this.showingRoles;
    }

    public void click(Coords coord) {
        //faire tout les events d'un clique..?
    }

    public String getFolder() {
        return folder;
    }

    public List<Game> getGames() {
        return games;
    }

    public List<Sport> getAvailableSports() {
        return availableSports;
    }

    public List<Obstacle> getAvailableObstacles() {
        return availableObstacles;
    }

    public double getActualTime() {
        return actualTime;
    }

    public double getFrameTimeEquiv() {
        return frameTimeEquiv;
    }

    public double getZoomFactor() {
        return zoomFactor;
    }

    public double getStepTime() {
        return stepTime;
    }

    public Mode getCurrentMode() {
        return currentMode;
    }

    public boolean isShowingRoles() {
        return showingRoles;
    }

}
