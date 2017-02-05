package team56.mrurt.model;
import android.support.annotation.NonNull;

import java.io.Serializable;

public class Movie implements Serializable, Comparable<Movie> {
    /**
     * the movie attributes
     */
    private String title, year, synopsis, criticsRating, id;
    /**
     * ratings the user has given to movies
     */
    private double userRatings;

    /**
     * Sets Movie title.
     * @param titleP is movie title.
     */
    public void setTitle(String titleP) {
        this.title = titleP;
    }

    /**
     * Sets Movie release year.
     * @param yearP is release year of movie.
     */
    public void setYear(String yearP) {
        this.year = yearP;
    }

    /**
     * Sets Movie synopsis.
     * @param synopsisP movie synopsis.
     */
    public void setSynopsis(String synopsisP) {
        this.synopsis = synopsisP;
    }

    /**
     * Sets critics rating
     * @param criticsRatingP is critics rating
     */
    public void setCriticsRating(String criticsRatingP) {
        this.criticsRating = criticsRatingP;
    }

    /**
     * set the Id
     * @param idP a string
     */
    public void setId(String idP) {
        this.id = idP;
    }

    /**
     * Gets Movie title.
     * @return the title.
     */

    public String getTitle() {
        return this.title;
    }

    /**
     * Gets Move release year.
     * @return the year.
     */
    public String getYear() {
        return this.year;
    }

    /**
     * Gets Movie synopsis.
     * @return the synopsis.
     */
    public String getSynopsis() {
        return this.synopsis;
    }

    /**
     * Gets critics rating
     * @return the rating.
     */
    public String getCriticsRating() {
        return this.criticsRating;
    }

    /**
     * Gets movie title and year.
     * @return a string that contains title and year of movie.
     */

    public String getId() {
        return this.id;
    }

    /**
     * returns a string of the title of the movie with the year and critics rating
     * @return The title of the movie along with the year and critics rating
     */
    public String toString() {
        return (getTitle() + " (" + getYear() + ") " + getCriticsRating() + " %");
    }

    @Override
    public int compareTo(@NonNull Movie a) {
        return (int) this.userRatings - (int) a.userRatings;
    }

    /**
     * method to check if two movies are equal
     * @param m movies being compared to
     * @return if movie equal to
     */
    public boolean equals(Movie m) {
        return (m.getTitle().equals(this.title));
    }

    /**
     * produces a hashcode for the title
     * @return the hashcode
     */
    public int hashCode() {
        return this.getTitle().hashCode();
    }
}
