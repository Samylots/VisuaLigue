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
import visualigue.domain.services.Serializer;
import java.util.Map;
import visualigue.domain.Ressources;
import visualigue.inter.utils.exceptions.CantDeleteFrameException;
import visualigue.inter.utils.exceptions.CollisionDetectedException;
import visualigue.inter.utils.exceptions.MustPlaceAllPlayersOnFieldException;
import visualigue.inter.utils.IdGenerator;
import visualigue.inter.utils.exceptions.CantActivateMaxPlayerException;

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
    private boolean showingRoles;
    private int idGenetation;
    private int loggedAs = 0;

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
        if (currentGame != null) {
            this.currentGame.setSerializer(serializer);
        }
        this.ressources = controller.ressources;
        this.frameTimeEquiv = controller.frameTimeEquiv;
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
                Game.addFrameListener((FramesListener) listener);
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
        if (!isVisualizing()) {
            serializer.saveToHistory();
        }
    }

    public void showFrame(int number) {
        if (hasOpenedGame()) {
            currentGame.goToFrame(number);
        }
    }

    public List<TeamDTO> getCurrentGameTeams() {
        List<TeamDTO> returnData = new ArrayList<>();
        List<Team> teams = currentGame.getSport().getTeams();
        List<Integer> playersOnBoard = currentGame.getPlayersOnBoard();
        List<Integer> ownersOnBoard = currentGame.getOwnersOnBoard();

        for (Team team : teams) {
            TeamDTO teamDTO = new TeamDTO(team);

            // Setting isOnBoard values
            for (PlayerDTO playerDTO : teamDTO.players) {
                if (playersOnBoard.contains(playerDTO.correspondingId)) {
                    playerDTO.isOnBoard = true;
                }
                if (ownersOnBoard.contains(playerDTO.id)) {
                    playerDTO.isOwner = true;
                }
            }
            returnData.add(teamDTO);
        }
        return returnData;
    }

    public int createNewGame(String name, int sportId) {
        boolean maxPlayer;

        if (currentGame != null) {
            maxPlayer = currentGame.isMaxPlayer();
        } else {
            maxPlayer = true;
        }
        Game newGame = new Game(name, ressources.getSport(sportId), maxPlayer, ressources.getSport(sportId).getFieldPicturePath());
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
        currentGame = ressources.getGame(gameId);
        currentGame.setSerializer(serializer);
    }

    public void unOwnAccessory() {
        currentGame.unOwnAccessory();
        serializer.saveToHistory();
    }

    public void setCurrentGamePreview(String path) {
        if (hasOpenedGame()) {
            currentGame.setPreviewImage(path);
        }
    }

    public void changeMode(Mode mode) {
        if (hasOpenedGame()) {
            currentGame.changeMode(mode);
            serializer.saveToHistory();
        }
    }

    public boolean canUndo() {
        return hasOpenedGame() && serializer.canUndo();
    }

    public boolean canRedo() {
        return hasOpenedGame() && serializer.canRedo();
    }

    public void undo() {
        if (hasOpenedGame()) {
            this.serializer.undo();
            currentGame.triggerUndoRedo();
        }
    }

    public void redo() {
        if (hasOpenedGame()) {
            this.serializer.redo();
            currentGame.triggerUndoRedo();
        }
    }

    public void togglePlayerRoles() {
        this.showingRoles = !this.showingRoles;
        serializer.saveToHistory();
    }

    public void selectEntityAt(Coords coord) {
        currentGame.selectEntityAt(coord);
    }

    public void selectEntityForRotationAt(Coords coord) {
        currentGame.selectEntityForRotationAt(coord);
    }

    public boolean getRotationAllowed() {
        return currentGame.getRotationAllowed();
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
        return hasOpenedGame() && currentGame.getCurrentMode() == Mode.VISUALISATION;
    }

    public boolean isShowingRoles() {
        return showingRoles;
    }

    public void toggleRoles() {
        showingRoles = !showingRoles;
        serializer.saveToHistory();
    }

    public void toggleMaxPlayer() throws CantActivateMaxPlayerException {
        currentGame.toggleMaxPlayer();
    }

    public boolean isMaxPlayer() {
        if (currentGame != null) {
            return currentGame.isMaxPlayer();
        }
        return true;
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

    public List<List<PositionDTO>> getAllFramePositions() {
        if (!hasOpenedGame()) {
            throw new NoCurrentGameException("There is no current game defined!");
        }
        List<List<PositionDTO>> frames = new ArrayList<>();
        List<List<Position>> framesPositions = currentGame.getAllFramesPositions();
        List<PositionDTO> framePositionsDTO;
        for (List<Position> positions : framesPositions) {
            framePositionsDTO = new ArrayList<>();
            for (Position pos : positions) {
                framePositionsDTO.add(new PositionDTO(pos));
            }
            frames.add(framePositionsDTO);
        }
        return frames;
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
        if (currentGame != null) {
            return currentGame.getSport().getFieldPicturePath();
        }
        return "";
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

    public void moveCurrentEntityTo(Coords coords) {
        if (currentGame.getCurrentMode() != Mode.VISUALISATION) { //can't edit on visualization
            currentGame.moveCurrentEntityTo(coords);
        }
    }

    public int login(String username, String password) {
        if (username.equals("joueur") && password.equals("joueur")) {
            loggedAs = 2;
        } else if (username.equals("entraineur") && password.equals("entraineur")) {
            loggedAs = 1;
        }
        return getLoginUser();
    }

    public int getLoginUser() {
        return loggedAs;
    }

    public void rotateCurrentEntityTo(Coords coords) {
        if (currentGame.getCurrentMode() != Mode.VISUALISATION) { //can't edit on visualization
            currentGame.rotateCurrentEntityTo(coords);
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
