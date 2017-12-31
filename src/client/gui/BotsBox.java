package client.gui;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class BotsBox {
    ChoiceBox choiceBots;
    int amountOfPlayers;
    int amountOfBots;

    public BotsBox(int amountOfPlayers){
        this.amountOfPlayers=amountOfPlayers;
    }
    public  int display() {
        int numberOfBoot=0;
        Stage window = new Stage();

      //  window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(500);
        window.setMaxHeight(500);

        final Text information = new Text(50, 75, "Choose number of bots");
        information.setFont(Font.font(java.awt.Font.DIALOG, 40));
        information.setFill(Color.DARKRED);

        Button closeButton = new Button("OK");
        closeButton.setMinSize(70,40);
        closeButton.setOnAction(e -> window.close());
        closeButton.setStyle(" -fx-background-color:#B3B3B3; ");

        switch (amountOfPlayers){
            case 2:
                choiceBots = new ChoiceBox(FXCollections.observableArrayList(0,1));
                break;
            case 3:
                choiceBots = new ChoiceBox(FXCollections.observableArrayList(0,1,2));
                break;
            case 4:
                choiceBots = new ChoiceBox(FXCollections.observableArrayList(0,1,2,3));
                break;
            case 6:
                choiceBots = new ChoiceBox(FXCollections.observableArrayList(0,1,2,3,4,5));
                break;
        }

        choiceBots.setValue(0);

        choiceBots.setOnAction(e ->
        {
            for (int i=0;i<5;i++) {
                if (choiceBots.getValue().equals(i)) {
                    amountOfBots =i;
                    System.out.println(amountOfBots);
                }
            }
        });

        choiceBots.setTooltip(new Tooltip("Number of bots"));
        choiceBots.setStyle("-fx-font: 22 arial; -fx-base: #c0c0c0;");
        choiceBots.setValue(0);

        VBox layout = new VBox(10);
        ToggleButton toggle = new ToggleButton("Toggle color");

        layout.backgroundProperty().bind(Bindings.when(toggle.selectedProperty())
                .then(new Background(new BackgroundFill(Color.CORNFLOWERBLUE, CornerRadii.EMPTY, Insets.EMPTY)))
                .otherwise(new Background(new BackgroundFill(Color.SILVER, CornerRadii.EMPTY, Insets.EMPTY))));

        layout.getChildren().addAll(information, choiceBots, closeButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout,Color.BLACK);
        window.setScene(scene);
        window.showAndWait();

        return amountOfBots;
    }
}

