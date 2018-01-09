package server;

import common.PlayerTag;

public class ComputerPlayer implements IPlayer{
  private Game game;
  private PlayerTag tag;

  public ComputerPlayer(Game game) {
    this.game = game;
  }
  @Override
  public GameMessage readGameMessage() {
      return null;
  }

  @Override
  public void setPlayerTag(PlayerTag tag) {

  }

  @Override
  public void writeGameMessage(GameMessage msg) {

  }

  @Override
  public void start() {

  }

  @Override
  public void close() {

  }

  @Override
  public PlayerTag getTag() {
    return tag;
  }

}
