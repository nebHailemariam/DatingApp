package user;

import algorithm.CollaborativeFiltering;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import app.Main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class Chat implements Initializable {
    public static ObservableList key = FXCollections.observableArrayList();
    public static ObservableList value = FXCollections.observableArrayList();
    public static String ToBeSent = "";

    @FXML
    Label username, fname, lname, age, height, bioArea;
    @FXML
    TextArea clientWindow;

    @FXML
    TextArea clientMessage;
    @FXML
    ListView<String> chatListView;

    static DataOutputStream dos;
    static DataInputStream dis;
    String msg = "";
    int mid = 0;
    final static int ServerPort = 1234;
    public static Socket s;
    String receiverId;
    String senderId = Main.id;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        username.setText(Main.username);
        CollaborativeFiltering.find_similar_users(Integer.parseInt(Main.id));


        int size = key.size();
        String keySort[] = new String[size];
        for (int i = 0; i < size; i++) {
            keySort[i] = (String) key.get(i);
        }

        double ValueSort[] = new double[size];
        for (int i = 0; i < size; i++) {
            ValueSort[i] = (double) value.get(i);
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                if (ValueSort[i] < ValueSort[j]) {
                    double hold = ValueSort[j];
                    ValueSort[j] = ValueSort[i];
                    ValueSort[i] = hold;
                    String hold1 = (String) keySort[j];
                    keySort[j] = keySort[i];
                    keySort[i] = hold1;
                }
            }
        }

        for (int i = 0; i < size; i++) {
            ResultSet rs = User.getUser(Integer.parseInt(keySort[i]));
            try {
                while (rs.next()) {
                    chatListView.getItems().addAll(rs.getString(2));
                }
                ;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        chatListView.setOnMouseClicked(event -> {
            ToBeSent = chatListView.getSelectionModel().getSelectedItem();
            receiverId = User.getUserID(ToBeSent);


            String bio = fetchBio(receiverId);
            String biomsg[] = bio.split(",");
            fname.setText(biomsg[0]);
            lname.setText(biomsg[1]);
            age.setText(biomsg[2]);
            height.setText(biomsg[3]);
            bioArea.setText(biomsg[4]);


            readMessage();
            clientWindow.clear();
        });
    }

    public void readMessage() {
        try {
            //here we will put the ip of the pc where the Server will be based at
            InetAddress ip = InetAddress.getByName("localhost");
            s = new Socket(ip, ServerPort);
            dis = new DataInputStream(s.getInputStream());
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/xo", "root", "");

            Thread readMessage = new Thread(() -> {

                while (true) {
                    try {

                        String query = "select Message,MessageId from Message where SenderId=" + receiverId + " and ReceiverId=" + senderId + " and isRead=false ";
                        Statement stmt = con.prepareStatement("");
                        ResultSet rs = stmt.executeQuery(query);
                        if (rs.next()) {

                            if (rs.getString(1) != null) {
                                msg = rs.getString(1);
                                mid = rs.getInt(2);
                            }
                            if (!msg.isEmpty()) {
                                clientWindow.appendText("#friend : " + msg + "\n");
                                String query2 = "update Message set isRead=true where MessageId=" + mid;
                                Statement stmt2 = con.prepareStatement("");
                                stmt2.executeUpdate(query2);
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            });
            readMessage.start();
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(ActionEvent actionEvent) throws IOException {
        dos = new DataOutputStream(s.getOutputStream());
        try {
            if (!clientMessage.getText().isEmpty()) {
                dos.writeUTF(clientMessage.getText() + "#" + receiverId + "#" + senderId);
                String text = " ";
                text = clientMessage.getText();
                clientMessage.setText("");
                clientWindow.appendText(text + "\n");
            } else {
                System.out.println("Error");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String fetchBio(String id) {

        try {
            String msg2 = "";
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/xo", "root", "");

            String query = "select FirstName,LastName,Age,Height,bio From Profile where UserId=" + id;
            Statement stmt = con.prepareStatement("");
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {

                if (rs.getString(1) != null) {
                    msg2 = rs.getString(1) + "," + rs.getString(2) + "," + Integer.toString(rs.getInt(3)) + "," + Integer.toString(rs.getInt(4)) + "," + rs.getString(5);

                }

            }
            return msg2;

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;

    }
}
