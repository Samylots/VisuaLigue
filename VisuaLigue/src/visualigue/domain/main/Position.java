/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.domain.main;

import java.awt.Rectangle;
import visualigue.domain.main.entities.Player;
import visualigue.domain.main.entities.Entity;
import java.io.Serializable;
import visualigue.inter.utils.Coords;
import visualigue.domain.main.entities.Accessory;
import java.lang.Math.*;

/**
 *
 * @author Bruno L.L.
 */
public class Position implements Serializable {

    private static final int COLLISION_PRECISION = 100;

    private Coords coords;
    private Entity entity;
    private Player owner;
    private Accessory owns;
    private boolean isMoved = true;
    private double direction = 0;

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

    public Position(Position pos) {
        this.coords = new Coords(pos.coords);
        this.entity = pos.entity; //same entity ref
        this.owner = pos.owner; //same ref
        this.owns = pos.owns; //same ref
        this.isMoved = false; //default
        this.direction = pos.direction;
    }

    public void setLocation(Coords coords) {
        this.coords = coords;
        centerIt();
    }

    public void setDirection(Coords coordsPointer) {
        double diffX = coordsPointer.getX() - coords.getX() - entity.getDimension().getWidth() / 2;
        double diffY = -(coordsPointer.getY() - coords.getY() - entity.getDimension().getHeight() / 2);

        double diffAngle = Math.toDegrees(Math.atan(Math.abs(diffY) / Math.abs(diffX)));

        if (diffX < 0 && diffY > 0) {
            diffAngle = 90 - diffAngle;
            diffAngle += 90;
        }
        if (diffX < 0 && diffY < 0) {
            diffAngle += 180;
        }
        if (diffX > 0 && diffY < 0) {
            diffAngle = 90 - diffAngle;
            diffAngle += 270;
        }
        this.direction = -diffAngle;
    }

    public double getDirection() {
        return direction;
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
        if (entity == this.entity || this.owner == entity || this.owns == entity) {
            return false;
        }
        Rectangle rectThis = new Rectangle((int) (this.coords.getX() * COLLISION_PRECISION),
                (int) (this.coords.getY() * COLLISION_PRECISION),
                (int) (this.entity.getDimension().getWidth() * COLLISION_PRECISION),
                (int) (this.entity.getDimension().getHeight() * COLLISION_PRECISION));
        Rectangle rectEntity = new Rectangle((int) ((coords.getX() - entity.getDimension().getWidth() / 2) * COLLISION_PRECISION),
                (int) ((coords.getY() - entity.getDimension().getHeight() / 2) * COLLISION_PRECISION),
                (int) (entity.getDimension().getWidth() * COLLISION_PRECISION),
                (int) (entity.getDimension().getHeight() * COLLISION_PRECISION));

        return rectThis.intersects(rectEntity);
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

    public Accessory getOwns() {
        return owns;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public void setOwns(Accessory owns) {
        this.owns = owns;
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
