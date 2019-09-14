package user;

import java.sql.*;
import java.util.LinkedList;


public class Profile {
    private static String currentUserId;


    public Profile() {
    }

    //CONSTRAINT UserProfile FOREIGN KEY (userId) REFERENCES (Users.userId)
    //updated the  query
    public static void profileModelCreate() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/xo", "root", "");
            String query = "CREATE TABLE Profile(ProfileId int auto_increment, UserId int, FirstName VARCHAR (255), " +
                    "LastName VARCHAR (255), Age int, Height float, Email VARCHAR (255), " +
                    "PhoneNumber VARCHAR (25),City VARCHAR (255), Movie VARCHAR (20),hobbie varchar(20),music varchar(20), Religion VARCHAR (255)," +
                    " FinancialStatus VARCHAR (255), bio varchar(255),PRIMARY KEY (ProfileId), " +
                    "CONSTRAINT UserProfileKey FOREIGN KEY (userId) References Users(UserID));";
            Statement stmt = con.prepareStatement("");
            stmt.executeUpdate(query);

            stmt.close();
            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void createProfile(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/xo", "root", "");
            String query = "insert into Profile(firstname,lastname,age,height,email,phonenumber,city,movie,hobbie,music,religion,financialstatus,bio,UserId)" +
                    "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, var1);
            stmt.setString(2, var2);
            stmt.setInt(3, Integer.parseInt(var3));
            stmt.setFloat(4, Float.parseFloat(var4));
            stmt.setString(5, var5);
            stmt.setString(6, var6);
            stmt.setString(7, var7);
            stmt.setString(8, var8);
            stmt.setString(9, var9);
            stmt.setString(10, var10);
            stmt.setString(11, var11);
            stmt.setString(12, var12);
            stmt.setString(13, var13);
            stmt.setString(14, currentUserId);

            stmt.executeUpdate();

            stmt.close();
            con.close();

        } catch (Exception e) {
            System.out.println(e + " inside create profile");
        }
    }


    public static void drop() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/xo", "root", "");
            String query = "DROP TABLE Profiles;";
            Statement stmt = con.prepareStatement("");
            stmt.executeUpdate(query);
            stmt.close();
            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static ResultSet getProfile(int Id) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/xo", "root", "");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM  profile WHERE userId=" + Id + ";");
            return rs;

        } catch (Exception e) {
            return null;
        }
    }

    public static void currentUser(LinkedList<String> info) {
        currentUserId = info.get(0);
    }
}
