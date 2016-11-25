/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.domain.game;

import visualigue.domain.game.entities.Player;
import visualigue.domain.game.entities.Entity;
import visualigue.domain.game.entities.Accessory;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import visualigue.utils.Dimension;
import visualigue.exceptions.NoSuchIdException;
import visualigue.utils.IdGenerator;

/**
 *
 * @author Bruno L.L.
 */
public class Sport implements Serializable {

    private int id;
    private String name;
    private String fieldPicturePath;
    private Dimension fieldDimension;
    private Accessory accessory;
    private final List<Team> teams = new ArrayList<>();

    public Sport(String name, String fieldPicturePath, Dimension dimension, Accessory accessory) {
        this.id = IdGenerator.getInstance().generateId();
        this.name = name;
        this.fieldPicturePath = fieldPicturePath;
        this.fieldDimension = dimension;
        this.accessory = accessory;
    }

    public Accessory getSportAccessory() {
        return accessory;
    }

    public Dimension getFieldDimension() {
        return fieldDimension;
    }

    public Player getPlayer(int id) {
        Player player = null;
        for (Team team : teams) {
            if (team.hasPlayer(id)) {
                player = team.getPlayer(id);
            }
        }
        if (player == null) {
            //TODO throw exception or not?
        }
        return player;
    }

    public List<Player> getPlayers() {
        List<Player> players = new ArrayList<>();
        teams.stream().forEach((team) -> {
            players.addAll(team.getPlayers());
        });
        return players;
    }

    public void addTeam(Team team) {
        teams.add(team);
    }

    public Team getTeam(String teamName) {
        for (Team team : teams) {
            if (team.getName().equals(teamName)) {
                return team;
            }
        }
        throw new NoSuchIdException("There is no such team named '" + teamName + "' in teams of current sport.");
    }

    public String getName() {
        return name;
    }

    public String getFieldPicturePath() {
        return fieldPicturePath;
    }

    public Accessory getAccessory() {
        return accessory;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public int getId() {
        return id;
    }

}
