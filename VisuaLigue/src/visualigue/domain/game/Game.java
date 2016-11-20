/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.domain.game;

import visualigue.domain.game.entities.Obstacle;
import visualigue.domain.game.entities.Player;
import visualigue.domain.game.entities.Accessory;
import visualigue.domain.game.entities.Entity;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import visualigue.domain.utils.Coords;
import visualigue.exceptions.*;

/**
 *
 * @author Bruno L.L.
 */
public class Game implements Serializable {

    private Sport sport;
    private List<Obstacle> obstacles;
    private List<Accessory> accessories;
    private Frame firstFrame;
    private Frame lastFrame;
    private int totalFrames;
    private Entity currentEntity;
    private Frame currentFrame;

    public Game() {
        firstFrame = new Frame();
        lastFrame = firstFrame;
        totalFrames = 1;
        currentFrame = firstFrame;
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
    }

    public void deletePlayerAt(Coords coords) {
        Entity entity = currentFrame.findEntityAt(coords);

        if (entity != null) {
            currentFrame.removeEntity(entity.getId());
        } else {
            throw new NoEntityAtLocationException("There is no entity at this location");
        }
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
    }

    public void deleteObstacleAt(Coords coords) {
        Entity entity = currentFrame.findEntityAt(coords);

        if (entity != null) {
            currentFrame.removeEntity(entity.getId());
            obstacles.remove(entity);
        } else {
            throw new NoEntityAtLocationException("There is no entity at this location");
        }
    }

    public void selectEntityAt(Coords coords) {
        Entity entity = currentFrame.findEntityAt(coords);
        
        if (entity != null) {
            currentEntity = entity;
        }
    }

    public void addAccessoryAt(Coords coords) {
        Accessory accessory = this.sport.getAccessory();
        Position collidesWithPosition = currentFrame.findCollisionAt(accessory, coords);
        Accessory copy = new Accessory(accessory);
   
        if (collidesWithPosition != null) {
            if (collidesWithPosition.getEntity().getClass().getName() == "Player") {
                accessories.add(copy);
                currentFrame.addEntityAt(copy, collidesWithPosition.getCoords(), (Player)collidesWithPosition.getEntity());
            } else {
                throw new CollisionDetectedException("Collided with: " + collidesWithPosition.getEntity().getId());
            }
        } else {
            accessories.add(copy);
            currentFrame.addEntityAt(copy, coords);
        }
    }

    public void deleteAccessoryAt(Coords coords) {
        Entity entity = currentFrame.findEntityAt(coords);

        if (entity != null) {
            currentFrame.removeEntity(entity.getId());
            accessories.remove(entity);
        } else {
            throw new NoEntityAtLocationException("There is no entity at this location");
        }
    }

    public void moveCurrentEntityTo(Coords coords) {
        Position collidesWithPosition = currentFrame.findCollisionAt(currentEntity, coords);

        if (collidesWithPosition == null) {
            currentFrame.movePosition(currentEntity.getId(), coords);
        } else {
            throw new CollisionDetectedException("Collided with: " + collidesWithPosition.getEntity().getId());
        }
    }

    public void setSport(Sport sport) {
        this.sport = sport;
        //TODO checks team nb and positions (Field limits..!)
    }

    public Sport getSport() {
        return sport;
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }
    
    public List<Accessory> getAccessories() {
        return accessories;
    }

    public Map<Integer, Position> getCurrentPositions() {
        return currentFrame.getPositions();
    }
}
