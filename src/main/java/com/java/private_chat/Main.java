package com.java.private_chat;
import javafx.stage.Stage;
import javafx.application.Application;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        ChatUI ui = new ChatUI();
        ui.start(primaryStage);
    }
}