/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.gui.javafx.models;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Samuel
 */
public class TeamTableItem {

    private final SimpleStringProperty name;
    private final SimpleStringProperty color;

    public TeamTableItem(String name, String color) {
        this.name = new SimpleStringProperty(name);
        this.color = new SimpleStringProperty(color);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getColor() {
        return color.get();
    }

    public void setColor(String color) {
        this.color.set(color);
    }
}
