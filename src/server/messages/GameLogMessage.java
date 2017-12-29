package server.messages;

import server.GameMessage;
import server.GameMessageType;

public class GameLogMessage extends GameMessage{
  private final String desc;

  public GameLogMessage(String desc) {
    super(GameMessageType.GAME_LOG_MESSAGE);
    this.desc = desc;
  }

  public String getDesc() {
    return desc;
  }
}
