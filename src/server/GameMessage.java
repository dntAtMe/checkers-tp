package server;

public abstract class GameMessage {
  private GameMessageType gameDataType;

  public GameMessage(GameMessageType gameDataType) {
    this.gameDataType = gameDataType;
  }

  public GameMessageType getGameDataType() {
    return gameDataType;
  }
}
