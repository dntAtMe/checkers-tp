package main.java.sample;

import java.nio.channels.SocketChannel;

public interface Player {
    String getPlayerId();
    String getSessionId();
    SocketChannel getChannel();
    boolean loggedIn();
    boolean inGame();
    int getGameId();

    void setPlayerId(String id);
    void setSessionId(String id);
    void setChannel(SocketChannel channel);
    void setLoggedIn(boolean in);
    void setInGame(boolean in);
    void setGameId(int gid);
}
