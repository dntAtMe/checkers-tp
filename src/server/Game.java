package server;

import common.Board;
import common.ChineseCheckersBoardFactory;
import client.PlayerTag;
import server.messages.GameLogMessage;
import server.messages.GameStatusMessage;
import server.messages.GameTurnMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
  private Board board;
  private List<Player> players;
  private int playersAmount;
  private int currentTurn;
  private Random random;

  public Game(int playersAmount) {
    players = new ArrayList<>();
    board = ChineseCheckersBoardFactory.createBoard(playersAmount);
    this.playersAmount = playersAmount;
    random = new Random();
  }

  //TODO: another thread?
  //  TEMPORARY METHOD
  public void broadcastMoveMessage(GameMessage msg, PlayerTag tag) {
    if (!isOnTurn(tag))
      return;
    for (Player player : players) {
      player.writeGameMessage(msg);
      player.writeGameMessage(new GameLogMessage(tag + " moved"));
    }
    advanceTurn();
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

  private void advanceTurn() {
    currentTurn = ((currentTurn + 1) % players.size()) + 1;
    broadcastTurnMessage(new GameTurnMessage(getActivePlayer()));
  }

  public int getMaxPlayersAmount() {
    return playersAmount;
  }
  public int getCurrentPlayersAmount() {return players.size(); }

  public PlayerTag getActivePlayer() { return PlayerTag.values()[currentTurn]; }
  public boolean isOnTurn(PlayerTag playerTag) {
    return PlayerTag.values()[currentTurn] == playerTag;
  }
  public boolean isFull() { return players.size() >= playersAmount;}

  public void addPlayer(Player player) {
    players.add(player);
  }

  public void start(){
    currentTurn = random.nextInt(playersAmount) + 1;
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


}
