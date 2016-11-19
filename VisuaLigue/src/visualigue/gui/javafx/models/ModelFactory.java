/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.gui.javafx.models;

import javafx.scene.image.Image;

/**
 *
 * @author Samuel
 */
public class ModelFactory {

    public static Sport createSport(visualigue.domain.game.Sport sport) {
        return new Sport(sport.getName(), new Image(sport.getFieldPicturePath()), sport.getSportId());
    }
}
