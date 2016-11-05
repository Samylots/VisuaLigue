/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.gui.layouts;

import java.io.Serializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author samap
 */
public class CustomWindow extends Stage implements Serializable {

    public CustomWindow(Node parent, Parent node) {
        super();
        this.initModality(Modality.WINDOW_MODAL);
        this.initOwner(parent.getScene().getWindow());
        Scene scene = new Scene(node);
        this.setScene(scene);
    }
}
