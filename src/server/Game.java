package server;

import common.Board;
import common.ChineseCheckersBoardFactory;

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


  public int getMaxPlayersAmount() {
    return playersAmount;
  }
  public int getCurrentPlayersAmount() {return players.size(); }

  public boolean isOnTurn(Player player) {
    return players.indexOf(player) == currentTurn;
  }
  public boolean isFull() { return players.size() >= playersAmount;}

  public void addPlayer(Player player) {
    players.add(player);
  }

  public void start(){
    currentTurn = random.nextInt() % players.size();
    for (Player player : players) {
      player.start();
    }
  }

}
