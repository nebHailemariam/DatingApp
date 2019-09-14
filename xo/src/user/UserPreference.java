package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by installation on 12/18/2018.
 */
public class UserPreference {
    /**
     * Created by installation on 11/27/2018.
     */
    public UserPreference() {
    }

    //    CONSTRAINT UserProfile FOREIGN KEY (userId) REFERENCES (Users.userId)
    public static void userPreferencesModelCreate() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/xo", "root", "");
            String query = "CREATE TABLE UserPreferences(PreferenceId int, UserId int, Age int, Weight int, Height int, " +
                    "ReligiousViews VARCHAR (255)," +
                    " FinancialStatus VARCHAR (255), PRIMARY KEY (PreferenceId), " +
                    "CONSTRAINT UserPreferencesKey FOREIGN KEY (userId) References Users(UserID));";
            Statement stmt = con.prepareStatement("");
            stmt.executeUpdate(query);

            stmt.close();
            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void drop() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/xo", "root", "");
            String query = "DROP TABLE UserPreferences;";
            Statement stmt = con.prepareStatement("");
            stmt.executeUpdate(query);

            stmt.close();
            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
