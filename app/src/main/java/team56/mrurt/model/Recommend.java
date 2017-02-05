package team56.mrurt.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import team56.mrurt.activity.LoginActivity;

/**
 * Created by Haruka.
 * Sorts the movies based on recommendation criteria
 */
public final class Recommend {

    /**
     * constructor for Recommend
     */
    private Recommend() {

    }

    /**
     * Sorts the movie by year
     * @param year the year the user wants to see recommendation for
     * @return the list of the movie sorted from highest to lowest rating
     */
    public static List<Movie> sortByYear(String year) {
        final  List<Rating> ratedMovieList = RatingStorage.getInstance().getRatings();
        final List<Rating> ratings = new ArrayList<>();
        for (final Rating r : ratedMovieList){
            if (r.getMovie().getYear().equals(year)){
                ratings.add(r);
            }
        }
        Collections.sort(ratings);
        final List<Movie> moviesInYear = new ArrayList<>();
        for (final Rating r: ratings) {
            moviesInYear.add(r.getMovie());
        }
        return moviesInYear;
    }

    /**
     * Sorts the movie by the user's major
     * @return the list of the movie sorted from highest to lowest rating
     */
    public static List<Movie> sortByMajor(){
        final List<Rating> ratedMovieList = RatingStorage.getInstance().getRatings();
        final  List<Rating> ratings = new ArrayList<>();
        final String currentLoggedIn = LoginActivity.getCurrentLoggedInUser();
        final  User currentUser = UserStorage.getInstance().findUserByName(currentLoggedIn);
        for (final Rating r : ratedMovieList) {
            if (r.getMajor().equals(currentUser.getMajor())) {
                ratings.add(r);
            }
        }
        Collections.sort(ratings);
        final List<Movie> moviesForMajor = new ArrayList<>();
        for (final Rating r: ratings) {
            moviesForMajor.add(r.getMovie());
        }
        return moviesForMajor;
    }

}
