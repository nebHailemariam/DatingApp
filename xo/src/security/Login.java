package security;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import app.Main;
import user.Profile;
import user.User;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import static app.Main.primaryStage;

public class Login implements Initializable {
    @FXML
    TextField username;
    @FXML
    TextField password;
    @FXML
    Label username_error;
    @FXML
    Label password_error;


    public String getPassword() {
        return username.getText();
    }

    @FXML
    Button login;
    @FXML
    Button sign_up;

    public void onSignUp(ActionEvent e) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("signup.fxml"));
        Main.primaryStage.setScene(new Scene(root, 850, 500));
        Main.primaryStage = primaryStage;
        Main.primaryStage.show();
        Main.primaryStage.setResizable(false);

    }

    public void onLogin(ActionEvent e) {
        if (username.getText().isEmpty()) {
            username_error.setText("!");
            username_error.setStyle("-fx-text-fill:Red;");
        } else {
            username_error.setText("");
            username_error.setStyle("-fx-text-fill:White;");
        }
        if (password.getText().isEmpty()) {
            password_error.setText("! ");
            password_error.setStyle("-fx-text-fill:Red;");
        } else {
            password_error.setText("");
            password_error.setStyle("-fx-text-fill:White;");
        }

        if (!password.getText().isEmpty() && !username.getText().isEmpty()) {
            String password_string = password.getText();
            String username_string = username.getText();

            String hashed_password = Hashing.SHA_512(password_string);
            LinkedList<String> info = User.getUserInfo(username_string);
            if (info.isEmpty()) {
                username_error.setText("Incorrect!");
                username_error.setStyle("-fx-text-fill:Red;");
                password_error.setText("Incorrect!");
                password_error.setStyle("-fx-text-fill:Red;");
            } else if (info.get(2).equals(hashed_password)) {
                Main.username = username_string;
                Main.id = User.getUserID(username_string);
                try {

                    if (User.check(info.get(0))) {

                        Parent root = FXMLLoader.load(getClass().getResource("../user/chat.fxml"));
                        Main.primaryStage.setScene(new Scene(root, 1325, 650));
                        Main.primaryStage.show();
                        Main.primaryStage.setResizable(true);
                        Main.username = username_string;
                    } else {
                        Profile.currentUser(info);
                        Parent root = FXMLLoader.load(getClass().getResource("../user/info.fxml"));
                        Main.primaryStage.setScene(new Scene(root, 850, 500));
                        Main.primaryStage.show();
                        Main.primaryStage.setResizable(true);
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            } else {
                password_error.setText("Incorrect!");
                password_error.setStyle("-fx-text-fill:Red;");
            }

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
