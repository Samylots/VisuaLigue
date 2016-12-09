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
public class CantOpenSpecifiedFXMLFile extends RuntimeException {

    public CantOpenSpecifiedFXMLFile() {
    }

    public CantOpenSpecifiedFXMLFile(String message) {
        super(message);
    }

}
