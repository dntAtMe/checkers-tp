package client.gui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import client.engine.InputHandler;

public class Window extends Application implements IWindow {

  private Stage stage,information;
  Scene stageInformation;

  private InputHandler inputHandler;
  private MenuScene menuScene;

  @Override
  public void start(Stage mainStage) throws Exception {
    final Group rootGroup = new Group();

    stage = mainStage;
    inputHandler = new InputHandler(this);
    menuScene = new MenuScene(inputHandler, 200, 800);
    AlertBox.display();
    stage.setScene(menuScene.getScene());
    stage.setTitle("Chinese Checkers");
    stage.show();
  }

  public void onMouseEntered(MouseEvent en) {
    inputHandler.onMouseEntered(en);
  }

  public void onMouseExited(MouseEvent ex) {
    inputHandler.onMouseExited(ex);
  }

  public void onMouseClicked(MouseEvent e) {
    inputHandler.onMouseClicked(e);
  }


  @Override
  public void setScene(Scene scene) {
    stage.setScene(scene);
  }

  public static void main(String[] args) {
    launch(args);
  }
}
