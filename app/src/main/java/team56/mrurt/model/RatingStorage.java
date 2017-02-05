package team56.mrurt.model;

import java.util.List;
import java.util.ArrayList;
/**
 * Created by Haruka
 */
/**
 * A class that stores ratings and the users that rated them, stored locally
 */
public final  class RatingStorage {

    /**
     * a list of Rating
     */
    private List<Rating> ratings;
    /**
     * the RatingStorage instance
     */
    private static RatingStorage rsInstance;

    /**
     * constructor
     */
    private RatingStorage() {
        ratings = new ArrayList<>();
    }

    /**
     * Gets an instance of Rating Storage
     * @return returns the instance of RatingStorage
     */
    public static RatingStorage getInstance() {
        if (rsInstance == null) {
            rsInstance = new RatingStorage();
        }
        return rsInstance;

    }

    /**
     * gets the list of ratings stored
     * @return returns list of ratings
     */
    public List<Rating> getRatings() {
        return this.ratings;
    }

    /**
     * Sets the list of ratings stored
     * @param list the list of ratings
     */
    public void setRatings(List<Rating> list) {
        this.ratings = list;
    }

    /**
     * Adds rating to storage
     * @param rating rating to be added
     */
    public void addRating(Rating rating) {
        this.ratings.add(rating);
    }

    /**
     * Removes rating from this storage
     * @param list is rating to be removed.
     */
    public void updateRatingDatabase(List<Rating> list) {
        ratings.clear();
        setRatings(list);
    }

}
