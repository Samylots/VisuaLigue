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
public class Accessory extends Entity implements Serializable {
    
    public Accessory(Dimension dimension, String picturePath) {
        super(dimension, picturePath);
    }
    
    public Accessory(Accessory accessory) {
        super(accessory);
    }
}
