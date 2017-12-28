package client.engine;

import common.*;
import client.net.Client;
import client.gui.DrawEngine;
import client.gui.Window;

public class Game {

  private DrawEngine drawEngine;
  private Client client;
  private Board board;
  private Cell selected = null;


  public Game(Window window) {
    drawEngine = new DrawEngine(window);
//    client = new Client("localhost");
  }

  public void startNewGame(int numberOfPlayers) {
   // boolean newGame = client.canStartNewGame(numberOfPlayers);
    boolean newGame = true;
    if (newGame) {
      board = ChineseCheckersBoardFactory.createBoard(numberOfPlayers);
      drawEngine.startGameGUI(Board.COLUMNS, Board.ROWS, board.board);
    }
  }

  //TODO:
  public void joinGame() {

  }

  //TODO:
  public void onCellSelected(double x, double y) {
     Point p = pixelToPoint(x, y);
     int q = (int)p.getQ();
     int r = (int)p.getR();

     if (board.board[q][r] != null) {

       drawEngine.selectCell(q, r);
       selected = board.board[q][r];
     }
    }

  public Point pixelToPoint(double x, double y) {
    x = (x - DrawEngine.WIDTH / 2) / DrawEngine.WIDTH;

    double t1 = y / DrawEngine.SIZE, t2 = Math.floor(x + t1);
    int r = (int)Math.floor((Math.floor(t1 - x) + t2) / 3);
    int q = (int)Math.floor((Math.floor(2 * x + 1) + t2) / 3) - r + 6;
    System.out.println("SELECTED " + q + ", " + r);

    return new Point(q, r);
  }

}
