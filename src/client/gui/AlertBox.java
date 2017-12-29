package client.gui;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {
    public static void display() {
        Stage window = new Stage();

        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(500);
        window.setMaxHeight(500);

        final Text information = new Text(50, 75, "Welcome to Chinese Checkers Game \n \t\t    Good luck!");
        information.setFont(Font.font(java.awt.Font.DIALOG, 40));
        information.setFill(Color.DARKRED);
        Button closeButton = new Button("Start");//, new ImageView(imageOk));
        closeButton.setMinSize(70,40);
        closeButton.setOnAction(e -> window.close());
        closeButton.setStyle(" -fx-background-color:#B3B3B3; ");
        VBox layout = new VBox(10);
        ToggleButton toggle = new ToggleButton("Toggle color");

        layout.backgroundProperty().bind(Bindings.when(toggle.selectedProperty())
                .then(new Background(new BackgroundFill(Color.CORNFLOWERBLUE, CornerRadii.EMPTY, Insets.EMPTY)))
                .otherwise(new Background(new BackgroundFill(Color.SILVER, CornerRadii.EMPTY, Insets.EMPTY))));

        layout.getChildren().addAll(information, closeButton);
        layout.setAlignment(Pos.CENTER);
        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout,Color.BLACK);
        window.setScene(scene);
        window.showAndWait();
    }
}
