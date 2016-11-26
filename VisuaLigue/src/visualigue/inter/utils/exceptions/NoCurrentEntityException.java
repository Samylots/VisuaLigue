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
public class NoCurrentEntityException extends RuntimeException {

    public NoCurrentEntityException() {
    }

    public NoCurrentEntityException(String message) {
        super(message);
    }

}
