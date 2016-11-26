/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.domain.events;

import visualigue.inter.dto.AccessoryDTO;
import visualigue.inter.dto.ObstacleDTO;
import visualigue.inter.dto.PlayerDTO;

/**
 *
 * @author Bruno L.L.
 */
public interface SelectionListener extends Listener {

    public void selectNothing();

    public void selectPlayer(PlayerDTO player);

    public void selectObstacle(ObstacleDTO obstacle);

    public void selectAccessory(AccessoryDTO accessory);
}
