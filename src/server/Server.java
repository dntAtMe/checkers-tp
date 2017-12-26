package server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
  private final static short PORT = 8000;
  public static final String ON_START_MSG = "starting";
  private boolean running;
  private GameController gameController;
  private ServerSocket serverSocket;

  public void start() {
    try {
      serverSocket = new ServerSocket(PORT);
      gameController = new GameController();

      while(running) {
        Player player = new Player(serverSocket.accept());
        System.out.println("New player joined, handling...");
        gameController.handlePlayerConnecting(player);
      }

      System.out.println(ON_START_MSG);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        serverSocket.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
