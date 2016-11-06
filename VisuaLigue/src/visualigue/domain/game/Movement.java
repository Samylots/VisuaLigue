/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.domain.game;

import java.util.List;
import visualigue.domain.utils.Coords;

/**
 *
 * @author Samuel
 */
public class Movement {

    private List<Coords> positions;
    private final double start;
    private double duration;

    public Movement(double start) {
        this.start = start;
    }

    public List<Coords> getPositions() {
        return positions;
    }

    public void setPositions(List<Coords> positions) {
        this.positions = positions;
    }

    public double getStart() {
        return start;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public boolean isValidMovement() {
        return positions.size() >= 2;
    }

}
