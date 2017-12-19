package main.java.sample;

import java.nio.ByteBuffer;

public interface GameEvent {

    int getType();
    String getMessage();
    String getGameName();
    String getPlayerId();
    String getSessionId();
    String[] getRecipients();

    void setType(int type);
    void setGameName(String gameName);
    void setMessage(String message);
    void setPlayerId(String id);
    void setSessionId(String id);
    void setRecipients(String[] recipients);
    void read(ByteBuffer buff);
    int write(ByteBuffer buff);
    }

