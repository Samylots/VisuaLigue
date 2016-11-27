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
    public String color;

    public PlayerDTO(Player player) {
        super(player.getDimension(), player.getId(), player.getPicturePath());
        this.role = player.getRole();
        this.name = player.getName();
        this.isOnBoard = false;
        this.color = player.getColor();
        this.number = player.getNumber();
    }
}
