/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.inter.dto;

import visualigue.domain.main.entities.Player;
import visualigue.inter.utils.Dimension;
import visualigue.domain.main.entities.Entity;

/**
 *
 * @author Bruno L.L.
 */
public class EntityDTO {
    public int id;
    public String picturePath;
    public Dimension dimension;
    
    public EntityDTO(Dimension dimension, int id, String picturePath) {
        this.id = id;
        this.picturePath = picturePath;
        this.dimension = dimension;
    }
    
    public EntityDTO(Entity entity) {
        this.id = entity.getId();
        this.picturePath = entity.getPicturePath();
        this.dimension = entity.getDimension();
    }
}
