/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.domain.game;

import java.io.Serializable;
import java.util.List;
import visualigue.domain.utils.Coords;

/**
 *
 * @author Bruno L.L.
 */
public class Game implements Serializable {

    private Sport sport;
    private List<Obstacle> obstacles;
    private Frame firstFrame;
    private Frame lastFrame;
    private int totalFrames;
    private Entity currentEntity;
    private Frame currentFrame;

    public void addPlayerAt(Coords coord, Player player) {
        //TODO
    }

    public void deletePlayerAt(Coords coord) {
        //TODO
    }

    public void addObstacleAt(Obstacle obstacle, Coords coord) {
        // TODO add on current frame
        obstacles.add(obstacle);
    }

    public void deleteObstacleAt(Coords coord) {
        //TODO find obstacle at coord, delete from frame
        obstacles.remove(0);
    }

    public void selectEntityAt(Coords coord) {
        // TODO
    }
    
    public void addAccessoryAt(Coords coord, Player player) {
        //TODO
    }

    public void deleteAccessoryAt(Coords coord) {
        //TODO
    }

    public void moveCurrentEntityTo(Coords coord) {
        // TODO
    }

    public void setSport(Sport sport) {
        this.sport = sport;
        //TODO checks team nb and positions (Field limits..!)
    }
    
    public Sport getSport() {
        return sport;
    }
}
