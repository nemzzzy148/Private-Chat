module com.java.private_chat {
    requires javafx.controls;
    requires javafx.fxml;


    exports com.java.private_chat;
    opens com.java.private_chat to javafx.fxml;
}