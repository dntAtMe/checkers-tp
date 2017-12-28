package klient.gui;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;

public class GameScene {

  Group root;
  Scene scene;
  Canvas canvas;

  public GameScene(int width, int height) {
    root = new Group();
    scene = new Scene(root, width, height);
    canvas = new Canvas();

    root.getChildren().addAll(canvas);
  }
}
