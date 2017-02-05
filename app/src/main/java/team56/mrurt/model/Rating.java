package team56.mrurt.model;


import android.support.annotation.NonNull;

/**
 * A rating for a movie and stores the username that rated a movie
 */
public class Rating implements Comparable<Rating> {
    /**
     * strings major and user used in the constructor
     */
    private final String major, user;
    /**
     * movie object used in the constructor
     */
    private final Movie movie;
    /**
     * double that is used to give a movie a rating
     */
    private final double movieRating;

    /**
     * a constructor for the rating
     * @param majorP major of person giving rating
     * @param userP  user giving rating
     * @param movieP the movie being rated
     * @param movieRatingP the movieRating
     */
    public Rating(String majorP, String userP, Movie movieP, double movieRatingP) {
        this.major = majorP;
        this.user = userP;
        this.movie = movieP;
        this.movieRating = movieRatingP;
    }

    /**
     * Gets user Major
     * @return the major
     */
    public String getMajor() {
        return this.major;
    }

    /**
     * Gets username of User(toString())
     * @return the user
     * */
    public String getUser() {
        return this.user;
    }

    /**
     * Get the rated movie object
     * @return the movie
     */
    public Movie getMovie() {
        return this.movie;
    }

    /**
     * Get the rating of the movie
     * @return the movie id
     */
    public Double getMovieRating() {
        return this.movieRating;
    }

    /**
     *
     * @param r checks if the movies are equals
     * @return true if the movies are equals
     */
    public boolean equals(Rating r) {
        return (r.getMovie().equals(this.getMovie()));
    }

    @Override
    public int hashCode() {
        return this.user.hashCode();
    }

    /**
     * The movie_id and movie rating
     * @return the string representation of Rating
     */
    public String toString(){
        return movie.getTitle() + " " + movieRating + " " + user;
    }

    @Override
    public int compareTo(@NonNull Rating a) {
        return (a.getMovieRating().compareTo(this.getMovieRating()));
    }

}
