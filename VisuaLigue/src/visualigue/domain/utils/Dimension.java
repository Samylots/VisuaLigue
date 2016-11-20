/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.domain.utils;

import java.io.Serializable;

/**
 *
 * @author Bruno L.L.
 */
public class Dimension implements Serializable {
    
    private double width;
    private double height;

    public Dimension() {
        this.width = 0;
        this.height = 0;
    }

    public Dimension(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public Dimension(Dimension dimension) {
        this.width = dimension.width;
        this.height = dimension.height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void invert() {
        this.width = -this.width;
        this.height = -this.height;
    }

    @Override
    public String toString() {
        return "Width:" + width + ", Height:" + height;
    }
}
