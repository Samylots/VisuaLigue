/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.dto;

import visualigue.domain.utils.Dimension;

/**
 *
 * @author Bruno L.L.
 */
public class DimensionDTO {
    
    public double width;
    public double height;
    
    public DimensionDTO(Dimension dimension) {
        this.width = dimension.getWidth();
        this.height = dimension.getHeight();
    }
    
    public DimensionDTO(double width, double height) {
        this.width = width;
        this.height = height;
    }
}
