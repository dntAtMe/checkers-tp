package  klient;

public class Controller {
    Board board;
    Move move;
    int numberOfPlayer;

    public Controller(int i ){
        this.numberOfPlayer=i;
        buildGame();
    }

    public void buildGame(){
        Board board = new Board();
        Event event = new Event(board);
    }

    public void checkMove(Point point){

    }


}
