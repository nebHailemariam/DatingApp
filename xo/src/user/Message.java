package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class Message {

    private static String currentUserId;


    public static void messageModelCreate() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/xo", "root", "");
            String query = "Create Table Message(MessageId int auto_increment primary key,SenderId int,ReceiverId int,Message varchar(1000),time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,isRead boolean not null default 0)";
            Statement stmt = con.prepareStatement("");
            stmt.executeUpdate(query);

            stmt.close();
            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void createMessage(int senderId, int receiverId, String message) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/xo", "root", "");
            String query = "insert into Message(SenderId,ReceiverId,Message)" +
                    "values(?,?,?)";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, senderId);
            stmt.setInt(2, receiverId);
            stmt.setString(3, message);

            stmt.executeUpdate();

            stmt.close();
            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
