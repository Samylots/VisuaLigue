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
public class CantActivateMaxPlayerException extends RuntimeException {

    public CantActivateMaxPlayerException() {
    }

    public CantActivateMaxPlayerException(String message) {
        super(message);
    }

}
