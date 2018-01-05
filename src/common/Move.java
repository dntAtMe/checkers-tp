package common;


import client.PlayerTag;

public class Move {
    private Board board;
    boolean isTurnFinished=false;


    public Move(Board board){
        this.board=board;
    }

    public void makeMove(Point start, Point end, PlayerTag tag) {
      board.copyOwner(end, start);
      board.setCellEmpty(start);
    }

    public boolean isTurnFinished(){
        return isTurnFinished;
    }
    public boolean canMove(Point from, Point to, PlayerTag askingTag, PlayerTag lastPlayer) {
        isTurnFinished=false;

        if (board.getCell(from).getOwner() != askingTag)
            return false;

        if (board.isTaken(to))
            return false;

        if(askingTag == lastPlayer){
            if(canMoveAgain(from,to,askingTag))
                return true;
        }
        else {
            if(canMoveForFirstTimePoint(from,to,askingTag))
                return true;
        }
        return false;
    }

    public boolean canMoveAgain(Point from, Point to, PlayerTag askingTag) {

        if(board.getTmpPoint()!=from)
            return false;

        if (board.getCell(from).getOwner() != askingTag)
            return false;
        if (board.isTaken(to))
            return false;

        if (distance(from, to) <= 2) {
            if (((distance(from, to) == 2) && isPointBetween(from, to))) {
                board.setTmpPoint(to);
                return true;
            }
        }
        return false;
    }

    public boolean canMoveForFirstTimePoint(Point from, Point to, PlayerTag askingTag) {


        if (distance(from, to) <= 2) {
            if (((distance(from, to) == 2) && isPointBetween(from, to))){
                board.setTmpPoint(to);
                return true;
            }
            else if(distance(from, to) == 1) {
                isTurnFinished=true;
                return true;
            }
        }
        return false;
    }

    public boolean isPointBetween(Point a, Point b) {
        System.out.println("FROM"+a.getQ()+" , "+a.getR());
        System.out.println("TO"+b.getQ()+" , "+b.getR());

        Point vector = Point.substract(b, a);
        if(!board.isDiagonal(vector)) {
            if(board.board[getQBetween(a,b)][getRBetween(a,b)].getOwner()!= PlayerTag.NONE)
                return true;
        }
        return false;

    }


    public double distance(Point a, Point b) {
        return (Math.abs(a.getQ() - b.getQ()) +
                Math.abs(a.getR() - b.getR()) +
                Math.abs(a.getS() - b.getS())
                ) / 2;
    }


    private static  int getQBetween(Point a, Point b){

        if(a.getQ()>b.getQ())
            return a.getQ()-1;
        if(a.getQ()<b.getQ())
            return b.getQ()-1;
        else
            return a.getQ();
    }

    private static int getRBetween(Point a, Point b){

        if (a.getR()>b.getR())
            return a.getR()-1;
        else if(a.getR()<b.getR())
            return b.getR()-1;
        else
            return b.getR();
    }



}



