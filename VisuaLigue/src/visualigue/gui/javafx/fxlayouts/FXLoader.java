/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.gui.javafx.fxlayouts;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import visualigue.exceptions.CantFindSpecifiedFXMLFile;

/**
 *
 * @author Samuel
 */
//Singleton Class...
public class FXLoader {

    private FXLoader() {

    }

    private static FXLoader instance = null;

    public static FXLoader getInstance() {
        if (instance == null) {
            instance = new FXLoader();
        }
        return instance;
    }

    FXMLLoader fxmlLoader;

    public Parent load(String name) {
        try {
            fxmlLoader = new FXMLLoader(getClass().getResource(name));
            return fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(FXLoader.class.getName()).log(Level.SEVERE, null, ex.getMessage());
        }
        throw new CantFindSpecifiedFXMLFile("File in error: '" + name);
    }

    public <T> T getLastController() {
        return (T) fxmlLoader.getController();
    }
}
