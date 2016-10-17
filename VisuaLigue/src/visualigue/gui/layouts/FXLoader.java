/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.gui.layouts;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;

/**
 *
 * @author Samuel
 */
public class FXLoader {

    FXMLLoader fxmlLoader = new FXMLLoader();

    public Parent load(String name) {
        try {
            //C'est ici que le lien dans "getResource(#####)" ne marche pas....
            return fxmlLoader.load(getClass().getResource(name));
        } catch (IOException ex) {
            Logger.getLogger(FXLoader.class.getName()).log(Level.SEVERE, null, ex.getMessage());
        }
        return null; //BAD!!!!!
    }

    public <T> T getLastController() {
        return (T) fxmlLoader.getController();
    }
}
