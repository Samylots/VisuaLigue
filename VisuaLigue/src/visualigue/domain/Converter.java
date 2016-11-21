/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.domain;

import visualigue.dto.DimensionDTO;
import visualigue.domain.utils.Coords;

/**
 *
 * @author Samuel
 */
public class Converter {

    private VisuaLigueController domain;

    public Converter(VisuaLigueController domainController) {
        domain = domainController;
    }

    public Coords pixelToMeter(Coords currentPos, DimensionDTO uiDimension) {
        DimensionDTO realDimension = domain.getFieldDimension();
        double x = currentPos.getX();
        double y = currentPos.getY();
        x /= uiDimension.width ;
        x *= realDimension.width;
        y /= uiDimension.height;
        y *= realDimension.height;
        return new Coords(round(x) , round(y) );
    }

    public Coords meterToPixel(Coords currentPos, DimensionDTO uiDimension) {
        DimensionDTO realDimension = domain.getFieldDimension();
        double x = currentPos.getX(), y = currentPos.getY();
        x = x / realDimension.width * uiDimension.width;
        y = y / realDimension.height * uiDimension.height;
        return new Coords(round(x), round(y));
    }
   
    private double round(double value){
        return Math.round(value * 1000.0) / 1000.0;
    }
    
    public DimensionDTO pixelToDimension(double width, double height) {
        return new DimensionDTO(width, height);
    }
}
