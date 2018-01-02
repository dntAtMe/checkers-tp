package client.gui;

import common.Cell;
import client.PlayerTag;
import common.Point;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.Map;

public class DrawEngine {
  public static final int SIZE = 20;
  public static final double HEIGHT = SIZE * 2;
  public static final double WIDTH = Math.sqrt(3)/2 * HEIGHT;

  Color[] colors = new Color[]{Color.TRANSPARENT, Color.LIGHTGRAY, Color.LIGHTGREEN, Color.LIGHTPINK, Color.LIGHTCORAL, Color.YELLOWGREEN, Color.STEELBLUE};
  Map<PlayerTag, Color> playerColors = new HashMap<>();

  private Polygon polygons[][];

  private Button skipButton;
  private Text text;

  private GameScene scene;
  private Window window;

  public DrawEngine(Window window) {
    this.window = window;
    int i = 0;
    for (PlayerTag tag : PlayerTag.values()) {
      playerColors.put(tag, colors[i++]);
    }
  }

  public void onMove(Point from, Point to, PlayerTag newTag){
    polygons[from.getQ()][from.getR()].
            setFill(playerColors.get(PlayerTag.NONE));
    polygons[to.getQ()][to.getR()].
            setFill(playerColors.get(newTag));
  }

  public void selectCell(int x, int y) {
    polygons[x][y].setStroke(Color.RED);
    polygons[x][y].setStrokeWidth(4);
  }

  public void deselectCell(int x, int y) {
    polygons[x][y].setStroke(Color.BLACK);
    polygons[x][y].setStrokeWidth(1);
  }

  public void startGameGUI(int width, int height, Cell[][] board) {
    prepareBoard(width, height, board);
    scene = new GameScene((int) (width * WIDTH), (int) (height * HEIGHT));
    for (int x = 0; x < width; x++){
      for (int y = 0; y < height; y++) {
        if(polygons[x][y] != null)
          scene.addChildren(polygons[x][y]);
      }
    }

    scene.getScene().addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
      window.onMouseClicked(e);
    });

    scene.getScene().addEventFilter(MouseEvent.MOUSE_ENTERED, en -> {
      window.onMouseEntered(en);
    });

    scene.getScene().addEventFilter(MouseEvent.MOUSE_EXITED, ex -> {
      window.onMouseExited(ex);
    });


    window.setScene(scene.getScene());
  }

  private void prepareBoard(int width, int height, Cell[][] board) {
    polygons = new Polygon[width][height];
    for (int x = 0; x < width; x++){
      for (int y = 0; y < height; y++) {
        if(board[x][y] == null)
          continue;
        Polygon poly = getDrawableShape(x, y);
        poly.setFill(playerColors.get(board[x][y].getOwner()));
        poly.setStroke(Color.BLACK);
        polygons[x][y] = poly;
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
      double pointX = centerX + SIZE * Math.cos(angleRad);
      double pointY = centerY + SIZE * Math.sin(angleRad);

      poly.getPoints().addAll(pointX, pointY);
    }
    return poly;
  }

  public void createInformation(boolean isOnTurn){
    text = new Text(500,100, "");
    text.setText(updateText(isOnTurn));
    text.setFont(Font.font(java.awt.Font.DIALOG, 20));
    scene.addChildren(text);

    skipButton = createButton();
    scene.addChildren(skipButton);

  }

  private String updateText(boolean isOnTurn){

    if(isOnTurn)
      return new String ("\tGo! \nIt's your turn!");

      return new String("\tSTOP! \nIt's not your turn!");

  }
  private Button createButton(){
    Button button = new Button("SKIP");
    button.setMinSize(50,50);
    button.setStyle(" -fx-background-color:#B3B3B3; ");

    button.setOnAction(e -> {
      window.onMoveSkipped();
      System.out.println("Skip move");
            });
    return button;
  }

  public void updateSkipOption(boolean isOnTurn) {
    skipButton.setDisable(!isOnTurn);
  }

  public void updateTurnNotification(boolean onTurn) {
    text.setText(updateText(onTurn));
  }
}

