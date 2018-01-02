package client.engine;

import common.Move;
import client.PlayerTag;
import common.*;
import client.net.Client;
import client.gui.DrawEngine;
import client.gui.Window;

public class Game {

  private DrawEngine drawEngine;
  private Client client;
  private Board board;
  private Cell selected = null;
  private Cell change = null;

  private Move move;

  boolean isOnTurn = false;
  PlayerTag tag;

  Thread clientThread;


  public Game(Window window) {

    drawEngine = new DrawEngine(window);

  }

  private void setUpGame(int numberOfPlayers) {
    board = ChineseCheckersBoardFactory.createBoard(numberOfPlayers);
    drawEngine.startGameGUI(Board.COLUMNS, Board.ROWS, board.board);
    drawEngine.createInformation(isOnTurn);
    clientThread.start();

    move = new Move(board);
  }

  private void setUpClient(String ipAddress) {
    client = new Client(ipAddress, this);
    clientThread = new Thread(client);
  }

  //TODO:
  public void startNewGame(int numberOfPlayers, String ipAddrees) {
    setUpClient(ipAddrees);
    boolean newGame = client.canStartNewGame(numberOfPlayers);

    if (newGame) {
      setUpGame(numberOfPlayers);
    }

  }

  //TODO:
  public void joinGame(int numberOfPlayers, String ipAddrees) {
    setUpClient(ipAddrees);
    boolean joinedGame = client.canJoinGame(numberOfPlayers);

    if(joinedGame) {
      setUpGame(numberOfPlayers);
    }
  }


  public void onCellEntered(double x, double y) {
/*
    Point point = pixelToPoint(x, y);
    int q = (int)point.getQ();
    int r = (int)point.getR();
     if (board.board[q][r] != null){
       tmp_2=board.board[q][r];
        drawEngine.selectCell(q, r);
     }
*/
  }

  public void onCellExited(double x, double y) {
/*
    Point point = pixelToPoint(x, y);
    int a = (int)point.getQ();
    int b = (int)point.getR();
    if(board.board[a][b]!=null){
      tmp=board.board[a][b];
      drawEngine.deselectCell(a,b);
    }
*/

  }

  //TODO: Fix change on NULL value
  public void onCellSelected(double x, double y) {

    if (!isOnTurn)
      return;
     Point p = pixelToPoint(x, y);
     int q = (int)p.getQ();
     int r = (int)p.getR();

    if (selected == null) {
      if (board.board[q][r] != null) {
        selected = board.board[q][r];
        //drawEngine.selectCell(q, r);
      }
    } else {
      change = board.board[q][r];
      attemptMove(selected, change);
      selected = null;
    }
  }

  public void onMoveSkipped() {
     client.attemptSkip();
  }

  private boolean attemptMove(Cell from, Cell to) {
    return client.attemptMove(from.getPoint(), to.getPoint());
  }

  //TODO:
  public void move(Point start, Point end) {
    Cell from = board.board[(int) start.getQ()][(int) start.getR()];
    Cell to = board.board[(int) end.getQ()][(int) end.getR()];

    to.setOwner(from.getOwner());
    from.setOwner(PlayerTag.NONE);
    drawEngine.onMove(from.getPoint(), to.getPoint(), to.getOwner());
    selected = null;
  }

  public Point pixelToPoint(double x, double y) {
    x = (x - DrawEngine.WIDTH / 2) / DrawEngine.WIDTH;

    double t1 = y / DrawEngine.SIZE, t2 = Math.floor(x + t1);
    int r = (int) Math.floor((Math.floor(t1 - x) + t2) / 3);
    int q = (int) Math.floor((Math.floor(2 * x + 1) + t2) / 3) - r + 6;

    return new Point(q, r);
  }


  public boolean isOnTurn() {
    return isOnTurn;
  }

  public void setOnTurn(boolean onTurn) {
    drawEngine.updateSkipOption(onTurn);
    drawEngine.updateTurnNotification(onTurn);
    isOnTurn = onTurn;
  }

  public PlayerTag getTag() {
    return tag;
  }

  public void setTag(PlayerTag tag) {
    this.tag = tag;
  }


  public void onGameEnded() {
      System.exit(1);
  }
}

