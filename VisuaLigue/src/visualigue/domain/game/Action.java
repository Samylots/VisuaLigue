/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.domain.game;

/**
 *
 * @author Samuel
 */
public class Action {

    private final String name;
    private final double start;
    private final double duration;

    public Action(String name, double start, double duration) {
        this.name = name;
        this.start = start;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public double getStart() {
        return start;
    }

    public double getDuration() {
        return duration;
    }

}
