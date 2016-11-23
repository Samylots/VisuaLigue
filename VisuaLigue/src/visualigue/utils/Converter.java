/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.utils;

import visualigue.domain.VisuaLigueController;
import visualigue.utils.Dimension;
import visualigue.utils.Coords;

/**
 *
 * @author Samuel
 */
public class Converter {

    private VisuaLigueController domain;

    public Converter(VisuaLigueController domainController) {
        domain = domainController;
    }

    public Coords pixelToMeter(Coords currentPos, Dimension uiDimension) {
        Dimension realDimension = domain.getFieldDimension();
        double x = currentPos.getX();
        double y = currentPos.getY();
        x /= uiDimension.getWidth();
        x *= realDimension.getWidth();
        y /= uiDimension.getHeight();
        y *= realDimension.getHeight();
        return new Coords(round(x) , round(y) );
    }

    public Coords meterToPixel(Coords currentPos, Dimension uiDimension) {
        Dimension realDimension = domain.getFieldDimension();
        double x = currentPos.getX(), y = currentPos.getY();
        x = x / realDimension.getWidth() * uiDimension.getWidth();
        y = y / realDimension.getHeight() * uiDimension.getHeight();
        return new Coords(round(x), round(y));
    }
   
    private double round(double value){
        return Math.round(value * 1000.0) / 1000.0;
    }
    
    public Dimension pixelToDimension(double width, double height) {
        return new Dimension(width, height);
    }
}
