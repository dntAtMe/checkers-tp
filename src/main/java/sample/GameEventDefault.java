package main.java.sample;

import main.java.sample.GameEvent;

import java.nio.ByteBuffer;

 // przykladowa implemetacja
 // taka z dupy
public class GameEventDefault implements GameEvent {

    // C - client
    // S - server

    public static final int C_LOGIN = 1001;
    public static final int S_LOGIN = 1004;
    public static final int C_LOGOUT = 1005;
    public static final int S_LOGOUT = 1006;
    public static final int C_JOIN_GAME = 1101;
    public static final int S_PLAYER_JOINED = 1104;
    public static final int C_QUIT_GAME = 1105;
    public static final int S_PLAYER_QUIT = 1106;
    public static final int C_MOVE = 1301;
    public static final int S_MOVE_ACK_OK = 1302;
    public static final int S_MOVE_ACK_FAIL = 1303;
    public static final int S_ROUND_COMPLETE = 1304;
    public static final int S_GAME_OVER = 1305;

    protected int eventType;
    protected String playerId;
    protected String sessionId;
    protected int gameId = -1;
    protected String  gameName;
    protected int numRecipients;
    protected String[] recipients;
    protected String message;

    public GameEventDefault() {

    }

    public GameEventDefault(int type) {
        this.eventType = type;
    }

    public GameEventDefault(int type, String message){
        this.eventType = type;
        this.message = message;
    }

    public void setType(int type) {
        eventType = type;
    }
    public int getType() {
        return eventType;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }
    public String getGameName() {
        return gameName;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

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

    public String[] getRecipients() {
        return recipients;
    }
    public void setRecipients(String[] recipients) {
        this.recipients = recipients;
        numRecipients = recipients.length;
    }

    public int write(ByteBuffer buff) {
        int pos = buff.position();

        buff.putInt(eventType);
        NIOUtils.putStr(buff, playerId);
        NIOUtils.putStr(buff, sessionId);
        buff.putInt(gameId);
        NIOUtils.putStr(buff, gameName);
        buff.putInt(numRecipients);
        for (int i=0;i<numRecipients;i++)
            NIOUtils.putStr(buff, recipients[i]);
        NIOUtils.putStr(buff, message);

        return buff.position() - pos;
    }

    public void read(ByteBuffer buff) {
        eventType = buff.getInt();
        playerId = NIOUtils.getStr(buff);
        sessionId = NIOUtils.getStr(buff);
        gameId = buff.getInt();
        gameName = NIOUtils.getStr(buff);
        numRecipients = buff.getInt();
        recipients = new String[numRecipients];
        for (int i=0;i<numRecipients;i++)
            recipients[i] = NIOUtils.getStr(buff);
        message = NIOUtils.getStr(buff);
    }
}