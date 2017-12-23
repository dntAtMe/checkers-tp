package klient;

public class Controller {
    Board board;
    int numberOfPlayer;

    public Controller(int i ){
        this.numberOfPlayer=i;
        board=new Board();
        board.buildBoard();

    }

    public void buildGame(){
        board.buildBoard();

    }

    public void checkMove(Point point){

    }


}
