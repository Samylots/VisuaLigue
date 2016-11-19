/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.domain;

import visualigue.domain.utils.Dimension;
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

    public Coords pixelToMeter(Coords currentPos, Dimension uiDimension) {
        Dimension realDimension = domain.getFieldDimension();
        double x = currentPos.getX(), y = currentPos.getY();
        return new Coords(x / uiDimension.getWidth() * realDimension.getWidth(), y / uiDimension.getHeight() * realDimension.getHeight());
    }

    public Coords meterToPixel(Coords currentPos, Dimension uiDimension) {
        Dimension realDimension = domain.getFieldDimension();
        double x = currentPos.getX(), y = currentPos.getY();
        x = x / realDimension.getWidth() * uiDimension.getWidth();
        y = y / realDimension.getHeight() * uiDimension.getHeight();
        return new Coords(x, y);
    }
}
