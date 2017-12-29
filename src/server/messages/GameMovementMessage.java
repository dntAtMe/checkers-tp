package server.messages;

import common.Point;
import server.GameMessage;
import server.GameMessageType;

public class GameMovementMessage extends GameMessage {
  private Point start;
  private Point end;

  public GameMovementMessage(Point from, Point to) {
    super(GameMessageType.GAME_MOVEMENT_MESSAGE);
    this.start = from;
    this.end = to;
  }

  public Point getStart() {
    return start;
  }

  public Point getEnd() {
    return end;
  }
}
