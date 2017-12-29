package client.gui;

import com.sun.prism.paint.Color;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import client.engine.InputHandler;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MenuScene {

  private Button buttonNewGame;
  private Button buttonJoinGame;
  private ChoiceBox choicePlayers;
  private ChoiceBox choiceBoot;
  private InputHandler inputHandler;
  BootBox box;
  int amountOfPlayers = 2;
  int amountOfBoot = 0;
  int[] optionsOfPlayers = {2, 3, 4, 6};
  private Scene scene;

  public MenuScene(InputHandler inputHandler, int width, int height) {
    BorderPane border = new BorderPane();
    border.setStyle("-fx-background-color:   #708090;");
    HBox startingGame = new HBox();
    startingGame.setSpacing(40.0);

    HBox numbersOfPlayer = new HBox();
    numbersOfPlayer.setSpacing(60.0);

    Text information = new Text("Choose number \n of players");

    buttonNewGame = new Button("New game");
    buttonNewGame.setStyle("-fx-font: 22 arial; -fx-base: #c0c0c0;");
    buttonNewGame.setOnAction(actionEvent -> {
      inputHandler.onNewGameSelected(amountOfPlayers);
      box = new BootBox(amountOfPlayers);
      amountOfBoot=box.display();
      System.out.println("Ilosc bootow: " +amountOfBoot);
    });

    buttonNewGame.setMinSize(100, 100);

    buttonJoinGame = new Button("Join game");
    buttonJoinGame.setStyle("-fx-font: 22 arial; -fx-base: #c0c0c0;");

    buttonJoinGame.setOnAction(actionEvent -> {
      inputHandler.onJoinGameSelected(amountOfPlayers);
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
    information.setFont(Font.font(java.awt.Font.DIALOG, 20));

    startingGame.getChildren().addAll(buttonJoinGame, buttonNewGame);
    numbersOfPlayer.getChildren().addAll(information, choicePlayers);
    border.setTop(numbersOfPlayer);
    border.setBottom(startingGame);

    border.setMargin(numbersOfPlayer, new Insets(50,20,12,50));
    border.setMargin(startingGame, new Insets(10,40,40,40));

        scene = new Scene(border, 400, 300);

    }

  public Scene getScene() {
    return scene;
  }
}



