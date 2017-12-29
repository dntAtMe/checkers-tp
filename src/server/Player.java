package server;

import server.messages.GameAnswerMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Player extends Thread {
  private ObjectInputStream objectInputStream;
  private ObjectOutputStream objectOutputStream;
  private Game game;
  private Socket socket;
  private boolean running;

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
    while (running) {

    }
  }

  void setGame(Game game) {
    this.game = game;
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


}
