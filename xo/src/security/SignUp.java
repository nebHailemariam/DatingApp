package security;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import app.Main;
import user.User;

import java.io.IOException;

/**
 * Created by installation on 12/31/2018.
 */
public class SignUp {
    @FXML
    TextField username;
    @FXML
    TextField password1;
    @FXML
    TextField password2;
    @FXML
    Label username_error;
    @FXML
    Label password_error;
    @FXML
    Button login;
    @FXML
    Button sign_up;

    public static String currentUser;

    public void onSignup(ActionEvent e) throws IOException {

        if (username.getText().isEmpty()) {
            username_error.setText("!");
            username_error.setStyle("-fx-text-fill:Red;");

        } else {
            username_error.setStyle("-fx-text-fill:White;");

        }
        if (password1.getText().isEmpty() || password2.getText().isEmpty()) {
            username_error.setText("!");
            password_error.setStyle("-fx-text-fill:Red;");

        } else {
            password_error.setStyle("-fx-text-fill:White;");

        }

        if (!password1.getText().isEmpty() && !username.getText().isEmpty() && !password2.getText().isEmpty() && password1.getText().equals(password2.getText())) {
            String password_string = password1.getText();
            String username_string = username.getText();
            String info = User.getUserID(username_string);
            if (info.isEmpty()) {
                String pass = Hashing.SHA_512(password_string);
                User.createUser(username_string, pass);
                currentUser = User.getUserID(username_string);
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("security/login.fxml"));
                Main.primaryStage.setScene(new Scene(root, 360, 374));
                Main.primaryStage.show();
                Main.primaryStage.setResizable(true);

            } else {
                username_error.setText("Username already exists");
                username_error.setStyle("-fx-text-fill:Red;");

            }
        } else {
            password_error.setStyle("-fx-text-fill:Red;");

        }

    }
}
