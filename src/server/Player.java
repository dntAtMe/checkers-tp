package server;

import java.io.*;
import java.net.Socket;

public class Player implements Runnable {
  private ObjectInputStream objectInputStream;
  private ObjectOutputStream objectOutputStream;
  private Game game;
  private Socket socket;

  public Player(Socket socket) throws IOException {
    this.socket = socket;
    try {
      objectInputStream = new ObjectInputStream(socket.getInputStream());
      objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

    } catch (IOException e) {
      e.printStackTrace();
      throw new IOException(String.format("Player init failed with %s", e.getMessage()));
    }
  }

  public void run() {

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
