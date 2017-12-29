package server.messages;

import client.PlayerTag;
import server.GameMessage;
import server.GameMessageType;

public class GameTurnMessage extends GameMessage {

  private PlayerTag onTurn;

  public GameTurnMessage(PlayerTag onTurn) {
    super(GameMessageType.GAME_TURN_MESSAGE);
    this.onTurn = onTurn;
  }

  public PlayerTag getOnTurn() {
    return onTurn;
  }
}
