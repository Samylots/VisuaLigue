/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.domain.main.entities;

import visualigue.inter.utils.Dimension;
import java.io.Serializable;
import visualigue.inter.utils.IdGenerator;

/**
 *
 * @author Bruno L.L.
 */
public abstract class Entity implements Serializable {

    private final int id;
    private final String picturePath;
    private final Dimension dimension;

    public Entity(Dimension dimension, String picturePath) {
        id = IdGenerator.getInstance().generateId();
        this.picturePath = picturePath;
        this.dimension = dimension;
    }

    public Entity(Entity entity) {
        id = IdGenerator.getInstance().generateId();
        this.picturePath = entity.getPicturePath();
        this.dimension = entity.getDimension();
    }

    public int getId() {
        return id;
    }

    public String getPicturePath() {
        return this.picturePath;
    }

    public Dimension getDimension() {
        return this.dimension;
    }

}
