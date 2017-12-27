package  klient;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.Map;


public class Event {
    Board b;
    Move move;
    boolean firstClick=true;
    int i;

    public Event(Board board){
        b=new Board();
        b=board;
        move=new Move(b);
        handleEvent();
    }

    public void handleEvent(){
        i=0;

        for(Map.Entry<Polygon,CheckerCell> entry : b.map.entrySet()){

            //zaznacza gdy najedziemy na szesciokat
            entry.getKey().addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
                entry.getKey().setStroke(Color.RED);
                entry.getKey().setStrokeWidth(4);
               // System.out.println(b.cells.get(i).isfree);
            });
            //odznacza gdy zostawimy
            entry.getKey().addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
                entry.getKey().setStroke(Color.BLACK);
                entry.getKey().setStrokeWidth(1);
            });

            entry.getKey().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                if (firstClick) {
                    if (move.properMove(entry.getValue(),entry.getKey())){
                        firstClick = false;
                    }
                } else {
                    move.move(entry.getValue(), entry.getKey());
                    firstClick = true;
                }

            });

            b.root.getChildren().add(entry.getKey());
        }

        b.showStage();

    }

}
