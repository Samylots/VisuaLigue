/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.domain.game;

import java.util.List;
import javafx.geometry.Dimension2D;
import visualigue.domain.utils.Entity;

/**
 *
 * @author Samuel
 */
public class Sport {

    private final String name;
    private final Entity field;
    private final Entity accessory;
    private final List<String> categories;
    private final int teamLimit;

    public Sport(String name, int teamLimit, Entity field, Entity accessory, List<String> categories) {
        this.name = name;
        this.field = field;
        this.accessory = accessory;
        this.categories = categories;
        this.teamLimit = teamLimit;
    }

    public String getName() {
        return name;
    }

    public Dimension2D getFieldDimension() {
        return field.getDimension();
    }

    public Entity getAccessory() {
        return accessory;
    }

    public List<String> getCategories() {
        return categories;
    }

    public int getTeamLimit() {
        return teamLimit;
    }

}
