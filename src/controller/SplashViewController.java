package controller;

import java.io.IOException;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class SplashViewController {

    @FXML
    private Label lblStates;

    @SuppressWarnings("unchecked")
    public void initialize() {

        Task initTask = new Task<>() {

            @Override
            protected Object call() throws Exception {
               
                Thread.sleep(2000);
                return null;
            }  
        };
    
        initTask.setOnSucceeded(event ->{
            Stage stage = new Stage();
            try {
                stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/Main.fxml"))));
            } catch (IOException e) {
                throw new RuntimeException();
            }
            stage.setResizable(false);
            stage.show();
            stage.centerOnScreen();
            ((Stage)(lblStates.getScene().getWindow())).close();
        });
        new Thread(initTask).start();
    }
}