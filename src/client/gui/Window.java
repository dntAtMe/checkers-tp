package client.gui;

import client.net.Client;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import client.engine.InputHandler;
import javafx.stage.WindowEvent;

public class Window extends Application implements IWindow {

  private Stage stage;

  private InputHandler inputHandler;
  private MenuScene menuScene;

  @Override
  public void start(Stage mainStage) throws Exception {

    stage = mainStage;
    inputHandler = new InputHandler(this);
    menuScene = new MenuScene(inputHandler, 200, 800);
    AlertBox box = new AlertBox();
    box.start(mainStage);
   // AlertBox.display();
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

  public void onMoveSkipped(){
    inputHandler.onMoveSkipped();
  }

  public void setMenuUp() {
    setScene(menuScene.getScene());
  }

  public void terminateOnClose(Object object) {
    if (object instanceof Client)
      stage.setOnCloseRequest(windowEvent -> {
        ((Client) object).disconnect();
        Platform.exit();
      });

  }

  @Override
  public void setScene(Scene scene) {
    stage.setScene(scene);
  }

  public static void main(String[] args) {
    launch(args);
  }
}
