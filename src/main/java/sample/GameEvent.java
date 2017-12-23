package main.java.sample;

import java.nio.ByteBuffer;

public interface GameEvent {

    int getType();
    String getMessage();
    String getGameName();
    String getPlayerId();
    String[] getRecipients();
    void setGameName(String gameName);
    void setPlayerId(String id);
    void setRecipients(String[] recipients);
    void read(ByteBuffer buff);
    int write(ByteBuffer buff);
    }

