/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.domain.game;

import visualigue.domain.game.entities.Player;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import visualigue.domain.utils.Dimension;
import visualigue.exceptions.NoPlayerWithSpecifiedIdException;

/**
 *
 * @author Bruno L.L.
 */
public class Team implements Serializable {

    private final static Dimension defaultPlayerDimensions = new Dimension(0.3, 0.7);

    private String name;
    private String color;
    private List<Player> players = new ArrayList<>();

    public Team(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public void addPlayer(String picPath, String name, String role) {
        players.add(new Player(defaultPlayerDimensions, picPath, role, name));
    }

    public Player getPlayer(int id) {
        Player selectedPlayer = null;
        for (Player player : players) {
            if (player.getId() == id) {
                selectedPlayer = player;
            }
        }
        if (selectedPlayer == null) {
            throw new NoPlayerWithSpecifiedIdException("There is no player with id " + id + "in team " + name);
        }
        return selectedPlayer;
    }

    public boolean hasPlayer(int id) {
        return players.stream().anyMatch((player) -> (player.getId() == id));
    }

    public List<Player> getPlayers() {
        return players;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }
}
