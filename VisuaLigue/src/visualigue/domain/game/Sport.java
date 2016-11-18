/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.domain.game;

import java.io.Serializable;
import java.util.List;
import visualigue.domain.utils.Dimension;
import visualigue.domain.game.Team;

/**
 *
 * @author Bruno L.L.
 */
public class Sport implements Serializable {

    private String name;
    private String fieldPicturePath;
    private Dimension dimension;
    private Entity accessory;
    private String accessoryName;
    private List<Team> team;

    public Sport(String name, String fieldPicturePath, Dimension dimension, Entity accessory, String accessoryName) {
        this.name = name;
        this.fieldPicturePath = fieldPicturePath;
        this.dimension = dimension;
        this.accessory = accessory;
        this.accessoryName = accessoryName;
    }

    public Dimension getFieldDimension() {
        return dimension;
    }
}