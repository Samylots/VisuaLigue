/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.exceptions;

/**
 *
 * @author Samuel
 */
public class CollisionDetectedException extends RuntimeException {

    public CollisionDetectedException() {
    }

    public CollisionDetectedException(String message) {
        super(message);
    }

}
