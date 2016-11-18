/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.domain.game;

import java.io.Serializable;

/**
 *
 * @author Bruno L.L.
 */
public class Team implements Serializable {
    
    private String name;
    private String color;

    public Team(String name, String color) {
        this.name = name;
        this.color = color;
    }
    
    public String getName() {
        return name;
    }
    
    public String getColor() {
        return color;
    }
}
