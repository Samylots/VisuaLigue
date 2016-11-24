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
import visualigue.utils.Dimension;
import visualigue.domain.game.Game;
import visualigue.domain.game.entities.Obstacle;
import visualigue.domain.game.Sport;
import visualigue.utils.Coords;
import visualigue.domain.game.entities.Accessory;
import visualigue.domain.game.Position;
import visualigue.domain.game.Team;
import visualigue.utils.Mode;
import visualigue.exceptions.NoCurrentGameException;
import visualigue.services.exporters.GameExporter;
import visualigue.services.exporters.GameExporterFactory;
import visualigue.services.persistence.Serializer;
import java.util.Map;
import visualigue.dto.*;
import visualigue.events.*;
import visualigue.gui.javafx.fxlayouts.Dialog;

/**
 *
 * @author Samuel
 */
public class VisuaLigueController implements Serializable {

    private String folder;
    private Game currentGame;
    private final transient Serializer serializer;
    private Ressources ressources = new Ressources();
    private double actualTime;
    private double frameTimeEquiv;
    private GameExporter exporter;
    private Mode currentMode;
    private boolean showingRoles;

    public VisuaLigueController() {
        this.showingRoles = true;
        this.currentMode = Mode.FRAME_BY_FRAME;
        //this.folder = folder; //need to keep it on first save
        this.serializer = new Serializer(this);
        
        this.serializer.loadFromFile();
    }
    
    public void copy(VisuaLigueController controller) {
        this.folder = controller.folder;
        this.currentGame = controller.currentGame;
        this.ressources = controller.ressources;
        this.actualTime = controller.actualTime;
        this.frameTimeEquiv = controller.frameTimeEquiv;
        this.exporter = controller.exporter;
        this.currentMode = controller.currentMode;
        this.showingRoles = controller.showingRoles;
    }
    
    public void addEventListener(String event, Listener listener) {
        if (event.equals("draw")) {
            Game.addDrawListener((DrawListener)listener);
        }
    }
    
    public void startGame() {
        currentGame.pauseGame();
    }
    
    public void pauseGame() {
        currentGame.startGame();
    }
    
    public void goToFrame(int number) {
        currentGame.goToFrame(number);
    }
    
    public List<TeamDTO> getCurrentGameTeams() {  
        List<TeamDTO> returnData = new ArrayList<>();
        List<Team> teams = currentGame.getSport().getTeams();
        List<Integer> playersOnBoard = currentGame.getPlayersOnBoard();
        
        for (Team team : teams) {
            TeamDTO teamDTO = new TeamDTO(team);
            
            // Setting isOnBoard values
            for (PlayerDTO playerDTO : teamDTO.players) {
                if (playersOnBoard.contains(playerDTO.id)) {
                    playerDTO.isOnBoard = true;
                }
            }
            returnData.add(teamDTO);
        }
        return returnData;
    }

    public void deleteObstacle(int obstacleId) {
        ressources.deleteObstacle(obstacleId);
    }
    
    public int createNewGame(String name, int sportId) {
        Game newGame = new Game(name, ressources.getSport(sportId));
        ressources.addGame(newGame);
        return newGame.getId();
    }

    public int createNewSport(String name, String fieldPath, double fieldWidth, double fielHeight, String accessoryPath, double accessoryWidth, double accessoryHeight) {
        Sport newSport = new Sport(name, fieldPath, new Dimension(fieldWidth, fielHeight), new Accessory(new Dimension(accessoryWidth, accessoryHeight), accessoryPath));
        ressources.addSport(newSport);
        return newSport.getId();
    }

    public void loadGame(int gameId) {
        //TODO check for saving?
        currentGame = ressources.getGame(gameId);
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

    public void foward() {

    }

    public void backward() {

    }

    public void stepFoward() {

    }

    public void stepBackward() {

    }

    public void changeMode(Mode mode) {
        this.currentMode = mode;
    }

    public void undo() {
        this.serializer.undo();
    }

    public void redo() {
        this.serializer.redo();
    }

    public void togglePlayerRoles() {
        this.showingRoles = !this.showingRoles;
    }

    public void selectEntityAt(Coords coord) {
        currentGame.selectEntityAt(coord);
    }

    public String getFolder() {
        return folder;
    }

    public List<SportDTO> getAvailableSports() {
        List<SportDTO> returnData = new ArrayList<SportDTO>();
        List<Sport> sports = ressources.getAvailableSports();
        
        sports.stream().forEach((sport) -> {
            returnData.add(new SportDTO(sport));
        });
        
        return returnData;
    }

    public List<ObstacleDTO> getAvailableObstacles() {
        List<ObstacleDTO> returnData = new ArrayList<ObstacleDTO>();
        List<Obstacle> obstacles = ressources.getAvailableObstacles();
        
        obstacles.stream().forEach((obstacle) -> {
            returnData.add(new ObstacleDTO(obstacle));
        });
        
        return returnData;
    }
    
    public List<GameDTO> getAvailableGames() {
        List<Game> games = ressources.getGames();
        List<GameDTO> returnData = new ArrayList<GameDTO>();
        
        games.stream().forEach((game) -> {
            returnData.add(new GameDTO(game));
        });
        
        return returnData;
    }

    public double getActualTime() {
        return actualTime;
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
        return new Dimension(currentGame.getSport().getFieldDimension());
    }

    public AccessoryDTO getGameAccessrory() {
        return new AccessoryDTO(currentGame.getSport().getAccessory());
    }

    public List<PositionDTO> getActualPositions() {
        if (currentGame == null) {
            throw new NoCurrentGameException("There is no current game defined!");
        }
        List<PositionDTO> returnData = new ArrayList<>();
        Map<Integer, Position> currentPositions = currentGame.getCurrentPositions();
        
        for (Map.Entry<Integer, Position> entry : currentPositions.entrySet()) {
            int id = entry.getKey();
            Position pos = entry.getValue();

            returnData.add(new PositionDTO(pos));
        }
        return returnData;
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

    public void close() {
        this.serializer.saveToFile();
    }
    
    public void newFrame() {
        currentGame.newFrame();
    }
    
    public void deleteCurrentFrame() {
        currentGame.deleteCurrentFrame();
    }
    
    public void nextFrame() {
        currentGame.nextFrame();
    }
    
    public void previousFrame() {
        currentGame.previousFrame();
    }

    // Remplacer par une seule fonction deleteCurrentEntity?
    public void deletePlayerAt(Coords coords) {
        currentGame.deleteAccessoryAt(coords);
    }

    public void addObstacleAt(Obstacle obstacle, Coords coords) {
        currentGame.addObstacleAt(obstacle, coords);
    }

    public void deleteObstacleAt(Coords coords) {
        currentGame.deleteObstacleAt(coords);
    }

    public void addAccessoryAt(Coords coords) {
        currentGame.addAccessoryAt(coords);
    }

    public void deleteAccessoryAt(Coords coords) {
        currentGame.deleteAccessoryAt(coords);
    }

    public void moveCurrentEntityTo(Coords coords) {
        currentGame.moveCurrentEntityTo(coords);
    }
}
