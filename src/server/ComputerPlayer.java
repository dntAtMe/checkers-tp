package server;

import common.PlayerTag;

public class ComputerPlayer implements IPlayer{
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
    return null;
  }
}
