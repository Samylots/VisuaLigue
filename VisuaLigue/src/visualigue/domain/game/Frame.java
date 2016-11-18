/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.domain.game;

import java.io.Serializable;
import visualigue.domain.game.Position;
import java.util.TreeMap;

/**
 *
 * @author Bruno L.L.
 */
public class Frame implements Serializable {
    
    private Frame next;
    private Frame back;
    private TreeMap<Integer, Position> positions;
}
