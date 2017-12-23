package main.java.sample;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Board implements Builder {
    Group root;
    CheckerBoard chekerBoard;
    Pawn pawns[];
    int numberOfPlayer;
    Stage stage = new Stage();
    Move move;
    boolean firstClick=true;
    ArrayList<CheckerCell> cells;

    public Board(){
        root= new Group();
        move=new Move(this);
        cells=new ArrayList<>();
    }

    @Override
    public void buildBoard() {
        initLogic();
        initGUI(stage);
        System.out.println("jhgf");
    }


    public void initLogic() {
        ArrayList<String> lines = (FileManager.readFile("/home/katarzyna/IdeaProjects/Chinese/src/sample/board.txt"));
        System.out.println(new String[0]);
        System.out.println("plansza");
        chekerBoard = new CheckerBoard(lines.toArray(new String[0]), numberOfPlayer);

    }

    private void initGUI(Stage stage) {
        stage.setTitle("Chinese Checkers");
        stage.setResizable(false);
        Canvas canvas = new Canvas();

        Scene scene = new Scene(root);
        root.getChildren().addAll(canvas);
        stage.setScene(scene);

        for (int y = 0; y < CheckerBoard.ROWS; y++) {
            for (int x = 0; x < CheckerBoard.COLUMNS; x++) {
                CheckerCell cell = chekerBoard.board[x][y];
                cells.add(cell);
                Polygon shape = chekerBoard.getDrawableShape(x, y);

                if (chekerBoard.board[x][y] != null) {

                    shape.setFill(cell.color);
                    shape.setStroke(Color.BLACK);

                    //zaznacza gdy najedziemy na szesciokat
                    shape.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> {
                        shape.setStroke(Color.RED);
                        shape.setStrokeWidth(4);
                       // System.out.println(cell.isfree);
                    });
                    //odznacza gdy zostawimy
                    shape.addEventFilter(MouseEvent.MOUSE_EXITED, e -> {
                        shape.setStroke(Color.BLACK);
                        shape.setStrokeWidth(1);
                    });

                    shape.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
                        //System.out.println("R: "+cell.getR()+"Q: "+cell.getQ()+"S: "+cell.getS());
                        if (firstClick) {
                            if (move.properMove(cell, shape)) {
                                firstClick = false;
                            }
                        } else {
                            move.move(cell, shape);
                            firstClick = true;
                        }
                    });

                    root.getChildren().add(shape);
                }

            }

        }
            stage.show();

    }

}

