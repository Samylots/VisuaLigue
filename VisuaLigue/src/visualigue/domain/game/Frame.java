/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.domain.game;

import visualigue.domain.game.entities.Entity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import visualigue.domain.game.Position;
import java.util.TreeMap;
import visualigue.domain.utils.Coords;
import visualigue.domain.utils.Dimension;
import visualigue.exceptions.*;
import visualigue.domain.game.entities.Player;

/**
 *
 * @author Bruno L.L.
 */
public class Frame implements Serializable {

    private Frame next;
    private Frame back;
    //Index of TreeMap is entity Id
    private final Map<Integer, Position> positions = new TreeMap<Integer, Position>();

    public Frame getNext() {
        return next;
    }

    public Frame getBack() {
        return back;
    }
    
    public void setNext(Frame frame) {
        this.next = frame;
    }
    
    public void setBack(Frame frame) {
        this.back = frame;
    }

    public void addEntityAt(Entity entity, Coords coords) {
        positions.put(entity.getId(), new Position(coords, entity));
    }
    
    public void addEntityAt(Entity entity, Coords coords, Player player) {
        positions.put(entity.getId(), new Position(coords, entity, player));
    }

    public Map<Integer, Position> getPositions() {
        return positions;
    }
    
    public boolean hasEntity(int id) {
        return positions.containsKey(id);
    }
    
    public void removeEntity(int id) {
        if (positions.remove(id) == null) {
            throw new NoSuchIdException();
        }
    }
    
    public Entity findEntityAt(Coords coords) {
        for (Map.Entry<Integer, Position> entry : positions.entrySet()) {
            Position pos = entry.getValue();

            if (pos.isInBounds(coords)) {
                return pos.getEntity();
            }
        }
        return null;
    }
    
    public Position findCollisionAt(Entity entity, Coords coords) {
        for (Map.Entry<Integer, Position> entry : positions.entrySet()) {
            Position pos = entry.getValue();

            if (pos.collidesWith(entity, coords)) {
                return pos;
            }
        }
        return null;
    }
    
    public void movePosition(int id, Coords coords) {
        Position position = positions.get(id);
        position.setLocation(coords);
    }

    public void setOwner(int idEntity, Player owner) {
        positions.get(idEntity).setOwner(owner);
    }
}