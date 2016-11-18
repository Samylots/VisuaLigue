/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.domain.game;

import java.io.Serializable;
import visualigue.domain.utils.Coords;

/**
 *
 * @author Bruno L.L.
 */
public class Position implements Serializable {
    
    private Coords location;
    private Entity entity;
    private Player owner;
    
    public boolean isInBounds(Coords coords) {
        // TODO
        return true;
    }
}
