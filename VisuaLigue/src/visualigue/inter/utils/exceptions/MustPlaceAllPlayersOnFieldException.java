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
public class MustPlaceAllPlayersOnFieldException extends RuntimeException {

    public MustPlaceAllPlayersOnFieldException() {
    }

    public MustPlaceAllPlayersOnFieldException(String message) {
        super(message);
    }

}
