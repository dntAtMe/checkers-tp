package client.engine;

import javafx.scene.input.MouseEvent;
import client.gui.Window;

public class InputHandler {

  Game game;

  public InputHandler(Window window) {
    game = new Game(window);
  }

  public void onNewGameSelected(int numberOfPlayers, int amountOfBots, String ipAddress) {
    game.startNewGame(numberOfPlayers, amountOfBots, ipAddress);
  }

  public void onJoinGameSelected(int numberOfPlayers, String ipAddress) {
    game.joinGame(numberOfPlayers, ipAddress);
  }

  public void onMouseClicked(MouseEvent e) {

    game.onCellSelected(e.getX(), e.getY());
  }
  public void onMouseEntered(MouseEvent en ){

    game.onCellEntered(en.getX(), en.getY());
  }
  public void onMouseExited(MouseEvent ex) {

    game.onCellExited(ex.getX(), ex.getY());
  }

  public void onMoveSkipped() {
    game.onMoveSkipped();
  }
}
