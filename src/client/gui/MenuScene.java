package client.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import client.engine.InputHandler;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MenuScene {

  private Button buttonNewGame;
  private Button buttonJoinGame;
  private ChoiceBox choicePlayers;
  private ChoiceBox choiceBoot;
  private InputHandler inputHandler;
  BotsBox box;
  int amountOfPlayers = 2;
  int amountOfBots = 0;
  int[] optionsOfPlayers = {2, 3, 4, 6};
  private Scene scene;

  public MenuScene(InputHandler inputHandler, int width, int height) {
    BorderPane border = new BorderPane();
    border.setStyle("-fx-background-color:   #708090;");
    HBox startingGame = new HBox();
    startingGame.setSpacing(40.0);

    HBox numbersOfPlayer = new HBox();
    numbersOfPlayer.setSpacing(60.0);

    HBox serverInformation = new HBox();
    serverInformation.setSpacing(60.0);
    final String[] serverIp = new String[1];

    Text information = new Text("Choose number \n of players");
    information.setFont(Font.font(java.awt.Font.DIALOG, 20));
    Text ipInformation = new Text("Choose \n server ip");
    ipInformation.setFont(Font.font(java.awt.Font.DIALOG, 20));
    TextField ip = new TextField();
    ip.setStyle("-fx-background-color:#B3B3B3;");


    buttonNewGame = new Button("New game");
    buttonNewGame.setStyle("-fx-font: 22 arial; -fx-base: #c0c0c0;");

    buttonNewGame.setOnAction(actionEvent -> {
      serverIp[0] =ip.getText();
      System.out.println("Server ip : "+serverIp[0]);
      box = new BotsBox(amountOfPlayers);
      amountOfBots=box.display();
      inputHandler.onNewGameSelected(amountOfPlayers, amountOfBots,serverIp[0]);
      System.out.println("Ilosc botow: " + amountOfBots);
    });

    buttonNewGame.setMinSize(100, 100);

    buttonJoinGame = new Button("Join game");
    buttonJoinGame.setStyle("-fx-font: 22 arial; -fx-base: #c0c0c0;");

    buttonJoinGame.setOnAction(actionEvent -> {
      serverIp[0] =ip.getText();
      System.out.println("server ip : "+serverIp[0]);
      inputHandler.onJoinGameSelected(amountOfPlayers, serverIp[0]);
    });

    buttonJoinGame.setMinSize(100, 100);

    choicePlayers = new ChoiceBox(FXCollections.observableArrayList(2, 3, 4, 6));
    choicePlayers.setValue(2);
    choicePlayers.setTooltip(new Tooltip("Number of players"));
    choicePlayers.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
      public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
        amountOfPlayers = optionsOfPlayers[t1.intValue()];
      }
    });

    choicePlayers.setStyle("-fx-font: 22 arial; -fx-base: #c0c0c0;");

    choicePlayers.setMinSize(50, 50);

    startingGame.getChildren().addAll(buttonJoinGame, buttonNewGame);
    numbersOfPlayer.getChildren().addAll(information, choicePlayers);
    serverInformation.getChildren().addAll(ipInformation,ip);
    border.setTop(numbersOfPlayer);
    border.setBottom(startingGame);
    border.setCenter(serverInformation);

    border.setMargin(numbersOfPlayer, new Insets(50,20,12,50));
    border.setMargin(startingGame, new Insets(10,40,40,40));
    border.setMargin(serverInformation, new Insets(40,40,40,50));
        scene = new Scene(border, 400, 400);

    }

  public Scene getScene() {
    return scene;
  }
}



