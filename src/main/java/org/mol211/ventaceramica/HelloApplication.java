package org.mol211.ventaceramica;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.mol211.ventaceramica.util.FXUtils;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login.fxml")));
        Scene scene = new Scene(root);

        FXUtils.enableDrag(root, stage);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
}
