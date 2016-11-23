/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.dto;

import visualigue.dto.*;
import visualigue.domain.game.Position;
import visualigue.utils.Coords;

/**
 *
 * @author Bruno L.L.
 */
public class PositionDTO {
    
    public Coords coords;
    public EntityDTO entity;

    public PositionDTO(Position position) {
        this.coords = position.getCoords();
        this.entity = new EntityDTO(position.getEntity());
    }
}
