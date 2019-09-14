package user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import app.Main;

import java.io.IOException;

public class InfoSheet {
    @FXML
    TextField firstname, lastname, age, height, email, pnumber, city;
    @FXML
    TextArea bio;
    @FXML
    ComboBox Combobox1;
    @FXML
    ComboBox Combobox2;

    @FXML
    public void initialize() {
        Combobox1.getItems().addAll("Orthodox", "Muslim", "Protestant", "Other", "none");
        Combobox1.getSelectionModel().select("none");
        Combobox2.getItems().addAll("Super rich", "Rich", "Average", "survivor ", "poor");
        Combobox2.getSelectionModel().select("Average");
    }

    public static String Ufirstname, Ulastname, Uage, Uheight, Ucity, Uphonenumber, Ureligon, Ustatus, Uemail, Ubio = "";

    public void oninfoAction(ActionEvent actionEvent) throws IOException {
        validate();
        getInfo();
        changeScene("movies.fxml");
    }

    private void validate() {
        TextField[] a = new TextField[7];
        a[0] = firstname;
        a[1] = lastname;
        a[2] = age;
        a[3] = height;
        a[4] = city;
        a[5] = pnumber;
        a[6] = email;
        int empty = 0;

        for (int i = 0; i < 7; i++) {
            if (a[i].getText().isEmpty()) {

                a[i].setStyle("-fx-border-color:Red");
                empty = 1;
            }
        }
        if (bio.getText().isEmpty()) {
            bio.setStyle("-fx-border-color:Red");
            empty = 1;
        }

        if (empty == 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("input error");
            alert.setContentText("Empty field is not allowed");
            alert.showAndWait();
        }
    }

    private void getInfo() {
        Ufirstname = firstname.getText();
        Ulastname = lastname.getText();
        Uage = age.getText();
        Uheight = height.getText();
        Uemail = email.getText();
        Ucity = city.getText();
        Uphonenumber = pnumber.getText();
        Ureligon = Combobox1.getSelectionModel().getSelectedItem().toString();
        Ustatus = Combobox2.getSelectionModel().getSelectedItem().toString();
        Ubio = bio.getText();

    }

    public static void writeDown() {
        Profile.createProfile(Ufirstname, Ulastname, Uage, Uheight, Uemail, Uphonenumber, Ucity, ProfileSheet.movieValue, ProfileSheet.hobbieValue, ProfileSheet.musicValue, Ureligon, Ustatus, Ubio);
        System.out.println("done");
    }

    private void changeScene(String scene) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(scene));
        Main.primaryStage.setScene(new Scene(root, 850, 500));
        Main.primaryStage.show();
        Main.primaryStage.setResizable(true);
    }
}
