package client.gui;

import javafx.scene.Group;
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

  public void addChildren(Polygon polygon) {
    getRoot().getChildren().add(polygon);
  }
  public void addChildren(Text text){
    getRoot().getChildren().add(text);
  }
  public void addChildren(Button button){
    getRoot().getChildren().add(button);
  }
}
