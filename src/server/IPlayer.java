package server;

import common.PlayerTag;

public interface IPlayer {
  GameMessage readGameMessage();
  void setPlayerTag(PlayerTag tag);
  void writeGameMessage(GameMessage msg);

  void start();
  void close();

  PlayerTag getTag();

}
