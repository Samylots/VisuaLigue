/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.gui.javafx.fxlayouts;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Samuel
 */
public class Dialog extends Stage {

    private Button ok = new Button("Ok");
    private Button cancel = new Button("Cancel");

    public Dialog(String title, String message, Parent node) {
        ok.setOnAction((ActionEvent) -> {
            this.close();
        });
        cancel.setOnAction((ActionEvent) -> {
            this.close();
        });

        this.initModality(Modality.WINDOW_MODAL);
        this.initOwner(node.getScene().getWindow());
        VBox root = new VBox();
        root.setSpacing(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(15, 15, 15, 15));
        root.getChildren().add(new Label(message));
        HBox commands = new HBox();
        commands.setSpacing(20);
        commands.setPadding(new Insets(10));
        commands.setAlignment(Pos.BOTTOM_RIGHT);
        commands.getChildren().add(cancel);
        commands.getChildren().add(ok);
        root.getChildren().add(commands);
        Scene scene = new Scene(root);
        this.setScene(scene);
        this.setTitle(title);
        this.showAndWait();
    }

}
