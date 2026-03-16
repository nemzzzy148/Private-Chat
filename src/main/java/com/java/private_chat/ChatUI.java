package com.java.private_chat;

import com.java.private_chat.Network;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class ChatUI {

    TextArea textArea = new TextArea();
    TextField textField = new TextField();
    TextField portField = new TextField(); // TextField ipv TextArea

    private Network network;

    public void start(Stage primaryStage) {
        textArea.setEditable(false);
        textArea.setWrapText(true);
        portField.setPromptText("Port (default. 5001)");

        Button send = new Button("Send");
        Button connect = new Button("Connect");

        textField.setOnAction(e -> sendMessage());
        portField.setOnAction(e -> newNetwork(Integer.parseInt(portField.getText())));
        connect.setOnAction(e -> newNetwork(Integer.parseInt(portField.getText())));
        send.setOnAction(e -> sendMessage());

        HBox bBox = new HBox(10, textField, send);
        HBox tBox = new HBox(10, portField, connect);

        BorderPane root = new BorderPane();
        root.setCenter(textArea);
        root.setBottom(bBox);
        root.setTop(tBox);

        newNetwork(5001);

        Scene scene = new Scene(root, 400, 400);
        primaryStage.setTitle("Private Chatting v1.0");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void sendMessage() {
        String message = textField.getText();
        if (!message.isEmpty() && network != null) {
            network.send(message);
            textField.clear();
        }
    }

    public void receiveMessage(String message) {
        Platform.runLater(() -> textArea.appendText("User: " + message + "\n"));
    }

    public void newNetwork(Integer port) {
        if (port == null || port < 1 || port > 65535) {
            textArea.appendText("Invalid port!\n");
            return;
        }
        if (network != null) {
            network.close();
        }
        network = new Network(this, port);
        network.start();
        textArea.appendText("Sending to port: " + port + "\n");
    }

    public void clear() {
        textArea.clear();
    }
}