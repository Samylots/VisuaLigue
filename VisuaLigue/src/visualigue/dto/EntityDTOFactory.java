/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.dto;

import visualigue.domain.main.entities.Entity;
import visualigue.domain.main.entities.Accessory;
import visualigue.domain.main.entities.Obstacle;
import visualigue.domain.main.entities.Player;
import visualigue.exceptions.NotAValidEntity;

/**
 *
 * @author Samuel
 */
public class EntityDTOFactory {

    public static EntityDTO create(Entity entity) {
        if (entity instanceof Player) {
            return new PlayerDTO((Player) entity);
        } else if (entity instanceof Obstacle) {
            return new ObstacleDTO((Obstacle) entity);
        } else if (entity instanceof Accessory) {
            return new AccessoryDTO((Accessory) entity);
        } else {
            throw new NotAValidEntity("Givent entity : " + entity.toString() + " is a valid entity!");

        }
    }

}
