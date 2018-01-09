package client.gui;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class AlertBox extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        GameInformation gameInformation = new GameInformation();
        Stage window = new Stage();
        BorderPane border = new BorderPane();
        border.setStyle("-fx-background-color: #c0c0c0; ");
        VBox vBox = new VBox(10);
        HBox hBox = new HBox(10);
        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(500);
        window.setMaxHeight(500);
        Button buttonGameInformation = new Button("INFORMATION");
        buttonGameInformation.setStyle(" -fx-background-color:#B3B3B3; ");
        Button buttonGameRules = new Button("RULES");
        buttonGameRules.setStyle(" -fx-background-color:#B3B3B3; ");

        final Text information = new Text(50, 75, "Welcome to Chinese Checkers Game \n \t\t    Good luck!");
        information.setFont(Font.font(java.awt.Font.DIALOG, 40));
        information.setFill(Color.DARKRED);
        Button closeButton = new Button("Start");//, new ImageView(imageOk));
        closeButton.setMinSize(70,40);
        closeButton.setOnAction(e -> window.close());
        closeButton.setStyle(" -fx-background-color:#B3B3B3; ");
        ToggleButton toggle = new ToggleButton("Toggle color");

        vBox.backgroundProperty().bind(Bindings.when(toggle.selectedProperty())
                .then(new Background(new BackgroundFill(Color.CORNFLOWERBLUE, CornerRadii.EMPTY, Insets.EMPTY)))
                .otherwise(new Background(new BackgroundFill(Color.SILVER, CornerRadii.EMPTY, Insets.EMPTY))));

        vBox.getChildren().addAll(information, closeButton);
        vBox.setAlignment(Pos.CENTER);

final String s = "information";
        buttonGameInformation.setOnAction(actionEvent -> {
               gameInformation.display("information");
        });

        buttonGameRules.setOnAction(actionEvent -> {
            gameInformation.display("rules");
        });

        hBox.getChildren().addAll(buttonGameInformation,buttonGameRules);
        border.setTop(hBox);
        border.setCenter(vBox);
        Scene scene = new Scene(border,Color.BLACK);
        window.setScene(scene);
        window.showAndWait();
    }


}
