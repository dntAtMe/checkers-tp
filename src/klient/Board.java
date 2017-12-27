package  klient;


import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Board implements Builder {
    Group root;
    CheckerBoard chekerBoard;
    int numberOfPlayer;
    Stage stage = new Stage();
    Move move;
    ArrayList<CheckerCell> cells;
    ArrayList<Polygon> shapes;
    Map<Polygon,CheckerCell> map;

    public Board() {
        root = new Group();
        move = new Move(this);
        cells = new ArrayList<>();
        shapes = new ArrayList<>();
        map = new HashMap<>();
        buildBoard();
    }

    @Override
    public void buildBoard() {
        initGUI(stage);
    }


    private void initGUI(Stage stage) {
        ArrayList<String> lines = (FileManager.readFile("C:\\Wzorce projektowe\\javatrainings\\designpatterns\\trunk\\Checkers\\src\\klient\\board.txt"));
        chekerBoard = new CheckerBoard(lines.toArray(new String[0]), numberOfPlayer);

        stage.setTitle("Chinese Checkers");
        stage.setResizable(false);
        Canvas canvas = new Canvas();

        Scene scene = new Scene(root);
        root.getChildren().addAll(canvas);
        stage.setScene(scene);

        for (int y = 0; y < CheckerBoard.ROWS; y++) {
            for (int x = 0; x < CheckerBoard.COLUMNS; x++) {
                CheckerCell cell = chekerBoard.board[x][y];
                Polygon shape = chekerBoard.getDrawableShape(x, y);

                if (chekerBoard.board[x][y] != null) {
                    shape.setFill(cell.color);
                    shape.setStroke(Color.BLACK);
                    map.put(shape,cell);
                    shapes.add(shape);
                    cells.add(cell);
                    }
                }

            }
        }

    public void showStage(){
        stage.show();
    }

}





