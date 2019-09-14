package algorithm;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;

/**
 * Created by installation on 2/28/2019.
 */
public class CollaborativeFiltering extends UnicastRemoteObject implements CollaborativeFilteringInterface {

    protected CollaborativeFiltering() throws RemoteException {
    }

    public double PearsonCorrelation(Dictionary userQuestion1, Dictionary userAnswer1, String userOne, String userTwo) throws RemoteException {

        Dictionary userQuestion = new Hashtable();
        userQuestion.put(userOne, new LinkedList<String>());
        userQuestion.put(userTwo, new LinkedList<String>());
        Dictionary userAnswer = new Hashtable();
        userAnswer.put(userOne, new LinkedList<String>());
        userAnswer.put(userTwo, new LinkedList<String>());
        double userOneSum = 0.0;
        double userTwoSum = 0.0;
        Dictionary commonThings = new Hashtable();
        double userOneSquaredSum = 0.0;
        double userTwoSquaredSum = 0.0;

        double sumOfProduct = 0;


        int count = 2;
        LinkedList<String> userOneQuestion = (LinkedList<String>) userQuestion1.get(userOne);
        LinkedList<String> userTwoQuestion = (LinkedList<String>) userQuestion1.get(userTwo);
        LinkedList<String> userOneAnswer = (LinkedList<String>) userAnswer1.get(userOne);
        LinkedList<String> userTwoAnswer = (LinkedList<String>) userAnswer1.get(userTwo);

        for (int i = 0; i < userOneQuestion.size(); i++) {
            for (int j = 0; j < userTwoAnswer.size(); j++) {
                if (userOneQuestion.get(i).equals(userTwoQuestion.get(j))) {
                    commonThings.put(userOneQuestion.get(i), 1);
                }
            }
        }

        int num_rating = commonThings.size();

        if (num_rating == 0) {

            return 0;
        }

        for (Enumeration u = commonThings.keys(); u.hasMoreElements(); ) {
            String store = u.nextElement().toString();
            for (int i = 0; i < userOneQuestion.size(); i++) {

                if (store.equals(userOneQuestion.get(i))) {
                    userOneSum += Float.parseFloat(userOneAnswer.get(i));
                }
            }

        }
        for (Enumeration u = commonThings.keys(); u.hasMoreElements(); ) {
            String store = u.nextElement().toString();
            for (int i = 0; i < userTwoQuestion.size(); i++) {

                if (store.equals(userTwoQuestion.get(i))) {
                    userTwoSum += Float.parseFloat(userTwoAnswer.get(i));
                }
            }

        }

        for (Enumeration u = commonThings.keys(); u.hasMoreElements(); ) {
            String store = u.nextElement().toString();
            for (int i = 0; i < userOneQuestion.size(); i++) {
                if (store.equals(userOneQuestion.get(i))) {
                    userOneSquaredSum += Math.pow(Float.parseFloat(userOneAnswer.get(i)), 2);
                }
            }

        }
        for (Enumeration u = commonThings.keys(); u.hasMoreElements(); ) {
            String store = u.nextElement().toString();
            for (int i = 0; i < userTwoQuestion.size(); i++) {
                if (store.toString().equals(userTwoQuestion.get(i))) {
                    userTwoSquaredSum += Math.pow(Float.parseFloat(userTwoAnswer.get(i)), 2);
                }
            }

        }

        for (Enumeration u = commonThings.keys(); u.hasMoreElements(); ) {
            String store = u.nextElement().toString();
            for (int i = 0; i < userOneQuestion.size(); i++) {
                for (int j = 0; j < userTwoQuestion.size(); j++) {

                    if (userOneQuestion.get(i).equals(store) && userTwoQuestion.get(j).equals(userOneQuestion.get(i))) {
                        sumOfProduct += Float.parseFloat(userOneAnswer.get(i)) * Float.parseFloat(userTwoAnswer.get(j));

                    }
                }
            }
        }

        double sxy = sumOfProduct - (userOneSum * userTwoSum / num_rating);
        double sxx = userOneSquaredSum - (Math.pow(userOneSum, 2) / num_rating);
        double syy = userTwoSquaredSum - (Math.pow(userTwoSum, 2) / num_rating);

        if (sxx * syy == 0) {
            return 0;
        }

        return sxy / Math.sqrt(sxx * syy);

    }
}
