/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.domain.utils;

import javafx.geometry.Dimension2D;

/**
 *
 * @author Samuel
 */
public class Entity {

    private Coords position;
    private String picPath;
    private final Dimension2D dimension;

    public Entity(Coords position, Dimension2D dimensions) {
        this.position = position;
        this.dimension = dimensions;
    }

    public Coords getPosition() {
        return position;
    }

    public void setPosition(Coords position) {
        this.position = position;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public Dimension2D getDimension() {
        return dimension;
    }

    public boolean isInBounds(Coords coord) {
        //TODO test if is in bounds
        return true;
    }

}
