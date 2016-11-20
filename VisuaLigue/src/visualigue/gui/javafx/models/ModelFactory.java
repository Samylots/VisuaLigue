/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.gui.javafx.models;

import java.util.HashMap;
import javafx.scene.image.Image;
import java.util.List;

/**
 *
 * @author Samuel
 */
public class ModelFactory {

    public static Sport createSport(HashMap<String, Object> sport) {
        return new Sport((String)sport.get("name"), new Image((String)sport.get("fieldPicturePath")), (Integer)sport.get("id"));
    }
}
