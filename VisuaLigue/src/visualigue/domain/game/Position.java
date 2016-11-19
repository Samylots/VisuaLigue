/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.domain.game;

import visualigue.domain.game.entities.Player;
import visualigue.domain.game.entities.Entity;
import java.io.Serializable;
import visualigue.domain.utils.Coords;

/**
 *
 * @author Bruno L.L.
 */
public class Position implements Serializable {

    private Coords coords;
    private Entity entity;
    private Player owner;

    public Position(Coords location, Entity entity) {
        this.coords = location;
        this.entity = entity;
    }

    public Position(Coords location, Entity entity, Player owner) {
        this.coords = location;
        this.entity = entity;
        this.owner = owner;
    }

    public boolean isInBounds(Coords coords) {
        // TODO
        return true;
    }

    public Position(Coords location) {
        this.coords = location;
    }

    public Coords getCoords() {
        return coords;
    }

    public Entity getEntity() {
        return entity;
    }

    public boolean hasOwner() {
        return owner != null;
    }

    public Player getOwner() {
        return owner;
    }

}
