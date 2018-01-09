package common;

import client.PlayerTag;

import java.util.HashMap;
import java.util.List;
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
  public final Map<PlayerTag, List<Point>> endingCells;

  public Board(Cell[][] board, Map<PlayerTag, List<Point>> endingCells) {
    this.board = board;
    this.endingCells = endingCells;
  }

  public boolean isTaken(Point p) {
    return isTaken(p.getQ(),p.getR());
  }

  public boolean isTaken(int x, int y) {
    Cell cell = board[x][y];
    if (cell == null || cell.getOwner() == PlayerTag.NONE)
      return false;
    return true;
  }

  public void setCellEmpty(Point p) {
    getCell(p).setOwner(PlayerTag.NONE);
  }

  public void copyOwner(Point target, Point source) {
    getCell(target).setOwner(getCell(source).getOwner());
  }

  public Cell getCell (Point p) {
    return board[p.getQ()][p.getR()];
  }

  public boolean isDiagonal(Point point) {
    for (Point p : diagonalVectors) {
      if (point.equals(p))
              return true;
    }
    return false;
  }


  public Point getTmpPoint(){
    return point;
  }
  public void setTmpPoint(Point point){
    this.point=point;
  }

}
