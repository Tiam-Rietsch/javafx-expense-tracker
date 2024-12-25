package com.tiam;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {


    @Override
    public void start(Stage window) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
        Scene scene = new Scene(root, 1000, 700);

        window.setTitle("Expense Tracker");
        window.setScene(scene);
        window.setMinWidth(1000);
        window.setMinHeight(700);
        window.show();
    }

    public static void main(String[] args) {
        launch();
    }

}