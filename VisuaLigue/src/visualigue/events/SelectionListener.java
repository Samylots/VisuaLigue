/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.events;

import visualigue.dto.AccessoryDTO;
import visualigue.dto.ObstacleDTO;
import visualigue.dto.PlayerDTO;

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
