package main.java.sample;

import main.java.sample.Player;

import java.nio.channels.SocketChannel;

public class PlayerDefault implements Player {

    private String playerId;
    private String sessionId;
    private SocketChannel channel;
    private boolean loggedIn;
    private boolean inGame;
    private int gameId;

    public String getPlayerId() {
        return playerId;
    }
    public void setPlayerId(String id) {
        playerId = id;
    }

    public String getSessionId() {
        return sessionId;
    }
    public void setSessionId(String id) {
        sessionId = id;
    }

    public SocketChannel getChannel() {
        return channel;
    }
    public void setChannel(SocketChannel channel) {
        this.channel = channel;
    }

    public boolean loggedIn() {
        return loggedIn;
    }
    public void setLoggedIn(boolean in) {
        loggedIn = in;
    }

    public boolean inGame() {
        return inGame;
    }
    public void setInGame(boolean in) {
        inGame = in;
    }

    public int getGameId() {
        return gameId;
    }
    public void setGameId(int gid) {
        gameId = gid;
    }
}

