/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue;

import visualigue.gui.layouts.FXLoader;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import visualigue.domain.dumies.Sport;
import visualigue.gui.SportListController;

/**
 *
 * @author samap
 */
public class VisuaLigue extends Application {

    FXLoader loader = new FXLoader();
    List<Sport> sports = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        sports.add(new Sport("/", "Soccer", "1"));

        Node node = loader.load("sportList.fxml");
        SportListController controller = loader.getLastController();
        controller.init(sports);
        /*Button btn = new Button();
         btn.setText("Say 'Hello World'");
         btn.setOnAction(new EventHandler<ActionEvent>() {

         @Override
         public void handle(ActionEvent event) {
         System.out.println("Hello World!");
         }
         });

         StackPane root = new StackPane();
         root.getChildren().add(btn);*/
        Scene scene = new Scene((Parent) node, 200, 250);

        primaryStage.setTitle("Hello World!");
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
