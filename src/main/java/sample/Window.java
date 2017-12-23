package main.java.sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Window extends Application implements EventHandler<ActionEvent> {
    Controller controller;
    Stage window;
    Scene scene;

    Button two_players;
    Button three_players;
    Button four_players;
    Button six_players;

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;

        Label label = new Label("Choose number of players!");
        two_players = new Button("Two players");
        three_players = new Button("Three players");
        four_players = new Button("Four players");
        six_players = new Button("Six players");

        two_players.setOnAction(this);
        three_players.setOnAction(this);
        two_players.setOnAction(this);
        six_players.setOnAction(this);

        VBox layout = new VBox(60);
        layout.getChildren().addAll(label, two_players,three_players,four_players,six_players);
        scene = new Scene(layout, 200, 400);

        window.setScene(scene);
        window.setTitle("Chinese Checkers");
        window.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void handle(ActionEvent event) {
        if(event.getSource() == two_players ){
            window.close();
            controller = new Controller(2);
        }

        else if(event.getSource() == three_players){
            window.close();
            controller = new Controller(3);
        }
        else if(event.getSource() == four_players){
            controller = new Controller(3);
            window.close();

        }
        else if(event.getSource() == six_players){
            window.close();
            controller = new Controller(3);
        }
    }
}