package server;

import java.net.ServerSocket;

public class ServerCreator {
  private ServerSocket serverSocket;
  private GameController gameController;

  public static void main(String[] args) {
    Server server = new Server();
    server.start();
  }

}
