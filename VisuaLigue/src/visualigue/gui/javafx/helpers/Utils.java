/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.gui.javafx.helpers;

/**
 *
 * @author Samuel
 */
public class Utils {

    public static boolean isNumeric(String text) {
        try {
            double value = Double.parseDouble(text);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
