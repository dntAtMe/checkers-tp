package common;


import client.PlayerTag;

public class Move {
    private Board board;


    public Move(Board board){
        this.board=board;
    }

    public void makeMove(Point start, Point end, PlayerTag tag) {
      board.copyOwner(end, start);
      board.setCellEmpty(start);
    }

    public boolean canMove(Point from, Point to, PlayerTag askingTag) {
      if (board.getCell(from).getOwner() != askingTag)
        return false;
      if (board.isTaken(to))
        return false;
  //    if (board.isInEndingZone(from, askingTag) && !board.isInEndingZone(to, askingTag))
   //       return false;
      if (distance(from, to) <= 2) {
        if (((distance(from, to) == 2) && isPointBetween(from, to))){
          return true;
        }
        else if(distance(from, to) == 1) {
          return true;
        }
      }
      return false;
    }

    public boolean isPointBetween(Point a, Point b) {
      Point vector = Point.substract(b, a);
      if(!board.isDiagonal(vector))
        return true;
      return false;

    }

    public double distance(Point a, Point b) {
        return (Math.abs(a.getQ() - b.getQ()) +
                Math.abs(a.getR() - b.getR()) +
                Math.abs(a.getS() - b.getS())
                ) / 2;
    }


}



