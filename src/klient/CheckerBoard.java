
package  klient;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;

public class CheckerBoard {
    public static final int ROWS = 17;
    public static final int COLUMNS = 25;
    public static final int SIZE = 20;
    public static final double HEIGHT = SIZE * 2;
    public static final double WIDTH = Math.sqrt(3)/2 * HEIGHT;
    public final CheckerCell[][] board = new CheckerCell[COLUMNS][ROWS];

    int numberOfplayers;

    char currentChar;

    ArrayList<Pawn> pawns = new ArrayList<Pawn>();


    public CheckerBoard(String[] lines, int numberOfPlayers) {
        this.numberOfplayers = numberOfPlayers;
        createBoardFromStrings(lines);
    }

    private void createBoard() {

    }

    private void createBoardFromStrings(String[] lines) {
        for(int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLUMNS; x++) {
               currentChar = lines[y].charAt(x);
                PlayerTagEnum[] possibilities = PlayerTagEnum.values();
                PlayerTagEnum owner;
                CheckerCellEnum type;
                Color[] colors = new Color[]{Color.TRANSPARENT, Color.LIGHTGRAY, Color.LIGHTGREEN, Color.LIGHTPINK, Color.LIGHTCORAL, Color.YELLOWGREEN, Color.STEELBLUE};
                if (currentChar == '0') {
                    owner = PlayerTagEnum.NONE;
                    type = CheckerCellEnum.CELL_BOARD;
                } else if (currentChar == ' ') {
                    continue;
                } else {
                    owner = possibilities[Integer.parseInt(String.valueOf(currentChar))];
                    type = CheckerCellEnum.CELL_TAKEN;
                    board[x][y] = new CheckerCell(x, y, type, owner, colors[Integer.parseInt(String.valueOf(currentChar))],false);
                    pawns.add(new Pawn(x,y,owner, colors[Integer.parseInt(String.valueOf(currentChar))]));
                    continue;
                }
                board[x][y] = new CheckerCell(x, y, type, owner, colors[Integer.parseInt(String.valueOf(currentChar))],true);

            }
        }
    }



    public Polygon getDrawableShape(int x, int y) {
        Polygon poly = new Polygon();
        for (int i = 0; i < 6; i ++) {
            int angleDeg = 60 * i + 30;
            double angleRad = Math.PI / 180 * angleDeg;
            double centerX = x * WIDTH + WIDTH / 2 * y - 5.5 * WIDTH;
            double centerY = SIZE + y * HEIGHT * 3/4 ;
            Point p = new Point(centerX + SIZE * Math.cos(angleRad), centerY + SIZE * Math.sin(angleRad));
            poly.getPoints().addAll(p.x, p.y);
        }

        return poly;
    }
}

