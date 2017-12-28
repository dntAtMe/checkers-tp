package server.messages;

import common.Point;
import server.GameMessage;
import server.GameMessageType;

public class GameMovementMessage extends GameMessage {
  private Point start;
  private Point end;

  public GameMovementMessage() {
    super(GameMessageType.GAME_MOVEMENT_MESSAGE);
  }
}
