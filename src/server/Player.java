package server;

import client.PlayerTag;
import server.messages.GameLogMessage;
import server.messages.GameMovementMessage;
import server.messages.GameStatusMessage;
import server.messages.GameTurnMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Player extends Thread {
  private boolean             running;

  private Game                game;
  private ObjectInputStream   objectInputStream;
  private ObjectOutputStream  objectOutputStream;
  private Socket              socket;
  private PlayerTag           tag;

  public Player(Socket socket) throws IOException {
    this.socket = socket;
    try {
      objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectInputStream = new ObjectInputStream(socket.getInputStream());

    } catch (IOException e) {
      e.printStackTrace();
      throw new IOException(String.format("Player init failed with %s", e.getMessage()));
    }
  }

  public void run() {
    running = true;
    System.out.println("Player tag: " + tag.toString());
    writeGameMessage(new GameStatusMessage(tag));
    writeGameMessage(new GameLogMessage("All players connected!"));

    while (running) {
      GameMessage msg = readGameMessage();
      handleGameMessage(msg);
    }
  }

  void setGame(Game game) {
    this.game = game;
  }
  void setPlayerTag(PlayerTag tag) { this.tag = tag; }

  //TODO:
  private void handleGameMessage(GameMessage msg) {
    switch(msg.getGameMessageType()) {
      case GAME_MOVEMENT_MESSAGE:
        System.out.println("Moved");
        GameMovementMessage moveMsg = (GameMovementMessage)msg;
        if(game.canMove(moveMsg.getStart(), moveMsg.getEnd(), tag)) {
          game.broadcastMoveMessage(msg, tag);
          game.checkForWin(tag);
        }
        break;
      case GAME_LOG_MESSAGE:
        System.out.println(((GameLogMessage) msg).getDesc());
        break;
      case GAME_SKIP_MESSAGE:
        System.out.println("Skipping " + tag);
        game.canSkip(tag);
        break;
    }
  }

  public GameMessage readGameMessage() {
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

  public void writeGameMessage(GameMessage msg) {
    try {
      objectOutputStream.writeObject(msg);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public PlayerTag getTag() {
    return tag;
  }

    public void close() {
      running = false;
      try {
          objectOutputStream.close();
          objectInputStream.close();
          socket.close();
      } catch (IOException e) {
          e.printStackTrace();
      }
    }
}
