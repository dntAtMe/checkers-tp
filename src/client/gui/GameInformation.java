package client.gui;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameInformation {
    public void display(String request){

        Stage stage = new Stage();
        HBox hBox = new HBox();
        hBox.setSpacing(60.0);
        stage.setTitle("Game Information");
        Text text =createText(request);

        text.setFont(Font.font(java.awt.Font.DIALOG, 15));
        ToggleButton toggle = new ToggleButton("Toggle color");
        hBox.backgroundProperty().bind(Bindings.when(toggle.selectedProperty())
                .then(new Background(new BackgroundFill(Color.CORNFLOWERBLUE, CornerRadii.EMPTY, Insets.EMPTY)))
                .otherwise(new Background(new BackgroundFill(Color.SILVER, CornerRadii.EMPTY, Insets.EMPTY))));

        hBox.getChildren().addAll(text);
        Scene scene = new Scene(hBox);
        stage.setScene(scene);
        stage.show();
    }

    private Text createText(String request){
        System.out.println(request);
        if(request == "information") {
            Text text = new Text("It's a strategy board game of German origin\n " +
                    "(named \"Sternhalma\" which can be played\n" +
                    "by two, three, four, or six people,\n" +
                    "playing individually or with partners \n " +
                    "The game is a modern and simplified variant of the American game Halma");
            return text;
        }
        if (request == "rules"){
            Text text = new Text("The aim is to race all one's pieces into the star corner\n " +
                    "on the opposite side of the board before opponents do the same.\n" +
                    " In our version,each player starts\n" +
                    " with their colored pieces on one of the six points or corners of the star\n" +
                    "and attempts to race them all home into the opposite corner.\n " +
                    "Players take turns moving a single piece, either by moving one step \n" +
                    "in any direction to an adjacent empty space, or by jumping in one\n" +
                    " or any number of available consecutive hops over other single pieces.\n" +
                    " A player may not combine hopping with a single-step move – a move consists of one or the other.\n" +
                    " There is no capturing in Chinese Checkers, so hopped pieces remain active and in play.\n" +
                    " Turns proceed clockwise around the board.");
            return text;
        }
        return new Text("dasn");
    }
}
