package server;

import common.Move;
import common.Board;
import common.ChineseCheckersBoardFactory;
import client.PlayerTag;
import common.Point;
import server.messages.GameLogMessage;
import server.messages.GameStatusMessage;
import server.messages.GameTurnMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
  private Board board;
  private Move move;
  private List<Player> players;
  private int playersAmount;
  private int currentTurn;
  private Random random;
  private boolean started;

  public PlayerTag lastPlayer = null;


  public Game(int playersAmount) {
    players = new ArrayList<>();
    board = ChineseCheckersBoardFactory.createBoard(playersAmount);
    move = new Move(board);
    this.playersAmount = playersAmount;
    random = new Random();
  }

  //TODO: another thread?
  //  TEMPORARY METHOD
  public void broadcastMoveMessage(GameMessage msg, PlayerTag tag) {
    for (Player player : players) {
      player.writeGameMessage(msg);
      player.writeGameMessage(new GameLogMessage(tag + " moved"));
    }
   // advanceTurn();
  }

  public void broadcastStatusMessage() {
    for (Player player : players) {
      player.writeGameMessage(new GameStatusMessage(player.getTag()));
    }
  }

  public void broadcastTurnMessage(GameMessage msg) {
    for (Player player : players) {
      player.writeGameMessage(msg);
      player.writeGameMessage(new GameLogMessage("current turn: " + getActivePlayer()));
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

  public boolean checkForWin(PlayerTag askingTag) {
    List<Point> points = board.endingCells.get(askingTag);
    for (Point p : points) {
      if (board.getCell(p).getOwner() != askingTag)
        return false;
    }
    kickPlayer(askingTag);
    return true;
  }

  private void kickPlayer(PlayerTag tag) {
    System.out.println("kicking");
    Player player = getPlayerByTag(tag);
    if (player != null) {
      player.close();
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
  public int getCurrentPlayersAmount() {return players.size(); }

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
    for (Player player : players) {
      player.setPlayerTag(PlayerTag.values()[1 + players.indexOf(player)]);
    }
    System.out.println(getActivePlayer().toString());
    broadcastStatusMessage();
    System.out.println(getActivePlayer().toString());
    broadcastTurnMessage(new GameTurnMessage(getActivePlayer()));
    for (Player player : players) {
      player.start();
    }
  }

  public boolean didStart() {
    return started;
  }

  private Player getPlayerByTag(PlayerTag tag) {
    for (Player player : players) {
      if (player.getTag() == tag)
        return player;
    }
    return null;
  }

  public void setLastPlayer(PlayerTag lastPlayer){
    this.lastPlayer=lastPlayer;
  }

}
