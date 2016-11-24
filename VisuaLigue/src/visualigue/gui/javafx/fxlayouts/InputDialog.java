/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.gui.javafx.fxlayouts;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Samuel
 */
public class InputDialog extends Stage {

    private final Button ok = new Button("Ok");
    private final Button cancel = new Button("Cancel");
    
    private VBox root = new VBox();
    private TextField input = new TextField();

    private boolean action = false;

    public InputDialog(String title, String message, Parent node) {
        ok.setOnAction((ActionEvent) -> {
            if(input.getText().equals("")){
                Dialog error =  new Dialog("Error", "Please fill field before confirming.", root);
            }else{
                action = true;
                this.close();
            }
        });
        cancel.setOnAction((ActionEvent) -> {
            action = false;
            this.close();
        });

        this.initModality(Modality.WINDOW_MODAL);
        this.initOwner(node.getScene().getWindow());
        root.setSpacing(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(15, 15, 15, 15));
        root.getChildren().add(new Label(message));
        root.getChildren().add(input);
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

    public boolean isConfirmed() {
        return action;
    }
    
    public String getInput(){
        return input.getText();
    }
}
