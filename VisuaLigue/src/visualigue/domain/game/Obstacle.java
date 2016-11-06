/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.domain.game;

import javafx.geometry.Dimension2D;
import visualigue.domain.utils.Coords;
import visualigue.domain.utils.Entity;

/**
 *
 * @author Samuel
 */
public class Obstacle extends Entity {

    String name;

    public Obstacle(String name, Coords position, Dimension2D dimensions) {
        super(position, dimensions);
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
