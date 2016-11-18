/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.domain.utils;

import java.io.Serializable;

/**
 *
 * @author Samuel
 */
public class Coords implements Serializable {

    private double x;
    private double y;

    public Coords() {
        this.x = 0;
        this.y = 0;
    }

    public Coords(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Coords(Coords coords) {
        this.x = coords.x;
        this.y = coords.y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void invert() {
        this.x = -this.x;
        this.y = -this.y;
    }

    @Override
    public String toString() {
        return "X:" + x + ", Y:" + y;
    }

}
