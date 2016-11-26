/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.domain.game;

import java.awt.Rectangle;
import visualigue.domain.game.entities.Player;
import visualigue.domain.game.entities.Entity;
import java.io.Serializable;
import visualigue.utils.Coords;

/**
 *
 * @author Bruno L.L.
 */
public class Position implements Serializable {

    private static final int COLLISION_PRECISION = 100;

    private Coords coords;
    private Entity entity;
    private Player owner;
    private boolean isMoved = false;

    public Position(Coords location, Entity entity) {
        this.coords = location;
        this.entity = entity;
        centerIt();
    }

    public Position(Coords location, Entity entity, Player owner) {
        this.coords = location;
        this.entity = entity;
        this.owner = owner;
        centerIt();
    }

    public void setLocation(Coords coords) {
        this.coords = coords;
        centerIt();
    }

    public boolean isInBounds(Coords coords) {
        double x = coords.getX();
        double y = coords.getY();
        double thisX = this.coords.getX();
        double thisY = this.coords.getY();
        double width = entity.getDimension().getWidth();
        double height = entity.getDimension().getHeight();

        return (x >= thisX && x <= thisX + width && y >= thisY && y <= thisY + height);
    }

    public boolean collidesWith(Entity entity, Coords coords) {
        if (entity == this.entity) {
            return false;
        }
        Rectangle rectThis = new Rectangle((int) ((this.coords.getX() + this.entity.getDimension().getWidth() / 2) * COLLISION_PRECISION),
                (int) ((this.coords.getY() + this.entity.getDimension().getHeight() / 2) * COLLISION_PRECISION),
                (int) (this.entity.getDimension().getWidth() * COLLISION_PRECISION),
                (int) (this.entity.getDimension().getHeight() * COLLISION_PRECISION));
        Rectangle rectOther = new Rectangle((int) (coords.getX() * COLLISION_PRECISION),
                (int) (coords.getY() * COLLISION_PRECISION),
                (int) (entity.getDimension().getWidth() * COLLISION_PRECISION),
                (int) (entity.getDimension().getHeight() * COLLISION_PRECISION));

        return rectThis.intersects(rectOther);
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

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public boolean IsMoved() {
        return this.isMoved;
    }

    public void setIsMoved(boolean isMoved) {
        this.isMoved = isMoved;
    }

    private void centerIt() {
        this.coords = new Coords(this.coords.getX() - (this.entity.getDimension().getWidth() / 2), this.coords.getY() - (this.entity.getDimension().getHeight() / 2));
    }

}
