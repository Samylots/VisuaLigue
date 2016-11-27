/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.inter.utils;

import visualigue.VisuaLigue;

/**
 *
 * @author Samuel
 */
public class Converter {

    public Converter() {
    }

    public Coords pixelToMeter(Coords currentPos, Dimension uiDimension) {
        Dimension realDimension = VisuaLigue.domain.getFieldDimension();
        double x = currentPos.getX();
        double y = currentPos.getY();
        x /= uiDimension.getWidth();
        x *= realDimension.getWidth();
        y /= uiDimension.getHeight();
        y *= realDimension.getHeight();
        return new Coords(round(x), round(y));
    }

    public Dimension pixelToMeter(Dimension currentPos, Dimension uiDimension) {
        Dimension realDimension = VisuaLigue.domain.getFieldDimension();
        double x = currentPos.getWidth();
        double y = currentPos.getHeight();
        x /= uiDimension.getWidth();
        x *= realDimension.getWidth();
        y /= uiDimension.getHeight();
        y *= realDimension.getHeight();
        return new Dimension(round(x), round(y));
    }

    public Coords meterToPixel(Coords currentPos, Dimension uiDimension) {
        Dimension realDimension = VisuaLigue.domain.getFieldDimension();
        double x = currentPos.getX(), y = currentPos.getY();
        x = x / realDimension.getWidth() * uiDimension.getWidth();
        y = y / realDimension.getHeight() * uiDimension.getHeight();
        return new Coords(round(x), round(y));
    }

    public Dimension meterToPixel(Dimension currentPos, Dimension uiDimension) {
        Dimension realDimension = VisuaLigue.domain.getFieldDimension();
        double x = currentPos.getWidth(), y = currentPos.getHeight();
        x = x / realDimension.getWidth() * uiDimension.getWidth();
        y = y / realDimension.getHeight() * uiDimension.getHeight();
        return new Dimension(round(x), round(y));
    }

    private double round(double value) {
        return Math.round(value * 1000.0) / 1000.0;
    }
}
