package client;


import common.Board;
import common.PlayerTag;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import common.Cell;

public class Move {
  Board board;
  Cell finded;
    int r, q;


    public Move(Board board){
        this.board=board;
        finded= new Cell();

    }
    public boolean canMove(Cell from, Cell to) {

            if (cellsDistance(from, to) <= 4) {
                if (((cellsDistance(from, to) == 4) && isBetween(from, to))){
                    return true;
                }

                else if(cellsDistance(from, to) == 2) {
                     return true;
                }
            }
        return false;
        }


    public boolean isBetween(Cell a, Cell b) {
        System.out.println("Qfrom :" + a.getPoint().getQ()+"RFrom"+a.getPoint().getR());
        System.out.println("QTo :" + b.getPoint().getQ()+"RTo"+b.getPoint().getR());

        if (a.getPoint().getQ() + 2 == b.getPoint().getQ())
            q = (int)a.getPoint().getQ();
        else if (a.getPoint().getQ() - 2 == b.getPoint().getQ())
            q = (int)b.getPoint().getQ();
        else if (a.getPoint().getQ() == b.getPoint().getQ())
            q = (int)a.getPoint().getQ();
        else
            return false;

        if (a.getPoint().getR() + 2 == b.getPoint().getR())
            r = (int)a.getPoint().getR();
        else if (a.getPoint().getR() - 2 == b.getPoint().getR())
            r =(int) b.getPoint().getR();
        else if (a.getPoint().getR() == b.getPoint().getR())
            r = (int)a.getPoint().getR();
        else
            return false;

        if (findCell(r, q + 1))
            return true;

        if (findCell(r + 1, q))
            return true;

        if (findCell(r + 1, q + 1))
            return true;

        return false;
    }

    public boolean findCell(int r, int q) {
    System.out.println(r + " " + " " + q);
    System.out.println("size"+board.board.length);

        if(board.board[r][q].getOwner() != PlayerTag.NONE)
            return true;

   /* for (int i=0; i<Board.ROWS;i++){
        for (int j=0;j<Board.COLUMNS;j++){
            if((board.board[i][j].getPoint().getR()==r) && board.board[i][j].getPoint().getQ()==q){
                if(board.board[i][j].getOwner()!=(PlayerTag.NONE)){
                    return true;
                }
            }
        }
        return true;
    }
*/

    return false;
    }



    public double cellsDistance(Cell a, Cell b) {
        return (Math.abs(a.getPoint().getQ() - b.getPoint().getQ()) + Math.abs(a.getPoint().getR() - b.getPoint().getR()) + Math.abs(a.getPoint().getS() - b.getPoint().getS()));
    }

}



