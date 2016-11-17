/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.domain.game;

import java.io.Serializable;
import java.util.List;
import javafx.geometry.Dimension2D;
import visualigue.domain.utils.Coords;
import visualigue.domain.utils.Entity;

/**
 *
 * @author Samuel
 */
public class Game implements Serializable {

    private Sport sport;
    private List<Player> team;
    private List<Obstacle> obstacles;
    private double totalGameTime;
    private Entity currentEntity;

    public void addPlayerAt(Coords coord, Dimension2D dimension) {
        //TODO check sport team limit!
        team.add(new Player(coord, dimension));
    }

    public void deletePlayerAt(Coords coord) {
        //TODO find player at coord
        team.remove(0);
    }

    public void addObstacleAt(String name, Coords coord, Dimension2D dimension) {
        obstacles.add(new Obstacle(name, coord, dimension));
    }

    public void deleteObstacleAt(Coords coord) {
        //TODO find obstacle at coord
        obstacles.remove(0);
    }

    public void selectEntityAt(Coords coord) {
        //select entity that has bounds
    }

    public void moveCurrentEntityTo(Coords coord) {
        currentEntity.setPosition(coord);
    }

    public void setSport(Sport sport) {
        this.sport = sport;
        //TOdo checks team nb and positions (Field limits..!)
    }

    public Dimension2D getFieldDimension() {
        return sport.getFieldDimension();
    }

}
