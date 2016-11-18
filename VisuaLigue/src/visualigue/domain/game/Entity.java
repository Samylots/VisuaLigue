/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.domain.game;

import visualigue.domain.utils.Dimension;
import java.io.Serializable;

/**
 *
 * @author Bruno L.L.
 */
public class Entity implements Serializable {

    private final String picturePath;
    private final Dimension dimension;

    public Entity(Dimension dimension, String picturePath) {
        this.picturePath = picturePath;
        this.dimension = dimension;
    }

    public String getPicturePath() {
        return this.picturePath;
    }

    public Dimension getDimension() {
        return this.dimension;
    }
}
