/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.inter.controller;

import visualigue.domain.events.FramesListener;
import visualigue.domain.events.Listener;
import visualigue.domain.events.SelectionListener;
import visualigue.domain.events.DrawListener;
import visualigue.inter.dto.ObstacleDTO;
import visualigue.inter.dto.PositionDTO;
import visualigue.inter.dto.PlayerDTO;
import visualigue.inter.dto.SportDTO;
import visualigue.inter.dto.TeamDTO;
import visualigue.inter.dto.AccessoryDTO;
import visualigue.inter.dto.GameDTO;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import visualigue.inter.utils.Dimension;
import visualigue.domain.main.Game;
import visualigue.domain.main.entities.Obstacle;
import visualigue.domain.main.Sport;
import visualigue.inter.utils.Coords;
import visualigue.domain.main.entities.Accessory;
import visualigue.domain.main.Position;
import visualigue.domain.main.Team;
import visualigue.inter.utils.Mode;
import visualigue.inter.utils.exceptions.NoCurrentGameException;
import visualigue.domain.services.exporters.GameExporter;
import visualigue.domain.services.exporters.GameExporterFactory;
import visualigue.domain.services.Serializer;
import java.util.Map;
import visualigue.domain.Ressources;
import visualigue.inter.utils.exceptions.CantDeleteFrameException;
import visualigue.inter.utils.exceptions.CollisionDetectedException;
import visualigue.inter.utils.exceptions.MustPlaceAllPlayersOnFieldException;
import visualigue.inter.utils.IdGenerator;

/**
 *
 * @author Samuel
 */
public class VisuaLigueController implements Serializable {

    private String folder;
    private Game currentGame;
    private final transient Serializer serializer;
    private Ressources ressources = new Ressources();
    private double frameTimeEquiv;
    private GameExporter exporter;
    private boolean showingRoles;
    private int idGenetation;

    public VisuaLigueController() {
        this.showingRoles = true;
        //this.folder = folder; //need to keep it on first save
        this.serializer = new Serializer(this);
        this.serializer.loadFromFile();
        this.currentGame = null;
    }

    public void copy(VisuaLigueController controller) {
        this.folder = controller.folder;
        this.currentGame = controller.currentGame;
        if (currentGame != null)
            this.currentGame.setSerializer(serializer);
        this.ressources = controller.ressources;
        this.frameTimeEquiv = controller.frameTimeEquiv;
        this.exporter = controller.exporter;
        this.showingRoles = controller.showingRoles;
        this.idGenetation = controller.idGenetation;
    }

    public void addEventListener(String event, Listener listener) {
        switch (event) {
            case "draw":
                Game.addDrawListener((DrawListener) listener);
                break;
            case "select":
                Game.setSelectionListener((SelectionListener) listener);
                break;
            case "frame":
                Game.setFrameListener((FramesListener) listener);
                ((FramesListener) listener).updateFrames(); //init
                break;
        }
    }

    public void startGame() {
        currentGame.startGame();
    }

    public void pauseGame() {
        currentGame.pauseGame();
    }

    public void goToFrame(int number) {
        int currentFrame = currentGame.getActualFrameNumber();
        int maxFrame = currentGame.getTotalFrames();
        if (currentFrame + number <= 0) {
            currentGame.goToFrame(1);
        } else if (currentFrame + number > maxFrame) {
            currentGame.goToFrame(maxFrame);
        } else {
            currentGame.goToFrame(currentFrame + number);
        }
        serializer.saveToHistory();
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

    public int createNewObstacle(String name, String path, double width, double height) {
        Obstacle newObstacle = new Obstacle(name, new Dimension(width, height), path);
        ressources.addObstacle(newObstacle);
        return newObstacle.getId();
    }

    public void loadGame(int gameId) {
        //TODO check for saving?
        currentGame = ressources.getGame(gameId);
        currentGame.setSerializer(serializer);
    }

    public void exportGame(String path) {
        //get path extention;
        String type = path;
        exporter = GameExporterFactory.getExporter(type);
        exporter.export(new File(path));
    }

    public void changeMode(Mode mode) {
        currentGame.changeMode(mode);
        serializer.saveToHistory();
    }

    public void undo() {
        this.serializer.undo();
        currentGame.triggerUndoRedo();
    }

    public void redo() {
        this.serializer.redo();
        currentGame.triggerUndoRedo();
    }

    public void togglePlayerRoles() {
        this.showingRoles = !this.showingRoles;
        serializer.saveToHistory();
    }

    public void selectEntityAt(Coords coord) {
        currentGame.selectEntityAt(coord);
    }

    public void unSelectCurrentEntity() {
        currentGame.unSelectCurrentEntity();
        serializer.saveToHistory();
    }

    public String getFolder() {
        return folder;
    }

    public List<SportDTO> getAvailableSports() {
        List<SportDTO> returnData = new ArrayList<>();
        List<Sport> sports = ressources.getAvailableSports();

        sports.stream().forEach((sport) -> {
            returnData.add(new SportDTO(sport));
        });

        return returnData;
    }

    public List<ObstacleDTO> getAvailableObstacles() {
        List<ObstacleDTO> returnData = new ArrayList<>();
        List<Obstacle> obstacles = ressources.getAvailableObstacles();

        obstacles.stream().forEach((obstacle) -> {
            returnData.add(new ObstacleDTO(obstacle));
        });

        return returnData;
    }

    public boolean sportHasGames(int sportId) {
        return ressources.getGames().stream().anyMatch((game) -> (game.getSport().getId() == sportId));
    }

    public List<GameDTO> getAvailableGames() {
        List<Game> games = ressources.getGames();
        List<GameDTO> returnData = new ArrayList<>();

        games.stream().forEach((game) -> {
            returnData.add(new GameDTO(game));
        });

        return returnData;
    }

    public double getActualFrame() {
        return currentGame.getActualFrameNumber();
    }

    public double getTotalFrame() {
        return currentGame.getTotalFrames();
    }

    public Mode getCurrentMode() {
        return currentGame.getCurrentMode();
    }

    public boolean isVisualizing() {
        return currentGame.getCurrentMode() == Mode.VISUALISATION;
    }

    public boolean isShowingRoles() {
        return showingRoles;
    }

    public void toggleRoles() {
        showingRoles = !showingRoles;
        serializer.saveToHistory();
    }

    public void toggleMaxPlayer() {
        currentGame.toggleMaxPlayer();
    }

    public boolean isMaxPlayer() {
        return currentGame.isMaxPlayer();
    }

    public Dimension getFieldDimension() {
        if (!hasOpenedGame()) {
            throw new NoCurrentGameException("There is no current game defined!");
        }
        return new Dimension(currentGame.getSport().getFieldDimension());
    }

    public AccessoryDTO getGameAccessrory() {
        return new AccessoryDTO(currentGame.getSport().getAccessory());
    }

    public List<PositionDTO> getActualPositions() {
        if (!hasOpenedGame()) {
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

    public List<PositionDTO> getLastPositions() {
        if (!hasOpenedGame()) {
            throw new NoCurrentGameException("There is no current game defined!");
        }
        List<PositionDTO> returnData = new ArrayList<>();
        Map<Integer, Position> lastPositions = currentGame.getLastPositions();

        for (Map.Entry<Integer, Position> entry : lastPositions.entrySet()) {
            int id = entry.getKey();
            Position pos = entry.getValue();

            returnData.add(new PositionDTO(pos));
        }
        return returnData;
    }

    public void addPlayerAt(Coords coords, int playerId) throws CollisionDetectedException {
        if (playerId > 0) {
            currentGame.addPlayerAt(coords, playerId);
            serializer.saveToHistory();
        }
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

    public void newFrame() throws MustPlaceAllPlayersOnFieldException {
        currentGame.newFrame();
        serializer.saveToHistory();
    }

    public void deleteCurrentFrame() throws CantDeleteFrameException {
        currentGame.deleteCurrentFrame();
    }

    public void nextFrame() {
        currentGame.nextFrame();
        serializer.saveToHistory();
    }

    public void previousFrame() {
        currentGame.previousFrame();
        serializer.saveToHistory();
    }

    // Remplacer par une seule fonction deleteCurrentEntity?
    public void deleteCurrentEntity() {
        currentGame.deleteCurrentEntity();
        serializer.saveToHistory();
    }

    public void addObstacleAt(Coords coords, int obstacleId) {
        currentGame.addObstacleAt(ressources.getObstacle(obstacleId), coords);
        serializer.saveToHistory();
    }

    public void addAccessoryAt(Coords coords) {
        currentGame.addAccessoryAt(coords);
        serializer.saveToHistory();
    }
    
    public void movementCompleted() {
        serializer.saveToHistory();
    }

    public void moveCurrentEntityTo(Coords coords) {
        if (currentGame.getCurrentMode() != Mode.VISUALISATION) { //can't edit on visualization
            currentGame.moveCurrentEntityTo(coords);
        }
    }

    public boolean hasOpenedGame() {
        return currentGame != null;
    }

    public void saveIdGeneration() {
        idGenetation = IdGenerator.getInstance().getLastId();
    }

    public void restoreIdGeneration() {
        IdGenerator.getInstance().restoreId(idGenetation);
    }

    public boolean hasCurrentEntity() {
        return currentGame.hasCurrentEntity();
    }

    public boolean isCurrentEntity(int id) {
        return currentGame.isCurrentEntity(id);
    }

    public void deleteGame(int id) {
        ressources.deleteGame(id);
    }

    public void deleteSport(int id) {
        ressources.deleteSport(id);
    }

    public void deleteObstacle(int id) {
        ressources.deleteObstacle(id);
    }
}
