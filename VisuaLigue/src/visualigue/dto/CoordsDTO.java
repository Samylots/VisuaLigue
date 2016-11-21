/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.dto;

import visualigue.domain.utils.Coords;

/**
 *
 * @author Bruno L.L.
 */
public class CoordsDTO {
    
    public double x;
    public double y;
    
    public CoordsDTO(Coords coords) {
        this.x = coords.getX();
        this.y = coords.getY();
    }
    
    public CoordsDTO(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
