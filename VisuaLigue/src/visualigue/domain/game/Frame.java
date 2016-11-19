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

/**
 *
 * @author Bruno L.L.
 */
public class Frame implements Serializable {

    private Frame next;
    private Frame back;
    //Index of TreeMap is entity Id
    private final Map<Integer, Position> positions = new TreeMap<>();

    public Frame getNext() {
        return next;
    }

    public Frame getBack() {
        return back;
    }

    public void addEntityAt(Entity entity, Coords coords) {
        positions.put(entity.getId(), new Position(coords, entity));
    }

    public List<Position> getPositions() {
        List<Position> positionsList = new ArrayList<>();
        positions.entrySet().stream().forEach((position) -> {
            positionsList.add(position.getValue());
        });
        return positionsList;
    }

}
