/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.dto;

import visualigue.utils.Dimension;
import visualigue.domain.game.entities.Player;
import visualigue.domain.game.entities.Entity;
import visualigue.dto.EntityDTO;

/**
 *
 * @author Bruno L.L.
 */
public class PlayerDTO extends EntityDTO {
    public int number;
    public String role;
    public String name;
    public boolean isOnBoard;
    
    public PlayerDTO(Player player, int number) {
        super(player.getDimension(), player.getId(), player.getPicturePath());
        this.number = number;
        this.role = player.getRole();
        this.name = player.getName();
        this.isOnBoard = false;
    }
}
