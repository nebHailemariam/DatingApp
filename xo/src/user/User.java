package user;

import java.sql.*;
import java.util.LinkedList;
import java.util.Scanner;


public class User {

    public User() {
    }

    public static void userModelCreate() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/xo", "root", "");
            String query = "CREATE TABLE Users(UserId int NOT NULL AUTO_INCREMENT , Username VARCHAR (255) NOT NULL , Password VARCHAR (255) NOT NULL , PRIMARY KEY (UserID));";
            Statement stmt = con.prepareStatement("");
            stmt.executeUpdate(query);

            stmt.close();
            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public static void createUser(String username, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/xo", "root", "");

            String query = "INSERT  into Users (username, password) VALUES ('" + username + "','" + password + "')";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.executeUpdate(query);
            stmt.close();
            con.close();
            System.out.println("user Created successfully ");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static ResultSet getUsers() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/xo", "root", "");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM  Users ");
            return rs;

        } catch (Exception e) {
            System.out.println(e);
            System.exit(-1);
            return null;
        }
    }

    public static ResultSet getUser(int Id) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/xo", "root", "");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM  Users WHERE userId=" + Id + ";");
            return rs;

        } catch (Exception e) {
            System.out.println(e);
            System.exit(-1);
            return null;
        }
    }

    public static String getUserID(String name) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/xo", "root", "");
            String id = "";
            PreparedStatement statement = con.prepareStatement("select * from Users where username = ?");
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                id = rs.getString(1);
            }

            return id;
        } catch (Exception e) {
            System.out.println(e);
            System.exit(-1);
            return null;
        }
    }

    public static void test() {

        try {

            ResultSet rs = getUser(123);

            while (rs.next()) {
                System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3));
            }
            rs.close();
            ResultSet rs1 = getUsers();
            while (rs1.next()) {
                System.out.println(rs1.getString(1) + " " + rs1.getString(2) + " " + rs1.getString(3));
            }
            rs1.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public static void drop() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/xo", "root", "");
            Scanner read = new Scanner(System.in);
            System.out.println("name of table to be dropped");
            String table = read.next();
            PreparedStatement stmt = con.prepareStatement(String.format("Drop TABLE %s", table));
            stmt.execute();

            stmt.close();
            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static LinkedList getUserInfo(String username_string) {
        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/xo", "root", "");
            Statement stmt = con.createStatement();
            LinkedList<String> rsList = new LinkedList<String>();
            PreparedStatement statement = con.prepareStatement("select * from Users where username = ?");
            statement.setString(1, username_string);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {

                rsList.add(rs.getString(1));
                rsList.add(rs.getString(2));
                rsList.add(rs.getString(3));
            }

            return rsList;
        } catch (Exception e) {
            System.out.println(e + " inside get userinfo");
            System.exit(-1);
            return null;
        }
    }

    public static boolean check(String id) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/xo", "root", "");

            int userid = Integer.parseInt(id);

            PreparedStatement statement = con.prepareStatement("select UserId from Profile where userId = ?");
            statement.setInt(1, userid);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {

                if (rs.getInt(1) == userid) {
                    return true;
                }

            }


        } catch (Exception e) {
            System.out.println(e + "inside check method");

        }
        return false;
    }


}
