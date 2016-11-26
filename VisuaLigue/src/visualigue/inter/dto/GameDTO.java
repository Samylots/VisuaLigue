/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.inter.dto;

import visualigue.domain.main.Game;

/**
 *
 * @author Bruno L.L.
 */
public class GameDTO {

    public int id;
    public int sportId;
    public String name;
    public String picPath;

    public GameDTO(Game game) {
        this.id = game.getId();
        this.name = game.getName();
        this.sportId = game.getSport().getId();
        this.picPath = game.getSport().getFieldPicturePath(); //TODO change it to Game pic preview generated!
    }
}
