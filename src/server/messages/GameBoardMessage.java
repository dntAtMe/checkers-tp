package server.messages;

import server.GameMessage;
import server.GameMessageType;

public class GameBoardMessage extends GameMessage{

  public GameBoardMessage() {
    super(GameMessageType.GAME_BOARD_MESSAGE);
  }
}
