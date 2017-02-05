package team56.mrurt.model;


import java.util.ArrayList;
import java.util.List;


/**
 * A class that represents a User
 */
public class User {
    /**
     * the string's used for the attributes of a user
     */
    private final String email, username, name, major, password;
    /**
     * a list of ratings the user has given
     */
    private final List<Rating> ratingList;
    /**
     * boolean that determines if the user is banned or not
     */
    private boolean banned;
    /**
     * boolean that determines if the user is an admin or not
     */
    private boolean admin;

    /**
     * Creates a User Object
     * @param emailP user email
     * @param usernameP username of user
     * @param nameP the name of the user
     * @param majorP the major
     * @param passwordP the majors
     */
    public User(String emailP, String usernameP, String nameP, String majorP, String passwordP) {
        this.email = emailP;
        this.username = usernameP;
        this.name = nameP;
        this.major = majorP;
        this.password = passwordP;
        this.ratingList = new ArrayList<>();
        this.banned = false;
        this.admin = false;
    }

    /**
     * Returns the user's email
     * @return email
     */
    public String getEmail(){
        return email;
    }

    /**
     * Returns the user's username
     * @return username
     */
    public String getUsername(){
        return username;
    }

    /**
     * Return the user's name
     * @return name
     */
    public String getName(){
        return name;
    }

    /**
     * return the user's Major
     * @return major
     */
    public String getMajor(){
        return major;
    }

    /**
     * Return the user's password
     * @return password
     */
    public String getPassword(){
        return password;
    }

    /**
     * Determines if the inputted password matches the user's password
     * @param pass password being checked
     * @return True if the passwords match
     */
    public boolean checkPassword(String pass) {
        return password.equals(pass);
    }

    /**
     * Add a rating to user's list of ratings made
     * @param newRating the new rating to be added
     */
    public void addRating(Rating newRating) {
        this.ratingList.add(newRating);
    }

    /**
     * Remove a rating to user's list of ratings made
     * @param oldRating the rating to be removed
     */
    public void removeRating(Rating oldRating) {
        this.ratingList.remove(oldRating);
    }

    /**
     * Return the user's ratings list
     * @return the user's rating list
     */
    public List<Rating> getRatingList() {
        return this.ratingList;
    }

    /**
     * Set the user to be banned or not
     * @param status if the user is banned or not
     */
    public void setBanStatus(boolean status){
        this.banned = status;
    }

    /**
     * Set the user to be an Admin or not
     * @param status if the user is an admin or not
     */
    public void setAdminStatus(boolean status){
        this.admin = status;
    }

    /**
     * Get the banned status
     * @return the int value
     */
    public int getBanStatus(){
        if(banned) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Get the admin status
     * @return the int value
     */
    public int getAdminStatus(){
        if(admin) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Set the user to be banned
     */
    public void banUser(){
        this.banned = true;
    }

    /**
     * Un-ban user
     */
    public void unlockUser(){
        this.banned = false;
    }

    /**
     * Get banned status
     * @return the status
     */
    public boolean isBanned(){
        return this.banned;
    }

    /**
     * Set the user to be an Admin
     */
    public void makeAdmin() { this.admin = true;}

    /**
     * Get admin status
     * @return the status
     */
    public boolean isAdmin(){
        return admin;
    }

    /**
     * checks if two users are the same based off username
     * @param u the user being compared
     * @return true if the users are equal
     */
    @Override
    public boolean equals(Object u) {
        return ((User)u).getUsername().equals(this.getUsername());
    }
    @Override
    public int hashCode() {
        return this.getUsername().hashCode();
    }
}

