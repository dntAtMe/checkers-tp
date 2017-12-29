package common;

import java.util.HashMap;
import java.util.Map;

public class Board {
  public static final int ROWS = 17;
  public static final int COLUMNS = 25;
  public  Map<PlayerTag,Cell[]> lastPosition = new HashMap<>();
  public final Cell[][] board;

  public Board(Cell[][] board, Map<PlayerTag,Cell[]> lastPosition) {
    this.board = board;
    this.lastPosition=lastPosition;
  }
}
