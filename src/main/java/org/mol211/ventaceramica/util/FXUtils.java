package org.mol211.ventaceramica.util;

import javafx.scene.Parent;
import javafx.stage.Stage;

public class FXUtils {
  Parent root;
  Stage stage;
  private static double x = 0;
  private static double y = 0;
  public static String username;
  public static String path;
  public static void enableDrag(Parent root, Stage stage) {
    root.setOnMousePressed(e -> {
      x = e.getSceneX();
      y = e.getSceneY();
    });
    root.setOnMouseDragged(e -> {
      stage.setX(e.getScreenX() - x);
      stage.setY(e.getScreenY() - y);
      stage.setOpacity(.8);
    });
    root.setOnMouseReleased(e -> stage.setOpacity(1));
  }

}
