package server.messages;

import common.BoardType;
import server.GameMessage;
import server.GameMessageType;

public class GameSetupMessage extends GameMessage {
  private BoardType boardType;
  private int players;

  public GameSetupMessage(BoardType boardType, int players) {
    super(GameMessageType.GAME_SETUP_MESSAGE);
    this.boardType = boardType;
    this.players = players;
  }

  public BoardType getBoardType() {
    return boardType;
  }

  public int getPlayers() {
    return players;
  }
}
