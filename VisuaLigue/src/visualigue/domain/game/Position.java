/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.domain.game;

import visualigue.domain.game.entities.Player;
import visualigue.domain.game.entities.Entity;
import java.io.Serializable;
import visualigue.utils.Coords;

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
    
    public void setLocation(Coords coords) {
        this.coords = coords;
    }

    public boolean isInBounds(Coords coords) {
        double x = coords.getX();
        double y = coords.getY();
        double thisX = this.coords.getX();
        double thisY = this.coords.getY();
        double width = entity.getDimension().getWidth();
        double height = entity.getDimension().getHeight();
        
        if (x >= thisX - width/2 
            && x <= thisX + width/2
            && y >= thisY - height/2
            && y <= thisY + height/2) {

            return true;
        }
        return false;
    }
    
    public boolean collidesWith(Entity entity, Coords coords) {
        double thisX = this.coords.getX();
        double thisY = this.coords.getY();
        double thisWidth = this.entity.getDimension().getWidth();
        double thisHeight = this.entity.getDimension().getHeight();

        double x = coords.getX();
        double y = coords.getY();
        double width = entity.getDimension().getWidth();
        double height = entity.getDimension().getHeight();
        
        if ((x - width/2 >= thisX - thisWidth/2 
            && x - width/2 <= thisX + thisWidth/2
            && y + height/2 >= thisY - thisHeight/2
            && y + height/2 <= thisY + thisHeight/2)
            ||
            (x + width/2 >= thisX - thisWidth/2 
            && x + width/2 <= thisX + thisWidth/2
            && y + height/2 >= thisY - thisHeight/2
            && y + height/2 <= thisY + thisHeight/2)
            ||    
            (x - width/2 >= thisX - thisWidth/2 
            && x - width/2 <= thisX + thisWidth/2
            && y - height/2 >= thisY - thisHeight/2
            && y - height/2 <= thisY + thisHeight/2)
            ||    
            (x + width/2 >= thisX - thisWidth/2 
            && x + width/2 <= thisX + thisWidth/2
            && y - height/2 >= thisY - thisHeight/2
            && y - height/2 <= thisY + thisHeight/2)){

            return true;
        }
        return false;
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

}
