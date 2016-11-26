/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.dto;

import visualigue.domain.main.entities.Obstacle;
import visualigue.utils.Dimension;
import visualigue.dto.EntityDTO;

/**
 *
 * @author Bruno L.L.
 */
public class ObstacleDTO extends EntityDTO {

    public String name;
    
    public ObstacleDTO(Obstacle obstacle) {
        super(obstacle.getDimension(), obstacle.getId(), obstacle.getPicturePath());
        this.name = obstacle.getName();
    }
}
