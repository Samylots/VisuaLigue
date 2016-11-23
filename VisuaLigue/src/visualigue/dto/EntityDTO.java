/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.dto;

import visualigue.domain.game.entities.Player;
import visualigue.domain.utils.Dimension;
import visualigue.domain.game.entities.Entity;
import visualigue.dto.DimensionDTO;

/**
 *
 * @author Bruno L.L.
 */
public class EntityDTO {
    public int id;
    public String picturePath;
    public DimensionDTO dimension;
    
    public EntityDTO(Dimension dimension, int id, String picturePath) {
        this.id = id;
        this.picturePath = picturePath;
        this.dimension = new DimensionDTO(dimension);
    }
    
    public EntityDTO(Entity entity) {
        this.id = entity.getId();
        this.picturePath = entity.getPicturePath();
        this.dimension = new DimensionDTO(entity.getDimension());
    }
}
