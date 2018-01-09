package server.messages;

import server.GameMessage;
import server.GameMessageType;
import common.PlayerTag;

public class GameWonMessage extends GameMessage{

  private PlayerTag tag;

  public GameWonMessage(PlayerTag tag) {
    super(GameMessageType.GAME_WON_MESSAGE);
    this.tag = tag;
  }

  public PlayerTag getTag() {
    return tag;
  }
}
