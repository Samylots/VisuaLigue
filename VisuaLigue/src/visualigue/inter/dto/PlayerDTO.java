/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.inter.dto;

import visualigue.domain.main.entities.Player;

/**
 *
 * @author Bruno L.L.
 */
public class PlayerDTO extends EntityDTO {

    public int number;
    public String role;
    public String name;
    public boolean isOnBoard;

    public PlayerDTO(Player player) {
        super(player.getDimension(), player.getId(), player.getPicturePath());
        this.number = -1;
        this.role = player.getRole();
        this.name = player.getName();
        this.isOnBoard = false;
    }

    public PlayerDTO(Player player, int number) { //Maybe it should be in the default Player entity...
        super(player.getDimension(), player.getId(), player.getPicturePath());
        this.number = number;
        this.role = player.getRole();
        this.name = player.getName();
        this.isOnBoard = false;
    }
}
