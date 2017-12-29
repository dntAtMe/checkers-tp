package client.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import client.engine.InputHandler;

public class MenuScene {

  private Scene scene;

  private Button buttonNewGame;
  private Button buttonJoinGame;
  private ChoiceBox choicePlayers;
  private InputHandler inputHandler;

  int amountOfPlayers = 2;
  int[] optionsOfPlayers = {2, 3, 4, 6};

  public MenuScene(InputHandler inputHandler, int width, int height) {
    buttonNewGame = new Button("New game");
    buttonNewGame.setOnAction(actionEvent -> {
      inputHandler.onNewGameSelected(amountOfPlayers);
    });

    buttonJoinGame = new Button("Join game");
    buttonJoinGame.setOnAction(actionEvent -> {
      inputHandler.onJoinGameSelected(amountOfPlayers);
    });

    choicePlayers = new ChoiceBox(FXCollections.observableArrayList(2, 3, 4, 6));
    choicePlayers.setValue(2);
    choicePlayers.setTooltip(new Tooltip("Number of players"));
    choicePlayers.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
      public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
        amountOfPlayers = optionsOfPlayers[t1.intValue()];
      }
    });

    VBox layout = new VBox(20);
    layout.getChildren().addAll(choicePlayers, buttonNewGame, buttonJoinGame);

    scene = new Scene(layout, 200, 400);
  }

  public Scene getScene() {
    return scene;
  }
}


