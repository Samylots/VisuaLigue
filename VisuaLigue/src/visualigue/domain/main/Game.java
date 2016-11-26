/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.domain.main;

import visualigue.domain.main.entities.Obstacle;
import visualigue.domain.main.entities.Player;
import visualigue.domain.main.entities.Accessory;
import visualigue.domain.main.entities.Entity;
import java.io.Serializable;
import java.util.List;
import visualigue.utils.Coords;
import visualigue.exceptions.*;
import visualigue.events.*;
import java.util.Map;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import visualigue.dto.AccessoryDTO;
import visualigue.dto.ObstacleDTO;
import visualigue.dto.PlayerDTO;
import visualigue.utils.IdGenerator;

/**
 *
 * @author Bruno L.L.
 */
public class Game implements Serializable {

    // Keeping a local list of obstacles in case they are deleted in the future
    private List<Obstacle> obstacles = new ArrayList<Obstacle>();
    private List<Accessory> accessories = new ArrayList<Accessory>();
    private Sport sport;
    private Frame firstFrame;
    private Frame lastFrame;
    private int totalFrames;
    private Entity currentEntity;
    private Frame currentFrame;
    private static SelectionListener selectionListener;
    private static List<DrawListener> drawListeners = new ArrayList<>();
    private static FramesListener frameListener;
    private transient Timer playbackTimer = new Timer();
    private int id;
    private String name;

    private final int frameTimeEquiv = 2 * 1000;

    public Game(String name, Sport sport) {
        this.id = IdGenerator.getInstance().generateId();
        firstFrame = new Frame();
        lastFrame = firstFrame;
        totalFrames = 1;
        currentFrame = firstFrame;
        this.name = name;
        this.sport = sport;
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

    public void startGame() {
        playbackTimer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (currentFrame != lastFrame) {
                    currentFrame = currentFrame.getNext();
                    triggerReDraw();
                } else {
                    playbackTimer.cancel();
                }
            }
        }, frameTimeEquiv, frameTimeEquiv);
    }

    public List<Integer> getPlayersOnBoard() {
        List<Integer> returnData = new ArrayList<Integer>();

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
        playbackTimer.cancel();
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
    }

    public void addPlayerAt(Coords coords, int playerId) {
        if (currentFrame.hasEntity(playerId)) {
            throw new PlayerAlreadyOnFieldException("This player is already on the field");
        } else {
            Player playerEntity = sport.getPlayer(playerId);
            Position collidesWithPosition = currentFrame.findCollisionAt(playerEntity, coords);

            if (collidesWithPosition == null) {
                currentFrame.addEntityAt(playerEntity, coords);
            } else {
                throw new CollisionDetectedException("Collided with: " + collidesWithPosition.getEntity().getId());
            }
        }
        triggerReDraw();
    }

    public void deletePlayerAt(Coords coords) {
        Entity entity = currentFrame.findEntityAt(coords);

        if (entity != null) {
            currentFrame.removeEntity(entity.getId());
        } else {
            throw new NoEntityAtLocationException("There is no entity at this location");
        }
        triggerReDraw();
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

    public void deleteObstacleAt(Coords coords) {
        Entity entity = currentFrame.findEntityAt(coords);

        if (entity != null) {
            currentFrame.removeEntity(entity.getId());
            obstacles.remove(entity);
        } else {
            throw new NoEntityAtLocationException("There is no entity at this location");
        }
        triggerReDraw();
    }

    public void selectEntityAt(Coords coords) {
        Entity entity = currentFrame.findEntityAt(coords);
        currentEntity = entity; //can be null and it's ok
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

    public void deleteAccessoryAt(Coords coords) {
        Entity entity = currentFrame.findEntityAt(coords);

        if (entity != null) {
            currentFrame.removeEntity(entity.getId());
            accessories.remove(entity);
        } else {
            throw new NoEntityAtLocationException("There is no entity at this location");
        }
        triggerReDraw();
    }

    public void moveCurrentEntityTo(Coords coords) {
        if (currentEntity == null) {
            //Exception?
        }
        Position collidesWithPosition = currentFrame.findCollisionAt(currentEntity, coords);

        if (collidesWithPosition == null) {
            currentFrame.movePosition(currentEntity.getId(), coords);

        } else {
            Entity collidedWithEntity = collidesWithPosition.getEntity();

            if (currentEntity instanceof Accessory && collidedWithEntity instanceof Player) {
                currentFrame.movePosition(currentEntity.getId(), collidesWithPosition.getCoords());
                currentFrame.setOwner(currentEntity.getId(), (Player) collidedWithEntity);
            }
            if (currentEntity instanceof Player && collidedWithEntity instanceof Accessory) {
                currentFrame.movePosition(currentEntity.getId(), coords);
                currentFrame.movePosition(collidedWithEntity.getId(), coords);
                currentFrame.setOwner(collidedWithEntity.getId(), (Player) currentEntity);
            }
            //throw new CollisionDetectedException("Collided with: " + collidesWithPosition.getEntity().getId());
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
            Map<Integer, Position> positions = currentFrame.getPositions();

            for (Player player : sport.getPlayers()) {
                if (!positions.containsKey(player.getId())) {
                    throw new MustPlaceAllPlayersOnFieldException("You have to place all players on the field before creating a new image");
                }
            }
        }
        totalFrames++;
        Frame newFrame = new Frame(lastFrame);
        lastFrame.setNext(newFrame);
        newFrame.setBack(lastFrame);
        lastFrame = newFrame;
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
        return currentEntity.getId() == id;
    }

    private void triggerSelection() {
        if (selectionListener != null) {
            if (currentEntity == null) {
                selectionListener.selectNothing();
            } else if (currentEntity instanceof Player) {
                selectionListener.selectPlayer(new PlayerDTO((Player) currentEntity));
            } else if (currentEntity instanceof Obstacle) {
                selectionListener.selectObstacle(new ObstacleDTO((Obstacle) currentEntity));
            } else if (currentEntity instanceof Accessory) {
                selectionListener.selectAccessory(new AccessoryDTO((Accessory) currentEntity));
            }
        }
    }
}
