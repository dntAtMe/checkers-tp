package server;

import server.messages.GameJoinMessage;
import server.messages.GameSetupMessage;

import java.util.List;

public class GameController {
  List<Game> games;


  public void handlePlayerConnecting(Player player) {
    GameMessage msg = player.readGameMessage();
  }

  private void handleGameSetup(GameSetupMessage msg, Player player) {

  }

  private void handleGameJoin(GameJoinMessage msg, Player player) {

  }

}
