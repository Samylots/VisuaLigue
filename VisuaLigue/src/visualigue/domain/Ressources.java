/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.domain;

import java.util.List;
import javafx.geometry.Dimension2D;
import visualigue.domain.game.Game;
import visualigue.domain.game.Obstacle;
import visualigue.domain.game.Sport;
import visualigue.domain.utils.Coords;
import visualigue.domain.game.Entity;

/**
 *
 * @author Samuel
 */
public class Ressources {

    private List<Game> games;
    private List<Sport> availableSports;
    private List<Obstacle> availableObstacles;

    public void createNewObstacle(String name, Coords coord, Dimension2D dimension) {
        //availableObstacles.add(new Obstacle(name, coord, dimension));
    }

    public void createNewSport(String name, int limit, Dimension2D fieldDimension, Entity accessory, List<String> categories) {
        //Entity field = new Entity(new Coords(), fieldDimension);
        //availableSports.add(new Sport(name, limit, field, accessory, categories));
    }

    public List<Game> getGames() {
        return games;
    }

    public List<Sport> getAvailableSports() {
        return availableSports;
    }

    public List<Obstacle> getAvailableObstacles() {
        return availableObstacles;
    }

}
