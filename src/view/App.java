package view;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class App extends Application {

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/SplashView.fxml"))));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.centerOnScreen();
    }
}
