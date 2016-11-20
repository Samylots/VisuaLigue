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

    private static VisuaLigueController domainController = new VisuaLigueController();

    public static VisuaLigueController getController() {
        return domainController;
    }

    @Override
    public void start(Stage primaryStage) {

        Node node = FXLoader.getInstance().load("mainWindow.fxml");
        MainWindowController mainWindowController = FXLoader.getInstance().getLastController();

        mainWindowController.init(domainController);

        Scene scene = new Scene((Parent) node, 800, 600);

        primaryStage.setTitle("VisuaLigue - BSSM");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    @Override
    public void stop() {
        this.domainController.close();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
