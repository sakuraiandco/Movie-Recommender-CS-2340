package team56.mrurt.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class that holds the list of Users
 */
public class UserStorage {
    /**
     * a map used to store users
     */
    private static final Map<String, User> USERS = new HashMap<>();
    /**
     * the instance of the userStorage
     */
    private static UserStorage usInstance;
    /**
     * Gets an instance of User Storage
     * @return returns the instance of UserStorage
     */
    public static UserStorage getInstance() {
        if (usInstance == null) {
            usInstance = new UserStorage();
        }
        return usInstance;

    }

    /**
     * Gets the list of Users from the database and
     * puts them into UserStorage for local user
     * @param list1 the list of users from database
     */
    private void setUserList(List<User> list1) {

        for(int a = 0; a < list1.size(); a++) {
            USERS.put(list1.get(a).getUsername(), list1.get(a));
        }
    }

    /**
     * Updates userStorage when changes were made to database
     * @param list the list of users from database
     */
    public void updateUserDatabase(List<User> list) {
        USERS.clear();
        setUserList(list);
    }
    /**
     * Find the user in the map by their username
     * @param username the user's username
     * @return the User with the corresponding username
     */
    public User findUserByName(String username){
        return USERS.get(username);
    }

    /**
     * Adds a user to the map
     * @param email email of new user
     * @param username username of new user
     * @param name name of new user
     * @param major major of new user
     * @param password password of new user
     */
    public void addUser(String email, String username, String name, String major, String password) {
        final User user = new User(email, username, name, major, password);
        USERS.put(username, user);
    }

    /**
     * removes the user with the corresponding username from the map
     * @param username the username of the user being removed
     */
    public void remove(String username) {
        USERS.remove(username);
    }

    /**
     * Determines if the user trying to log in is registered or not
     * @param userName Username that the user input
     * @param password password that the user input
     * @return If the user is registered, return true;
     */
    public boolean handleLoginRequest(String userName, String password) {
        final User user = findUserByName(userName);
        return user != null && user.checkPassword(password);
    }

    /**
     * Gets the list of Users in array form
     * @return the array of users
     */
    public String[] toArray(){
        return Arrays.copyOf(USERS.keySet().toArray(), USERS.keySet().toArray().length, String[].class);
    }

}