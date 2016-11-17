/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.domain.game;

import java.io.Serializable;
import java.util.List;
import javafx.geometry.Dimension2D;
import visualigue.domain.utils.Coords;
import visualigue.domain.utils.Entity;

/**
 *
 * @author Samuel
 */
public class Player extends Entity implements Serializable {

    private String role;
    private String category;
    private Entity accessory;
    private List<Action> actions;

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

    public void shootAccessoryTo(Player player) {
        player.receiveAccessory(accessory);
        accessory = null;
    }

    public void receiveAccessory(Entity accessory) {
        this.accessory = accessory;
    }

    //Todo make private methods to find nearet Movement/Action to given time...
}
