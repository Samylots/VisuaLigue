/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue;

import java.io.Serializable;
import visualigue.gui.javafx.fxlayouts.FXLoader;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import visualigue.gui.javafx.fxcontrollers.MainWindowController;
import visualigue.domain.VisuaLigueController;

/**
 *
 * @author samap
 */
public class VisuaLigue extends Application {
    
    private static VisuaLigueController controller = new VisuaLigueController();
    
    public static VisuaLigueController getController() {
        return controller;
    }

    @Override
    public void start(Stage primaryStage) {

        Node node = FXLoader.getInstance().load("mainWindow.fxml");
        MainWindowController controller = FXLoader.getInstance().getLastController();
        controller.init();
        
        VisuaLigue.controller.createNewGame();

        Scene scene = new Scene((Parent) node, 800, 600);

        primaryStage.setTitle("VisuaLigue - SSM");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
