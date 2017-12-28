package server;

import java.io.Serializable;

public abstract class GameMessage implements Serializable {
  private GameMessageType gameDataType;

  public GameMessage(GameMessageType gameDataType) {
    this.gameDataType = gameDataType;
  }

  public GameMessageType getGameMessageType() {
    return gameDataType;
  }
}
