package algorithm;

import user.Chat;
import user.Profile;
import user.User;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.ResultSet;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.LinkedList;


/**
 * Created by installation on 12/1/2018.
 */

public class CollaborativeFiltering {
    public static void find_similar_users(int user) {

        ResultSet rs = User.getUsers();
        ResultSet rs1;
        String profile_user_one = "";
        String profile_user_two = "";
        try {
            int x = 0;
            while (rs.next()) {

                x++;

                Dictionary userQuestion = new Hashtable();
                userQuestion.put(user, new LinkedList<String>());
                Dictionary userAnswer = new Hashtable();
                userAnswer.put(user, new LinkedList<String>());

                ResultSet userResultSet = Profile.getProfile(user);
                while (userResultSet.next()) {

                    LinkedList<String> userquestions = new LinkedList<String>();
                    LinkedList<String> useranswers = new LinkedList<String>();

                    profile_user_one = userResultSet.getString(2);

                    String storeUser[] = userResultSet.getString(10).split(",");
                    for (int i = 0; i < 10; i++) {
                        userquestions.push("Movie" + i);
                        useranswers.push(storeUser[i]);
                    }
                    storeUser = userResultSet.getString(11).split(",");
                    for (int i = 0; i < 10; i++) {
                        userquestions.push("Hobby" + i);
                        useranswers.push(storeUser[i]);
                    }

                    storeUser = userResultSet.getString(12).split(",");

                    for (int i = 0; i < 10; i++) {
                        userquestions.push("Music" + i);
                        useranswers.push(storeUser[i]);
                    }



                    userQuestion.put(userResultSet.getString(2), userquestions);
                    userAnswer.put(userResultSet.getString(2), useranswers);
                }

                try {
                    rs1 = Profile.getProfile(Integer.parseInt(rs.getString(1)));
                    while (rs1.next()) {
                        String store[] = rs1.getString(10).split(",");
                        LinkedList<String> questions = new LinkedList<String>();
                        LinkedList<String> answers = new LinkedList<String>();

                        profile_user_two = rs1.getString(2);
                        for (int i = 0; i < 10; i++) {
                            questions.push("Movie " + i);
                            answers.push(store[i]);
                        }

                        store = rs1.getString(11).split(",");

                        for (int i = 0; i < 10; i++) {
                            questions.push("Hobby " + i);
                            answers.push(store[i]);
                        }

                        store = rs1.getString(12).split(",");

                        for (int i = 0; i < 10; i++) {
                            questions.push("Music " + i);
                            answers.push(store[i]);
                        }
                        userQuestion.put(rs1.getString(2), questions);
                        userAnswer.put(rs1.getString(2), answers);

                    }
                    if (!profile_user_one.equals(profile_user_two)) {
                        CollaborativeFilteringInterface C;
                        try {

                            Registry reg = LocateRegistry.getRegistry("127.0.0.1", 3000);
                            //client should lookup for a name in the rmiregistry to access remote objects
                            C = (CollaborativeFilteringInterface) reg.lookup("calculate");
                            Chat.key.add(profile_user_two);
                            Chat.value.add(C.PearsonCorrelation(userQuestion, userAnswer, profile_user_one, profile_user_two));
                        } catch (Exception e) {
                            System.out.println("Client Exception " + e);
                        }

                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());

                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
