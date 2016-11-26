/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.inter.dto;

import visualigue.domain.main.Position;
import visualigue.inter.utils.Coords;

/**
 *
 * @author Bruno L.L.
 */
public class PositionDTO {

    public Coords coords;
    public EntityDTO entity;
    public boolean isMoved;
    public boolean isSelected;

    public PositionDTO(Position position) {
        this.coords = position.getCoords();
        this.entity = EntityDTOFactory.create(position.getEntity());
        this.isMoved = position.IsMoved();
    }
}
