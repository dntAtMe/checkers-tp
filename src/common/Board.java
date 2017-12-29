package common;

import java.util.HashMap;
import java.util.Map;

public class Board {
  public static final int ROWS = 17;
  public static final int COLUMNS = 25;
  public final Cell[][] board;

  public Board(Cell[][] board) {
    this.board = board;
  }
}
