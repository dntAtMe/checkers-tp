package common;


import client.PlayerTag;
import common.Board;
import common.Cell;
import common.Point;

public class Move {
    Board board;
    private Cell found;
    int q,r;


    public Move(Board board){
        this.board=board;
        found = new Cell();

    }

    public void makeMove(Point start, Point end) {
      Cell from = board.board[(int) start.getQ()][(int) start.getR()];
      Cell to = board.board[(int) end.getQ()][(int) end.getR()];

      to.setOwner(from.getOwner());
      from.setOwner(PlayerTag.NONE);
    }

    public boolean canMove(Point from, Point to, PlayerTag askingTag) {
      return canMove(board.getCell(from), board.getCell(to), askingTag);
    }

    private boolean canMove(Cell from, Cell to, PlayerTag askingTag) {
      System.out.println("canMove");
      if (from.getOwner() != askingTag)
        return false;
      if (board.isTaken(to.getPoint()))
        return false;
      System.out.println("Hi!");
      if (cellsDistance(from, to) <= 2) {
        if (((cellsDistance(from, to) == 2) && isCellBetween(from, to))){
          return true;
        }

        else if(cellsDistance(from, to) == 1) {
          return true;
        }
      }
      return false;
    }


    public boolean isCellBetween(Cell a, Cell b) {
      Point vector = Point.substract(b.getPoint(), a.getPoint());

      if(!board.isDiagonal(vector))
        return true;
      return false;

    }

    public boolean find(Cell a, Cell b) {

       r=findR(a,b);
       q=findQ(a,b);

        if (findCell(q,r)){
            return true;
        }

    return false;
    }

    boolean findCellR(Cell a, Cell b){
        r=findR(a,b);
        q=(int)a.getPoint().getQ();

        if (findCell(q,r)){
            return true;
        }
        return false;
    }

    boolean findCellQ(Cell a, Cell b){
        q=findQ(a,b);
        r=(int)a.getPoint().getR();

        if(findCell(q,r)){
            return true;
        }

        return false;
    }


    boolean findCell(int q,int r){
        if (board.board[q][r]!=null){
            found =board.board[q][r];
            if(found.getOwner()!=PlayerTag.NONE)
                return true;
        }
        return false;
    }

    public int findR(Cell a, Cell b ){

        if(a.getPoint().getR()>b.getPoint().getR())
            return (int)a.getPoint().getR()-1;
        else
            return (int)b.getPoint().getR()-1;
    }

    public int findQ(Cell a, Cell b ){

        if(a.getPoint().getQ()>b.getPoint().getQ())
            return (int)a.getPoint().getQ()-1;
        else
            return (int)b.getPoint().getQ()-1;
    }

    public double cellsDistance(Cell a, Cell b) {

        return (Math.abs(a.getPoint().getQ() - b.getPoint().getQ()) + Math.abs(a.getPoint().getR() - b.getPoint().getR()) + Math.abs(a.getPoint().getS() - b.getPoint().getS())) / 2;
    }


}



