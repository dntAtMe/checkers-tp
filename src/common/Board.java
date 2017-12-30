package common;

import client.PlayerTag;

import java.util.HashMap;
import java.util.Map;

public class Board {
  public static final int ROWS = 17;
  public static final int COLUMNS = 25;
  public final Cell[][] board;
  public final Point[] diagonalVectors = {
          new Point(2, -1), new Point (1, 1),
          new Point (-1, 2), new Point (-2, 1),
          new Point (-1, -1), new Point (1, -2)
  };

  public Board(Cell[][] board) {
    this.board = board;
  }

  public boolean isTaken(Point point) {
    return isTaken((int) point.getQ(), (int) point.getR());
  }

  public boolean isTaken(int x, int y) {
    Cell cell = board[x][y];

    if (cell == null || cell.getOwner() == PlayerTag.NONE)
      return false;
    return true;
  }

  public Cell getCell (Point point) {
    return board[(int) point.getQ()][(int) point.getR()];
  }

  public boolean isDiagonal(Point point) {
    for (Point p : diagonalVectors) {
      if (point.equals(p))
              return true;
    }
    return false;
  }
}
