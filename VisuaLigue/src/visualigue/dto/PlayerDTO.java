/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.dto;

import visualigue.domain.utils.Dimension;
import visualigue.domain.game.entities.Player;

/**
 *
 * @author Bruno L.L.
 */
public class PlayerDTO {
    public int id;
    public String picturePath;
    public Dimension dimension;
    public String role;
    public String name;
    
    public PlayerDTO(Player player, int id) {
        this.id = id;
        this.picturePath = player.getPicturePath();
        this.dimension = player.getDimension();
        this.role = player.getRole();
        this.name = player.getName();
    }
}
