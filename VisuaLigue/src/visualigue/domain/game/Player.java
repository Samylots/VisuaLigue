/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.domain.game;

import java.util.List;
import javafx.geometry.Dimension2D;
import visualigue.domain.utils.Coords;
import visualigue.domain.utils.Entity;

/**
 *
 * @author Samuel
 */
public class Player extends Entity {

    private String role;
    private String category;
    private Entity accessory;
    private List<Action> actions;
    private List<Movement> movements;

    public Player(Coords position, Dimension2D dimensions) {
        super(position, dimensions);
        accessory = null;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void addAction(String name, double start, double duration) {
        actions.add(new Action(name, start, duration));
    }

    public Action getCurrentAction(double time) {
        if (actions.isEmpty()) {
            return null; //of throw exception?
        }
        return actions.get(0);
    }

    public void deleteAction(double time) {
        //Todo find nearest Action at this time
    }

    public void addMovement(Movement movement) {
        movements.add(movement); //TODO do i need to check is a movemnt à this time exists?
    }

    public Movement getCurrentMovement(double time) {
        if (movements.isEmpty()) {
            return null; //of throw exception?
        }
        return movements.get(0);
    }

    public void deleteMovement(double time) {
        //Todo find nearest Movement at this time
    }

    public void shootAccessoryTo(Player player) {
        player.receiveAccessory(accessory);
        accessory = null;
    }

    public void receiveAccessory(Entity accessory) {
        this.accessory = accessory;
    }

    //Todo make private methods to find nearet Movement/Action to given time...
}
