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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import visualigue.domain.dumies.Sport;
import visualigue.gui.SportListController;

/**
 *
 * @author samap
 */
public class VisuaLigue extends Application {

    List<Sport> sports = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        sports.add(new Sport("/", "Soccer", "1"));
        sports.add(new Sport("/", "Football", "2"));
        sports.add(new Sport("/", "Hockey", "3"));

        Node node = FXLoader.getInstance().load("sportList.fxml");
        SportListController controller = FXLoader.getInstance().getLastController();
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
        Scene scene = new Scene((Parent) node, 300, 400);

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
