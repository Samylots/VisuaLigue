/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.inter.utils;

import javafx.scene.paint.Color;

/**
 *
 * @author Samuel
 */
public class ColorConverter {

    public static String toHex(Color color) {
        String colorHex = Integer.toHexString(color.hashCode());
        return colorHex.substring(0, Math.min(colorHex.length(), 6));
    }
}
