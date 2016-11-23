/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import visualigue.domain.game.Frame;
import visualigue.domain.game.Sport;
import visualigue.domain.game.entities.Accessory;
import visualigue.domain.game.entities.Entity;
import visualigue.domain.game.entities.Obstacle;
import visualigue.events.DrawListener;
import visualigue.domain.game.Game;

/**
 *
 * @author Bruno L.L.
 */
public class GameDTO {
    
    private int id;
    private String name;
    
    public GameDTO(Game game) {
        this.id = game.getId();
        this.name = game.getName();
    }
}
