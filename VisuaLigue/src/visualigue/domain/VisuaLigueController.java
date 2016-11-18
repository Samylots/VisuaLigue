/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.domain;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import visualigue.domain.utils.Dimension;
import visualigue.domain.game.Game;
import visualigue.domain.game.Obstacle;
import visualigue.domain.game.Sport;
import visualigue.domain.utils.Coords;
import visualigue.domain.game.Entity;
import visualigue.domain.utils.Mode;
import visualigue.exceptions.NoCurrentGameException;
import visualigue.services.exporters.GameExporter;
import visualigue.services.exporters.GameExporterFactory;
import visualigue.services.persistence.Serializer;

/**
 *
 * @author Samuel
 */
public class VisuaLigueController implements Serializable {

    private String folder;
    private Game currentGame;
    private transient Serializer serializer;
    private Ressources ressources;
    private double actualTime;
    private double frameTimeEquiv;
    private GameExporter exporter;
    private double stepTime;
    private Mode currentMode;
    private boolean showingRoles;

    public VisuaLigueController() {
        this.showingRoles = true;
        this.currentMode = Mode.FRAME_BY_FRAME;
        //this.folder = folder; //need to keep it on first save
        this.serializer = new Serializer(this);
    }

    /*
    public void createNewObstacle(String name, Coords coord, Dimension2D dimension) {
        ressources.createNewObstacle(name, coord, dimension);
    }

    public void createNewSport(String name, int limit, Dimension2D fieldDimension, Entity accessory, List<String> categories) {
        ressources.createNewSport(name, limit, fieldDimension, accessory, categories);
    }

    public void editObstacle(String oldName, String newName, Coords coord, Dimension2D dimension) {
        //TODO find obstacle by old name and edit it
    }
    */
    
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

    public List<Sport> getAvailableSports() {
        return ressources.getAvailableSports();
    }

    public List<Obstacle> getAvailableObstacles() {
        return ressources.getAvailableObstacles();
    }

    public double getActualTime() {
        return actualTime;
    }

    public double getFrameTimeEquiv() {
        return frameTimeEquiv;
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

    public Dimension getFieldDimension() {
        if (currentGame == null) {
            throw new NoCurrentGameException("There is no current game defined!");
        }
        return currentGame.getSport().getFieldDimension();
    }

}
