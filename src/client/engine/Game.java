package client.engine;

import client.Move;
import common.*;
import client.net.Client;
import client.gui.DrawEngine;
import client.gui.Window;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Game {

  private DrawEngine drawEngine;
  private Client client;
  private Board board;
  private Cell selected = null;
  private Cell change = null;
  private Move move;
  private Polygon polygons[][];
  boolean canMove;



  public Game(Window window) {
    drawEngine = new DrawEngine(window);
    client = new Client();
  }

  public void startNewGame(int numberOfPlayers) {
   // boolean newGame = client.canStartNewGame(numberOfPlayers);
    boolean newGame = true;
    if (newGame) {
      board = ChineseCheckersBoardFactory.createBoard(numberOfPlayers);
      drawEngine.startGameGUI(Board.COLUMNS, Board.ROWS, board.board);
    }
    move = new Move(board);

  }

  //TODO:
  public void joinGame() {

  }


  public void onCellEntered(double x, double y){
  /*
    Point point = pixelToPoint(x, y);
    int q = (int)point.getQ();
    int r = (int)point.getR();

    if(board.board[q][r]!=null)
      drawEngine.selectCell(q,r);
*/

  }

  public void onCellExited(double x, double y){

    /*
    Point point = pixelToPoint(x, y);
    int a = (int)point.getQ();
    int b = (int)point.getR();

    if(board.board[a][b]!=null)
      drawEngine.deselectCell(a,b);

*/
  }
  public void onCellSelected(double x, double y) {
     Point p = pixelToPoint(x, y);
     int q = (int)p.getQ();
     int r = (int)p.getR();

     if (selected == null) {
      if (board.board[q][r] != null) {
        selected = board.board[q][r];
        drawEngine.selectCell(q, r);
      }
     } else {
       change = board.board[q][r];
       moveSelected(selected,change);
     }
    }

    //TODO:
    public void moveSelected(Cell from, Cell to) {
           // if (canMove = client.canMove(from, to){

      if (from.getOwner() != PlayerTag.NONE && to.getOwner().equals(PlayerTag.NONE)) {
        if(move.canMove(from,to)){
          System.out.println("<o");
          drawEngine.onMove(from,to);
          selected = null;
        }
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
