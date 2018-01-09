package server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
  private final static short PORT = 8000;
  public static final String ON_START_MSG = "starting";
  private boolean running;
  private ServerSocket serverSocket;

  public void start() {
    running = true;
    try {
      serverSocket = new ServerSocket(PORT);


      //TODO: Loop player handling, since it crashes on failed join attempt.
      while(running) {
        System.out.println("Looking for a player...");
        Player player = new Player(serverSocket.accept());
        System.out.println("New player joined, handling...");
        GameController.getInstance().handlePlayerConnecting(player);
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
