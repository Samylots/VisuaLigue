/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.domain.main.entities;

import java.io.Serializable;
import visualigue.inter.utils.Dimension;

/**
 *
 * @author Bruno L.L.
 */
public class Obstacle extends Entity implements Serializable {

    String name;

    public Obstacle(String name, Dimension dimension, String picturePath) {
        super(dimension, picturePath);
        this.name = name;
    }

    public Obstacle(Obstacle obstacle) {
        super(obstacle);
        this.name = obstacle.getName();
    }

    public String getName() {
        return name;
    }
}
