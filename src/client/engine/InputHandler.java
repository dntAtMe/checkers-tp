package client.engine;

import javafx.scene.input.MouseEvent;
import client.gui.Window;

public class InputHandler {

  Game game;

  public InputHandler(Window window) {
    game = new Game(window);
  }

  public void onNewGameSelected(int numberOfPlayers) {
    game.startNewGame(numberOfPlayers);
  }

  public void onJoinGameSelected() {

  }

  public void onMouseClicked(MouseEvent e) {
    game.onCellSelected(e.getX(), e.getY());
  }

}
