package server.messages;

import server.GameMessage;
import server.GameMessageType;

public class GameSkipMessage extends GameMessage {


    public GameSkipMessage() {
        super(GameMessageType.GAME_SKIP_MESSAGE);
    }
}
