package client.net;

import client.engine.Game;
import common.*;
import common.Point;
import server.GameMessage;
import server.GameMessageType;
import server.messages.*;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Logger;

public class Client implements Runnable{
    private static final Logger log = Logger.getLogger( Client.class.getName());

    public static int PORT = 8000;
    private Socket socket;
    private String serverAddress;

    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    Game game;

    public Client(String serverAddress, Game game) {
      this.serverAddress = serverAddress;
      this.game = game;

      try {
        socket = new Socket(serverAddress, PORT);
      } catch (IOException e) {
        e.printStackTrace();
      }

      try {
        log.info("creating socket I/O");
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectInputStream = new ObjectInputStream(socket.getInputStream());
        log.info("Done creating socket I/O");

      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    //TODO:
    @Override
    public void run() {
      while(true) {
//        if(game.isOnTurn())
 //         continue;
        GameMessage msg = readGameMessage();
        handleGameMessage(msg);
        log.info("CLIENT: " + game.getTag() + ", Current turn: " + game.isOnTurn());

      }
    }

    private void handleGameMessage(GameMessage msg) {
      switch(msg.getGameMessageType()) {
        case GAME_MOVEMENT_MESSAGE:
          GameMovementMessage convertedMsg = (GameMovementMessage) msg;
          game.move(convertedMsg.getStart(), convertedMsg.getEnd());
          break;
        case GAME_LOG_MESSAGE:
          log.info(((GameLogMessage) msg).getDesc());
          break;
        case GAME_STATUS_MESSAGE:
          log.info("Got status message: " + ((GameStatusMessage)msg).getTag().toString());
          game.setTag(((GameStatusMessage)msg).getTag());
          break;
        case GAME_TURN_MESSAGE:
          GameTurnMessage turnMsg = (GameTurnMessage) msg;
          log.info("Got turn message: " + turnMsg.getOnTurn().toString());
          if (turnMsg.getOnTurn() == game.getTag())
            game.setOnTurn(true);
          else
            game.setOnTurn(false);
          break;
      }
    }

    //TODO:
    public boolean attemptMove(Point from, Point to) {
      log.info("Moving");
      sendGameMessage(new GameMovementMessage(from, to));
      return true;
    }

    public boolean attemptSkip() {
        log.info("Skipping");
        sendGameMessage(new GameSkipMessage());
        return true;
    }

    public boolean canStartNewGame(int numberOfPlayers) {
      sendNewGameMessage(numberOfPlayers);
      GameAnswerMessage msg = readNewAnswerMessage();
      log.info(msg.getDesc() + ", " + msg.getAnswer());
      if(msg != null && msg.getAnswer() == true) {
        return true;
      }
      return false;
    }

    //TODO:
    public boolean canJoinGame(int numberOfPlayers) {
        sendGameMessage(new GameJoinMessage(BoardType.BOARD_STAR, numberOfPlayers));
        GameAnswerMessage msg = readNewAnswerMessage();
        log.info(msg.getDesc() + ", " + msg.getAnswer());
        if(msg != null && msg.getAnswer() == true) {
            return true;
        }
        return false;
    }

    private void sendNewGameMessage(int numberOfPlayers) {
      GameSetupMessage msg = new GameSetupMessage(BoardType.BOARD_STAR, numberOfPlayers);
      try {
        objectOutputStream.writeObject(msg);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    private void sendGameMessage(GameMessage msg) {
      try {
        objectOutputStream.writeObject(msg);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    private GameMessage readGameMessage() {
      GameMessage msg = null;
      try {
        msg = (GameMessage) objectInputStream.readObject();
      } catch (IOException e) {
        e.printStackTrace();
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }

      return msg;
    }


    private GameAnswerMessage readNewAnswerMessage() {
      GameMessage msg = readGameMessage();

      if (msg.getGameMessageType() == GameMessageType.GAME_ANSWER_MESSAGE)
        return (GameAnswerMessage) msg;
      else
        return null;
    }




}
