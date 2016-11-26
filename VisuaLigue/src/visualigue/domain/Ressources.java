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
import visualigue.exceptions.NoSuchIdException;
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
            if (sport.getId() == id) {
                return sport;
            }
        }
        throw new NoSuchIdException("There is no such id '" + id + "' in available sports.");
    }

    public Game getGame(int id) {
        for (Game game : games) {
            if (game.getId() == id) {
                return game;
            }
        }
        throw new NoSuchIdException("There is no such id '" + id + "' in available games.");
    }

    public Obstacle getObstacle(int id) {
        for (Obstacle obstacle : availableObstacles) {
            if (obstacle.getId() == id) {
                return obstacle;
            }
        }
        throw new NoSuchIdException("There is no such id '" + id + "' in available obstacles.");
    }

    public void deleteObstacle(int id) {
        for (Obstacle obstacle : availableObstacles) {
            if (obstacle.getId() == id) {
                availableObstacles.remove(obstacle);
                break;
            }
        }
    }

    public void deleteSport(int id) {
        List<Game> toDelete = new ArrayList<Game>();
        for (Sport sport : availableSports) {
            if (sport.getId() == id) {
                for (Game game : games) {
                    if (game.getSport().getId() == id) {
                        toDelete.add(game);
                    }
                }
                for (Game game : toDelete) {
                    games.remove(game);
                }
                availableSports.remove(sport);
                break;
            }
        }
    }

    public void deleteGame(int id) {
        for (Game game : games) {
            if (game.getId() == id) {
                games.remove(game);
                break;
            }
        }
    }
}
