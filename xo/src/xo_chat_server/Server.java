package xo_chat_server;

import javafx.scene.control.TextArea;
import app.Main;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.Vector;

public class Server {
    public static Vector<ClientHandler> ar = new Vector<ClientHandler>();

    public static void main(String[] args) throws IOException {
        System.out.println("Chat server running...");
        ServerSocket ss = new ServerSocket(1234);
        Socket s;

        while (true) {
            s = ss.accept();
            DataInputStream dis = new DataInputStream(s.getInputStream());
            Main.username = "User";
            ClientHandler account = new ClientHandler(s, Main.username, dis);
            Thread chat = new Thread(account);
            chat.start();

        }
    }

    public static void read(Socket s, String receiverId, String senderId, TextArea clientWindow) {

        final int ServerPort = 1234;

        try {

            InetAddress ip = InetAddress.getByName("localhost");
            s = new Socket(ip, ServerPort);

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/xo", "root", "");

            Thread readMessage = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            String msg = "";
                            int mid = 0;
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
                }
            });

            readMessage.start();

        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }
}


