package client.gui;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.shape.Polygon;

public class GameScene {

  private Group root;
  private Scene scene;
  private Canvas canvas;

  public GameScene(int width, int height) {
    root = new Group();
    scene = new Scene(root, width, height);
    canvas = new Canvas();

    root.getChildren().addAll(canvas);
  }

  public Scene getScene() {
    return scene;
  }

  public Group getRoot() {
    return root;
  }

  public void addChildren(Polygon polygon) {
    getRoot().getChildren().add(polygon);
  }
}
