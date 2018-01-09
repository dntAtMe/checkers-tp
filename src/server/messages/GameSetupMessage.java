package server.messages;

import common.BoardType;
import server.GameMessage;
import server.GameMessageType;

public class GameSetupMessage extends GameMessage {
  private BoardType boardType;
  private int bots;
  private int players;

  public GameSetupMessage(BoardType boardType, int players) {
    this(boardType, players, 0);
  }

  public GameSetupMessage(BoardType boardType, int players, int bots) {
    super(GameMessageType.GAME_SETUP_MESSAGE);
    this.boardType = boardType;
    this.players = players;
    this.bots = bots;
  }

  public BoardType getBoardType() {
    return boardType;
  }

  public int getPlayers() {
    return players;
  }

  public int getBots() {
    return bots;
  }
}
