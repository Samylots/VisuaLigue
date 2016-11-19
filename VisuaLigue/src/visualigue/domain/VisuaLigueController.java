/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.domain;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import visualigue.domain.utils.Dimension;
import visualigue.domain.game.Game;
import visualigue.domain.game.entities.Obstacle;
import visualigue.domain.game.Sport;
import visualigue.domain.utils.Coords;
import visualigue.domain.game.entities.Entity;
import visualigue.domain.game.Position;
import visualigue.domain.game.Team;
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
    private final transient Serializer serializer;
    private final Ressources ressources = new Ressources();
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

    public void deleteObstacle(int obstacleId) {
        //find obstacle by id and delete it
    }

    public void createNewGame() {
        //createNewGame...
        currentGame = new Game();
    }

    public int createNewSport(String name, String fieldPath, double fieldWidth, double fielHeight, String accessoryPath, double accessoryWidth, double accessoryHeight) {
        Sport newSport = new Sport(name, fieldPath, new Dimension(fieldWidth, fielHeight), new Entity(new Dimension(accessoryWidth, accessoryHeight), accessoryPath));
        ressources.addSport(newSport);
        return newSport.getSportId();
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
        //serializer
    }

    public void redo() {
        //serializer
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

    public void toggleRoles() {
        showingRoles = !showingRoles;
    }

    public Dimension getFieldDimension() {
        if (currentGame == null) {
            throw new NoCurrentGameException("There is no current game defined!");
        }
        return currentGame.getSport().getFieldDimension();
    }

    public List<Entity> getGameEntities() {
        if (currentGame == null) {
            throw new NoCurrentGameException("There is no current game defined!");
        }
        List<Entity> entities = new ArrayList<>();
        entities.addAll(currentGame.getSport().getPlayers());
        entities.addAll(currentGame.getSport().getAccessories());
        entities.addAll(currentGame.getObstacles());
        return entities;
    }

    public List<Position> getActualPositions() {
        if (currentGame == null) {
            throw new NoCurrentGameException("There is no current game defined!");
        }
        return currentGame.getCurrentPositions();
    }

    public void addPlayerAt(Coords coords, int playerId) {
        currentGame.addPlayerAt(coords, playerId);
    }

    public void addTeamToSport(String teamName, String teamColor, int sportId) {
        ressources.getSport(sportId).addTeam(new Team(teamName, teamColor));
    }

    public void addPlayerToSportTeam(String playerName, String playerRole, String teamName, int sportId) {
        //TODO remove static player pic?
        ressources.getSport(sportId).getTeam(teamName).addPlayer("/visualigue/gui/javafx/fxlayouts/icons/player.png", playerName, playerRole);
    }

    public String getFieldPicPath() {
        return currentGame.getSport().getFieldPicturePath();
    }

    public void setSport(int sportId) {
        currentGame.setSport(ressources.getSport(sportId));
    }

}
