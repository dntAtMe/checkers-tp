package server.messages;

import server.GameMessage;
import server.GameMessageType;

public class GameJoinMessage extends GameMessage {


  public GameJoinMessage() {
    super(GameMessageType.GAME_JOIN_MESSAGE);
  }
}
