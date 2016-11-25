/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue;

import visualigue.gui.javafx.fxlayouts.FXLoader;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import visualigue.domain.VisuaLigueController;

/**
 *
 * @author samap
 */
public class VisuaLigue extends Application {

    public static final VisuaLigueController domain = new VisuaLigueController();

    @Override
    public void start(Stage primaryStage) {
        Node node = FXLoader.getInstance().load("mainWindow.fxml");
        Scene scene = new Scene((Parent) node, 800, 600);

        primaryStage.setOnCloseRequest((WindowEvent e) -> {
            VisuaLigue.domain.close();
            Platform.exit();
        });

        primaryStage.setTitle("VisuaLigue - BSSM");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /*@Override
     public void stop() {
     }*/
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
