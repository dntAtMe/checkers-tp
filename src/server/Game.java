package server;

import java.util.ArrayList;
import java.util.List;

public class Game {
  private Board board;
  private List<Player> players;
  private int playersAmount;
  private int currentTurn;

  public Game(int playersAmount) {
    players = new ArrayList<>();
  }


  public int getPlayersAmount() {
    return playersAmount;
  }

  public boolean isOnTurn(Player player) {
    return players.indexOf(player) == currentTurn;
  }
  public boolean isFull() { return players.size() >= playersAmount;}
}
