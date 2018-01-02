package server;

import common.BoardType;
import server.messages.GameAnswerMessage;
import server.messages.GameBoardMessage;
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

  //TODO:
  private void handleGameSetup(GameSetupMessage msg, Player player) {
    log.info("Game setup started");
    Game game = new Game(msg.getPlayers());
    game.addPlayer(player);
    player.setGame(game);
    games.add(game);
    player.writeGameMessage(new GameAnswerMessage(true, "Game created!"));
  }

  //TODO:
  private void handleGameJoin(GameJoinMessage msg, Player player) {
    log.info("Game join started | " + msg.getPlayers());
    Game game = findMatchingGame(msg.getBoardType(), msg.getPlayers());
    if ( game != null) {
      log.info("Found a matching game!");
      game.addPlayer(player);
      player.setGame(game);
      player.writeGameMessage(new GameAnswerMessage(true, "Joined!"));
      startGameIfFull(game);
    } else {
      log.info("No matching game found!");
      player.writeGameMessage(new GameAnswerMessage(false, "No matching game!"));
    }
  }

  private void startGameIfFull(Game game) {
    log.info("Game full, starting players' threads!");
    if(game.isFull()) {
      game.start();
    }
  }

  private Game findMatchingGame(BoardType type, int playersAmount) {
    for (Game game : games) {
      log.info("Looking for a game");
      if (game.getMaxPlayersAmount() == playersAmount && !game.didStart()) {
        return game;
      }
    }
    return null;
  }

}
