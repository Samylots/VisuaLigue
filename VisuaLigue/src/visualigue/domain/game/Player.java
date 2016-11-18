/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.domain.game;

import java.io.Serializable;
import java.util.List;
import visualigue.domain.utils.Coords;
import visualigue.domain.game.Team;
import visualigue.domain.game.Entity;
import visualigue.domain.utils.Dimension;

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

    public void shootAccessoryTo(Player player) {
        // TODO
    }
}
