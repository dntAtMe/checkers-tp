package server;

import server.messages.GameJoinMessage;
import server.messages.GameSetupMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class GameController {
  private static final Logger log = Logger.getLogger( GameController.class.getName());

  List<Game> games;

  public GameController() {
    games = new ArrayList<>();
  }


  public void handlePlayerConnecting(Player player) {
    GameMessage msg = player.readGameMessage();

    switch (msg.getGameMessageType()) {
      case GAME_SETUP_MESSAGE:
        handleGameSetup((GameSetupMessage) msg, player);
        break;
      case GAME_JOIN_MESSAGE:
        handleGameJoin((GameJoinMessage) msg, player);
        break;
    }
  }

  private void handleGameSetup(GameSetupMessage msg, Player player) {
    log.info("Game setup started");
  }

  private void handleGameJoin(GameJoinMessage msg, Player player) {
    log.info("Game join started");

  }

}
