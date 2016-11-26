/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.inter.utils.exceptions;

/**
 *
 * @author Samuel
 */
public class NoSuchIdException extends RuntimeException {

    public NoSuchIdException() {
    }

    public NoSuchIdException(String message) {
        super(message);
    }

}
