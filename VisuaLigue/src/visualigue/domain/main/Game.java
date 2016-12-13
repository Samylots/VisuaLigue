/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.domain.main;

import visualigue.inter.utils.exceptions.CollisionDetectedException;
import visualigue.inter.utils.exceptions.CantDeleteFrameException;
import visualigue.inter.utils.exceptions.MustPlaceAllPlayersOnFieldException;
import visualigue.inter.utils.exceptions.PlayerAlreadyOnFieldException;
import visualigue.domain.events.FramesListener;
import visualigue.domain.events.SelectionListener;
import visualigue.domain.events.DrawListener;
import visualigue.domain.main.entities.Obstacle;
import visualigue.domain.main.entities.Player;
import visualigue.domain.main.entities.Accessory;
import visualigue.domain.main.entities.Entity;
import java.io.Serializable;
import java.util.List;
import visualigue.inter.utils.Coords;
import java.util.Map;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import javafx.application.Platform;
import visualigue.inter.controller.VisuaLigueController;
import visualigue.inter.dto.AccessoryDTO;
import visualigue.inter.dto.ObstacleDTO;
import visualigue.inter.dto.PlayerDTO;
import visualigue.inter.utils.IdGenerator;
import visualigue.inter.utils.Mode;
import visualigue.domain.services.Serializer;

/**
 *
 * @author Bruno L.L.
 */
public class Game implements Serializable {

    // Keeping a local list of obstacles in case they are deleted in the future
    private final List<Obstacle> obstacles = new ArrayList<>();
    private final List<Accessory> accessories = new ArrayList<>();
    private final List<Player> players = new ArrayList<>();
    private final Sport sport;
    private Frame firstFrame;
    private Frame lastFrame;
    private int totalFrames;
    private Entity currentEntity;
    private Frame currentFrame;
    private static SelectionListener selectionListener;
    private static final List<DrawListener> drawListeners = new ArrayList<>();
    private static FramesListener frameListener;
    private transient Timer playbackTimer = new Timer();
    private transient Timer recordingTimer;
    private int id;
    private String name;
    private boolean maxPlayer = true;
    private Mode currentMode;
    private boolean movementMade;
    private boolean createNextFrame;
    private transient Serializer serializer;
    private Coords lastSelectionPosition;
    private boolean rotationAllowed = false;

    private transient boolean isTimerRunning = false;

    private final int frameTimeEquiv = 2 * 100;

    public Game(String name, Sport sport, boolean maxPlayer) {
        this.id = IdGenerator.getInstance().generateId();
        firstFrame = new Frame();
        lastFrame = firstFrame;
        totalFrames = 1;
        currentFrame = firstFrame;
        this.name = name;
        this.sport = sport;
        this.currentMode = Mode.FRAME_BY_FRAME;
        this.maxPlayer = maxPlayer;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getActualFrameNumber() {
        int frame = 1;
        Frame frameIndex = firstFrame;
        while (frameIndex.getNext() != null && frameIndex != currentFrame) {
            frame++;
            frameIndex = frameIndex.getNext();
        }
        return frame;
    }
    
    public void changeMode(Mode mode) {
        this.currentMode = mode;
    }
    
    public Mode getCurrentMode() {
        return this.currentMode;
    }

    public int getTotalFrames() {
        return totalFrames;
    }

    public static void addDrawListener(DrawListener listener) {
        drawListeners.add(listener);
    }

    public static void setFrameListener(FramesListener listener) {
        frameListener = listener;
    }

    public static void setSelectionListener(SelectionListener listener) {
        selectionListener = listener;
    }
    
    public void triggerUndoRedo() {
        triggerReDraw();
        triggerFrameUpdate();
        triggerSelection();
    }
    
    public void setSerializer(Serializer serializer) {
        this.serializer = serializer;
    }

    public void startGame() {
        if (currentFrame == lastFrame) {
            currentFrame = firstFrame;
        }
        if (playbackTimer == null) {
            playbackTimer = new Timer();
        }
        playbackTimer.cancel(); //allow another start
        playbackTimer = new Timer();
        playbackTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runVisualisation();
            }
        }, 0, frameTimeEquiv);
        isTimerRunning = true;
    }

    private void runVisualisation() {
        if (currentFrame != lastFrame) {
            Platform.runLater(() -> {
                currentFrame = currentFrame.getNext();
                triggerReDraw();
                triggerFrameUpdate();
            });
        } else {
            isTimerRunning = false;
            playbackTimer.cancel(); //allow another start
        }
    }

    public List<Integer> getPlayersOnBoard() {
        List<Integer> returnData = new ArrayList<>();

        for (Map.Entry<Integer, Position> entry : currentFrame.getPositions().entrySet()) {
            Position pos = entry.getValue();
            Entity entity = pos.getEntity();
            if (entity instanceof Player) {
                returnData.add(entity.getId());
            }
        }
        return returnData;
    }

    public void pauseGame() {
        if (isTimerRunning) {
            playbackTimer.cancel();
            triggerFrameUpdate();
        }
    }

    public void goToFrame(int number) {
        Frame frameIt = firstFrame;

        int i = 1;
        do {
            if (number == i) {
                currentFrame = frameIt;
                break;
            }
            frameIt = frameIt.getNext();
            ++i;
        } while (frameIt != lastFrame);
        
        triggerReDraw();
        triggerFrameUpdate();
    }

    public void addPlayerAt(Coords coords, int playerId) throws CollisionDetectedException {
        if (maxPlayer && currentFrame.hasEntity(playerId)) {
            throw new PlayerAlreadyOnFieldException("This player is already on the field");
        } else {
            Player playerEntity = sport.getPlayer(playerId);
            Position collidesWithPosition = currentFrame.findCollisionAt(playerEntity, coords);

            if (collidesWithPosition == null) {
                Player copy = new Player(playerEntity);
                currentFrame.addEntityAt(copy, coords);
            } else {
                throw new CollisionDetectedException("Collided with: " + collidesWithPosition.getEntity().getId());
            }
        }
        triggerReDraw();
    }
    
    public boolean getMaxPlayer() {
        return maxPlayer;
    }

    public void deleteCurrentEntity() {
        if (currentEntity instanceof Accessory){
            currentFrame.setOwner(currentEntity.getId(), null);
        }
        if (currentEntity instanceof Player){
            currentFrame.setOwns(currentEntity.getId(), null);
        }
        currentFrame.removeEntity(currentEntity.getId());
        currentEntity = null;
        triggerSelection();
    }

    public void addObstacleAt(Obstacle obstacle, Coords coords) {
        Obstacle copy = new Obstacle(obstacle);
        Position collidesWithPosition = currentFrame.findCollisionAt(copy, coords);

        if (collidesWithPosition == null) {
            obstacles.add(copy);
            currentFrame.addEntityAt(copy, coords);
        } else {
            throw new CollisionDetectedException("Collided with: " + collidesWithPosition.getEntity().getId());
        }
        triggerReDraw();
    }

    public void selectEntityAt(Coords coords) {
        Entity entity = currentFrame.findEntityAt(coords);
        
        int previousEntity = 0;
        if (currentEntity != null) {
            previousEntity = currentEntity.getId();
        }
        currentEntity = entity; //can be null and it's ok

        // entity has changed
        if ((entity == null && previousEntity != 0) || (entity != null && previousEntity != entity.getId())) {
            serializer.saveToHistory();
            
            if (entity != null) {
                lastSelectionPosition = currentFrame.getPositions().get(entity.getId()).getCoords();
            }
        } else {
            // if coords have changed, movement was made, so save to history
            if (entity != null) {
                Coords currCoords = currentFrame.getPositions().get(entity.getId()).getCoords();
                if (currCoords.getX() != lastSelectionPosition.getX() && currCoords.getY() != lastSelectionPosition.getY()) {
                    serializer.saveToHistory();
                    lastSelectionPosition = currCoords;
                }
            }
        }
        triggerSelection();
    }
    
    public void selectEntityForRotationAt(Coords coords) {
        Entity entity = currentFrame.findEntityAt(coords);
        
        if (entity != null && entity.getId() == currentEntity.getId()) {
            rotationAllowed = true;
        } else {
            rotationAllowed = false;
            serializer.saveToHistory();
        }
    }

    public void unSelectCurrentEntity() {
        currentEntity = null;
        
        if (this.currentMode == Mode.REAL_TIME) {
            goToFrame(1);
        }
        triggerSelection();
    }

    public void addAccessoryAt(Coords coords) {
        Accessory accessory = this.sport.getAccessory();
        Position collidesWithPosition = currentFrame.findCollisionAt(accessory, coords);
        Accessory copy = new Accessory(accessory);
        if (collidesWithPosition != null) {
            if (collidesWithPosition.getEntity() instanceof Player) {
                accessories.add(copy);
                currentFrame.addEntityAt(copy, collidesWithPosition.getCoords(), (Player) collidesWithPosition.getEntity());
            } else {
                throw new CollisionDetectedException("Collided with: " + collidesWithPosition.getEntity().getId());
            }
        } else {
            accessories.add(copy);
            currentFrame.addEntityAt(copy, coords);
        }
        triggerReDraw();
    }
    
    public void rotateCurrentEntityTo(Coords coords) {
        if (rotationAllowed && currentEntity instanceof Player) {
            currentFrame.rotateCurrentEntityTo(currentEntity.getId(), coords);
            triggerReDraw();
        }
    }
    
    public boolean getRotationAllowed() {
        return rotationAllowed;
    }

    public void moveCurrentEntityTo(Coords coords) {
        if (this.currentMode == Mode.REAL_TIME) {
            movementMade = true;
            
            if (recordingTimer == null) {
                recordingTimer = new Timer();
                
                recordingTimer.schedule(new TimerTask() {
                    public void run() {
                        if (movementMade != false) {
                            createNextFrame = true;
                            movementMade = false;
                        } else {
                            recordingTimer.cancel();
                            recordingTimer = null;
                        }
                    }
                }, 0, 500);
            }
        }
        Position collidesWithPosition = currentFrame.findCollisionAt(currentEntity, coords);

        if (collidesWithPosition == null) {
            currentFrame.movePosition(currentEntity.getId(), coords);

            if (currentEntity instanceof Player) {
                Accessory owns = currentFrame.getOwns(currentEntity.getId());
                if (owns != null) {
                    currentFrame.movePosition(owns.getId(), coords);
                }
            } else if (currentEntity instanceof Accessory) {
                Player owner = currentFrame.getOwner(currentEntity.getId());
                if (owner != null) {
                    currentFrame.movePosition(owner.getId(), coords);
                }
            }
            if (this.currentMode == Mode.REAL_TIME && createNextFrame) {
                nextFrame();
                createNextFrame = false;
            }
        } else {
            Entity collidedWithEntity = collidesWithPosition.getEntity();
 
            if (currentEntity instanceof Accessory 
                && collidedWithEntity instanceof Player 
                && currentFrame.getOwns(collidedWithEntity.getId()) == null
                && currentFrame.getOwner(currentEntity.getId()) == null) {
                
                currentFrame.movePosition(collidesWithPosition.getEntity().getId(), coords);
                currentFrame.setOwner(currentEntity.getId(), (Player)collidedWithEntity);
                
                if (this.currentMode == Mode.REAL_TIME && createNextFrame) {
                    nextFrame();
                    createNextFrame = false;
                }
            }
            if (currentEntity instanceof Player 
                && collidedWithEntity instanceof Accessory 
                && currentFrame.getOwns(currentEntity.getId()) == null
                && currentFrame.getOwner(collidedWithEntity.getId()) == null) {
                
                currentFrame.movePosition(currentEntity.getId(), coords);
                currentFrame.movePosition(collidedWithEntity.getId(), coords);
                currentFrame.setOwner(collidedWithEntity.getId(), (Player)currentEntity);
                
                if (this.currentMode == Mode.REAL_TIME && createNextFrame) {
                    nextFrame();
                    createNextFrame = false;
                }
            }
            currentEntity = null; //Deselect it if collision?
            triggerSelection();
        }
        
        triggerReDraw();
    }

    public Sport getSport() {
        return sport;
    }

    public Map<Integer, Position> getCurrentPositions() {
        return currentFrame.getPositions();
    }

    public Map<Integer, Position> getLastPositions() {
        if (currentFrame.getBack() != null) {
            return currentFrame.getBack().getPositions();
        } else {
            return new TreeMap<>();
        }
    }

    public void newFrame() throws MustPlaceAllPlayersOnFieldException {
        if (currentFrame == firstFrame && totalFrames == 1) {
            if (currentFrame.getTotalPlayer() < 1) {
                throw new MustPlaceAllPlayersOnFieldException("You have to place at least one players on the field before creating a new image");
            }
        }
        totalFrames++;
        Frame newFrame = new Frame(currentFrame);
        if (currentFrame.getNext() != null) {
            newFrame.setNext(currentFrame.getNext());
            currentFrame.getNext().setBack(newFrame);
        } else {
            lastFrame = newFrame;
        }
        currentFrame.setNext(newFrame);
        newFrame.setBack(currentFrame);
        currentFrame = newFrame;

        triggerReDraw();
        triggerFrameUpdate();
    }

    public void deleteCurrentFrame() throws CantDeleteFrameException {
        if (currentFrame == firstFrame && totalFrames == 1) {
            throw new CantDeleteFrameException("Can't delete frame if there's only one");
        }
        --totalFrames;

        if (currentFrame == firstFrame) {
            currentFrame.getNext().setBack(null);
            firstFrame = currentFrame.getNext();
            currentFrame = currentFrame.getNext();
        } else if (currentFrame == lastFrame) {
            currentFrame.getBack().setNext(null);
            lastFrame = currentFrame.getBack();
            currentFrame = currentFrame.getBack();
        } else {
            currentFrame.getBack().setNext(currentFrame.getNext());
            currentFrame.getNext().setBack(currentFrame.getBack());
            currentFrame = currentFrame.getNext();
        }
        triggerReDraw();
        triggerFrameUpdate();
    }

    public void nextFrame() throws MustPlaceAllPlayersOnFieldException {
        if (currentFrame.getNext() != null) {
            currentFrame = currentFrame.getNext();

            triggerReDraw();
            triggerFrameUpdate();
        } else {
            newFrame();
        }
    }

    public void previousFrame() {
        if (currentFrame.getBack() != null) {
            currentFrame = currentFrame.getBack();
            triggerReDraw();
            triggerFrameUpdate();
        }
    }

    private void triggerReDraw() {
        drawListeners.stream().forEach((listener) -> listener.redraw());
    }

    private void triggerFrameUpdate() {
        frameListener.updateFrames();
    }

    public List<Accessory> getAccessories() {
        return accessories;
    }

    public boolean hasCurrentEntity() {
        return currentEntity != null;
    }

    public boolean isCurrentEntity(int id) {
        if (currentEntity != null) {
            return currentEntity.getId() == id;
        }
        return false;
    }

    private void triggerSelection() {
        if (selectionListener != null) {
            if (currentEntity == null) {
                selectionListener.nothingSelected();
            } else if (currentEntity instanceof Player) {
                selectionListener.playerSelected(new PlayerDTO((Player) currentEntity));
            } else if (currentEntity instanceof Obstacle) {
                selectionListener.obstacleSelected(new ObstacleDTO((Obstacle) currentEntity));
            } else if (currentEntity instanceof Accessory) {
                selectionListener.accessorySelected(new AccessoryDTO((Accessory) currentEntity));
            }
        }
        triggerReDraw();
    }

    public boolean isMaxPlayer() {
        return maxPlayer;
    }

    public void toggleMaxPlayer() {
        this.maxPlayer = !this.maxPlayer;
    }
}
