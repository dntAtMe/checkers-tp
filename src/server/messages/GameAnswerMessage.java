package server.messages;

import server.GameMessage;
import server.GameMessageType;

public class GameAnswerMessage extends GameMessage{
  private boolean answer;
  private String desc;

  public GameAnswerMessage(boolean answer) {
    this(answer, null);
  }

  public GameAnswerMessage(boolean answer, String desc) {
    super(GameMessageType.GAME_ANSWER_MESSAGE);
    this.answer = answer;
    this.desc = desc;
  }

  public boolean getAnswer() {
    return answer;
  }

  public String getDesc() {
    return desc;
  }
}
