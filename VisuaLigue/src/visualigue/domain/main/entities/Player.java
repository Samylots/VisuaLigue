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
public class Player extends Entity implements Serializable {

    private String role;
    private String name;
    private String color;
    private int number;

    public Player(Dimension dimension, String picturePath, String role, String name, String color, int teamNumber) {
        super(dimension, picturePath);
        this.role = role;
        this.name = name;
        this.color = color;
        this.number = teamNumber;
    }

    public Player(Player player) {
        super(player);
        this.role = player.role;
        this.name = player.name;
        this.color = player.color;
        this.number = player.number;
    }

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public int getNumber() {
        return number;
    }

    public void shootAccessoryTo(Player player) {
        // TODO
    }
}
