package user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import app.Main;

import java.io.IOException;


public class ProfileSheet {

    @FXML
    Slider Drama, Thriller, Western, Comedy, Horror, Romance, Adventure, Science, Action, Animation;
    @FXML
    Slider Blues, Classical, EDM, Rock, Jazz, Pop, HipHop, Country, HeavyMetal, Reggae;
    @FXML
    Slider Cooking, Exercise, Photograph, Writing, Learning, FootBall, Dance, Gaming, Hiking, Drawing;


    public static String movieValue = "";
    public static String musicValue = "";
    public static String hobbieValue = "";

    public void movieChoice() {
        Double[] movies = new Double[10];
        movies[0] = Drama.getValue();
        movies[1] = Thriller.getValue();
        movies[2] = Western.getValue();
        movies[3] = Comedy.getValue();
        movies[4] = Horror.getValue();
        movies[5] = Romance.getValue();
        movies[6] = Adventure.getValue();
        movies[7] = Science.getValue();
        movies[8] = Action.getValue();
        movies[9] = Animation.getValue();


        for (int i = 0; i < 10; i++) {
            int temp = movies[i].intValue();
            movieValue += temp + ",";
        }
    }

    public void onAction(ActionEvent actionEvent) throws IOException {
        movieChoice();
        changeScene("musics.fxml");

    }

    public void onMusicAction(ActionEvent actionEvent) throws IOException {
        musicChoice();
        changeScene("hobbies.fxml");
    }

    private void musicChoice() {
        Double[] movies = new Double[10];
        movies[0] = Blues.getValue();
        movies[1] = Classical.getValue();
        movies[2] = EDM.getValue();
        movies[3] = Rock.getValue();
        movies[4] = Jazz.getValue();
        movies[5] = Pop.getValue();
        movies[6] = HipHop.getValue();
        movies[7] = Country.getValue();
        movies[8] = HeavyMetal.getValue();
        movies[9] = Reggae.getValue();


        for (int i = 0; i < 10; i++) {
            int temp = movies[i].intValue();
            musicValue += temp + ",";
        }
    }

    private void hobbieChoice() {
        Double[] movies = new Double[10];
        movies[0] = Cooking.getValue();
        movies[1] = Exercise.getValue();
        movies[2] = Photograph.getValue();
        movies[3] = Writing.getValue();
        movies[4] = Learning.getValue();
        movies[5] = Dance.getValue();
        movies[6] = Gaming.getValue();
        movies[7] = Hiking.getValue();
        movies[8] = Drawing.getValue();
        movies[9] = FootBall.getValue();


        for (int i = 0; i < 10; i++) {
            int temp = movies[i].intValue();
            hobbieValue += temp + ",";
        }
    }

    public void onhobbieAction(ActionEvent actionEvent) throws IOException {
        hobbieChoice();
        InfoSheet.writeDown();
        changeScene("chat.fxml", 1325, 650);
    }


    private void changeScene(String scene) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(scene));
        Main.primaryStage.setScene(new Scene(root, 850, 500));
        Main.primaryStage.show();
        Main.primaryStage.setResizable(true);
    }

    private void changeScene(String scene, int width, int height) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(scene));
        Main.primaryStage.setScene(new Scene(root, width, height));
        Main.primaryStage.show();
        Main.primaryStage.setResizable(true);
    }
}