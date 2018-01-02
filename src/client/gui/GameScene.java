package client.gui;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;


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

  public void addChildren(Node node) {
    getRoot().getChildren().add(node);
  }
}
