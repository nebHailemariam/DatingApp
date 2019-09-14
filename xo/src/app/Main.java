package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
    public static Stage primaryStage;
    public static Parent root;
    public static String username = "";
    public static String id = "";

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("security/login.fxml"));
        Main.root = root;
        primaryStage.setTitle("XO");

        primaryStage.setScene(new Scene(root, 360, 374));
        Main.primaryStage = primaryStage;
        Main.primaryStage.show();
        Main.primaryStage.setResizable(false);

    }


    public static void main(String[] args) {

        launch(args);
//        User.userModelCreate();
//        User.drop();
//        Profile.profileModelCreate();
//        Profile.drop();
//        UserPreference.userPreferencesModelCreate();
//        UserPreference.drop();
    }
}
