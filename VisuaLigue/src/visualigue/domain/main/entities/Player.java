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

    public Player(Dimension dimension, String picturePath, String role, String name) {
        super(dimension, picturePath);
        this.role = role;
        this.name = name;
    }

    public Player(Player player) {
        super(player);
        this.role = player.getRole();
        this.name = player.getName();
    }

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public void shootAccessoryTo(Player player) {
        // TODO
    }
}
