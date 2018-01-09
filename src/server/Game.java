package server;

import common.Move;
import common.Board;
import common.ChineseCheckersBoardFactory;
import common.PlayerTag;
import common.Point;
import server.messages.GameLogMessage;
import server.messages.GameStatusMessage;
import server.messages.GameTurnMessage;
import server.messages.GameWonMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
  private Board board;
  private Move move;
  private List<IPlayer> players;
  private int playersAmount;
  private int botsAmount;
  private int currentTurn;
  private Random random;
  private boolean started;

  public PlayerTag lastPlayer = null;


  public Game(int playersAmount, int botsAmount) {
    players = new ArrayList<>();
    board = ChineseCheckersBoardFactory.createBoard(playersAmount);
    move = new Move(board);
    this.playersAmount = playersAmount;
    this.botsAmount = botsAmount;
    random = new Random();

    prepareBots();
  }

  //TODO: another thread?
  //  TEMPORARY METHOD
  public void broadcastMoveMessage(GameMessage msg, PlayerTag tag) {
    for (IPlayer player : players) {
      player.writeGameMessage(msg);
      player.writeGameMessage(new GameLogMessage(tag + " moved"));
    }
   // advanceTurn();
  }

  public void broadcastStatusMessage() {
    for (IPlayer player : players) {
      player.writeGameMessage(new GameStatusMessage(player.getTag()));
    }
  }

  public void broadcastTurnMessage(GameMessage msg) {
    for (IPlayer player : players) {
      player.writeGameMessage(msg);
      player.writeGameMessage(new GameLogMessage("current turn: " + getActivePlayer()));
    }
  }

  public void broadcastWinMessage(PlayerTag tag) {
    GameMessage msg = new GameWonMessage(tag);
    for (IPlayer player : players) {
      player.writeGameMessage(msg);
      player.writeGameMessage(new GameLogMessage(tag + " won!"));
    }
  }

  public boolean canSkip(PlayerTag tag) {
    if (isOnTurn(tag)) {
      advanceTurn();
      return true;
    }
    return false;
  }

  public boolean canMove(Point from, Point to, PlayerTag askingTag) {

   if(getActivePlayer()!=askingTag)
     return false;

   if(askingTag==PlayerTag.NONE)
     return false;

    if (move.canMove(from, to, askingTag,getLastPlayer())) {
        move.makeMove(from, to, askingTag);
        setLastPlayer(askingTag);
        checkTurn(askingTag);
        return true;
      }
      checkTurn(askingTag);
      return false;
  }

  public void checkTurn(PlayerTag tag){
    if(isTurnFinished()){
      advanceTurn();
    }
  }
  public boolean isTurnFinished(){
    return move.isTurnFinished();
  }

  public void checkForWin(PlayerTag askingTag) {
    if (didWin(askingTag)) {
      broadcastWinMessage(askingTag);
      if (players.size() < 2) {
        endGame();
      }
    }
  }

  private boolean didWin (PlayerTag askingTag) {
    List<Point> points = board.endingCells.get(askingTag);
    for (Point p : points) {
      if (board.getCell(p).getOwner() != askingTag)
        return false;
    }
    return true;
  }

  public void onLostConnection(Player p) {
    kickPlayer(p.getTag());
    if (players.size() < 2) {
      broadcastWinMessage(players.get(0).getTag());
      endGame();
    } else {
      if(isOnTurn(p.getTag()))
        advanceTurn();
    }
  }

  private void endGame() {
    GameController.getInstance().endGame(this);
  }

  protected void kickPlayer(PlayerTag tag) {
    System.out.println("kicking");
    IPlayer player = getPlayerByTag(tag);
    if (player != null) {
      players.remove(player);
    }
  }

  private void advanceTurn() {
    lastPlayer=null;
    currentTurn = (currentTurn % players.size())+1;
   broadcastTurnMessage(new GameTurnMessage(getActivePlayer()));
  }

  public int getMaxPlayersAmount() {
    return playersAmount;
  }

  public PlayerTag getActivePlayer() {
    return PlayerTag.values()[currentTurn];}
  public PlayerTag getLastPlayer() { return lastPlayer;}
  public boolean isOnTurn(PlayerTag playerTag) {
    return PlayerTag.values()[currentTurn] == playerTag;
  }
  public boolean isFull() { return players.size() >= playersAmount;}

  public void addPlayer(Player player) {
    players.add(player);
  }

  public void start(){
    started = true;

    currentTurn = random.nextInt(playersAmount)+1;
    for (IPlayer player : players) {
      player.setPlayerTag(PlayerTag.values()[1 + players.indexOf(player)]);
    }
    System.out.println(getActivePlayer().toString());
    broadcastStatusMessage();
    System.out.println(getActivePlayer().toString());
    broadcastTurnMessage(new GameTurnMessage(getActivePlayer()));
    for (IPlayer player : players) {
      player.start();
    }
  }

  private void prepareBots() {
    for(int i = 0; i < botsAmount; i++) {
      System.out.println("Got a PC homie!");
      players.add(new ComputerPlayer(this));
    }
  }

  public boolean didStart() {
    return started;
  }

  private IPlayer getPlayerByTag(PlayerTag tag) {
    for (IPlayer player : players) {
      if (player.getTag() == tag)
        return player;
    }
    return null;
  }

  public void setLastPlayer(PlayerTag lastPlayer){
    this.lastPlayer=lastPlayer;
  }
}