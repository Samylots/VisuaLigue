/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.domain;

import java.util.ArrayList;
import java.util.List;
import visualigue.domain.game.Game;
import visualigue.domain.game.entities.Obstacle;
import visualigue.domain.game.Sport;
import visualigue.exceptions.NoSuchId;
import java.io.Serializable;

/**
 *
 * @author Samuel
 */
public class Ressources implements Serializable {

    private final List<Game> games = new ArrayList<>();
    private final List<Sport> availableSports = new ArrayList<>();
    private final List<Obstacle> availableObstacles = new ArrayList<>();

    public void addGame(Game game) {
        games.add(game);
    }

    public void addSport(Sport sport) {
        availableSports.add(sport);
    }

    public void addObstacle(Obstacle obstacle) {
        availableObstacles.add(obstacle);
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

    public Sport getSport(int id) {
        for (Sport sport : availableSports) {
            if (sport.getSportId() == id) {
                return sport;
            }
        }
        throw new NoSuchId("There is no such id '" + id + "' in available sports.");
    }

    public void deleteObstacle(int obstacleId) {
        Obstacle obstalceToDelete = null;
        for (Obstacle obstacle : availableObstacles) {
            if (obstacle.getId() == obstacleId) {
                obstalceToDelete = obstacle;
            }
        }
        availableObstacles.remove(obstalceToDelete);
    }

    public void deleteSport(int sportId) {
        for (Sport sport : availableSports) {
            if (sport.getSportId() == sportId) {
                for (Game game : games) {
                    if (game.getSport().getSportId() == sportId) {
                        games.remove(game);
                    }
                }
                availableSports.remove(sport);
            }
        }
    }

    public void deleteGame(Game currentGame) {
        games.remove(currentGame);
    }

}
