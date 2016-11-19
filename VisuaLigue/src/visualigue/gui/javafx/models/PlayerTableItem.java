/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.gui.javafx.models;

import javafx.beans.property.SimpleStringProperty;

/**
 * Class for player in new sport form
 *
 * @author Samuel
 */
public class PlayerTableItem {

    private final SimpleStringProperty name;
    private final SimpleStringProperty role;
    private final SimpleStringProperty team;

    public PlayerTableItem(String name, String role, String team) {
        this.name = new SimpleStringProperty(name);
        this.role = new SimpleStringProperty(role);
        this.team = new SimpleStringProperty(team);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getRole() {
        return role.get();
    }

    public void setRole(String role) {
        this.role.set(role);
    }

    public String getTeam() {
        return team.get();
    }

    public void setTeam(String team) {
        this.team.set(team);
    }
}
