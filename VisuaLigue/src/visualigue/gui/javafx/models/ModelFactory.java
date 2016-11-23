/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.gui.javafx.models;

import java.util.HashMap;
import javafx.scene.image.Image;
import java.util.List;
import visualigue.dto.SportDTO;

/**
 *
 * @author Samuel
 */
public class ModelFactory {

    public static Sport createSport(SportDTO sport) {
        return new Sport(sport.name, new Image(sport.fieldPicturePath), sport.id);
    }
}
