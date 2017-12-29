package server.messages;

import client.PlayerTag;
import server.GameMessage;
import server.GameMessageType;

public class GameStatusMessage extends GameMessage{

  private PlayerTag tag;

  public GameStatusMessage(PlayerTag tag) {
    super(GameMessageType.GAME_STATUS_MESSAGE);
    this.tag = tag;
  }

  public PlayerTag getTag() {
    return tag;
  }
}
