package com.example.alexlabanowski.movierecommenderusingrottentomatoes;

import java.util.ArrayList;

import team56.mrurt.model.Rating;
import team56.mrurt.model.Movie;
import team56.mrurt.model.RatingStorage;
import team56.mrurt.model.Recommend;

import org.junit.Test;
import static org.junit.Assert.*;

public class SortByYearTest {

    @Test
    public void testRecommendationsByYearWithRatingsForYear() throws Exception {
        Movie movie1 = new Movie();
        movie1.setTitle("SpiderMan");
        movie1.setYear("2002");
        Movie movie2 = new Movie();
        movie2.setTitle("Star Wars: Episode II");
        movie2.setYear("2002");
        Movie movie3 = new Movie();
        movie3.setTitle("Akira");
        movie3.setYear("2002");
        Movie movie4 = new Movie();
        movie4.setTitle("The Simpsons Movie");
        movie4.setYear("2007");
        Rating rating1 = new Rating("cs", "Kui", movie1, 4);
        Rating rating2 = new Rating("cs", "Alex", movie2, 5);
        Rating rating3 = new Rating("cs", "Natalie", movie3, 2);
        Rating rating4 = new Rating("cm", "Connor", movie4, 4);
        RatingStorage rs = RatingStorage.getInstance();
        rs.addRating(rating1);
        rs.addRating(rating2);
        rs.addRating(rating3);
        rs.addRating(rating4);
        ArrayList<Movie> testYear2002 = (ArrayList<Movie>) Recommend.sortByYear("2002");
        assertEquals(3, testYear2002.size());
        assertEquals("Star Wars: Episode II", testYear2002.get(0).getTitle());
        assertEquals("SpiderMan", testYear2002.get(1).getTitle());
        assertEquals("Akira", testYear2002.get(2).getTitle());
        ArrayList<Movie> testYear2007 = (ArrayList<Movie>) Recommend.sortByYear("2007");
        assertEquals(1, testYear2007.size());
        assertEquals("The Simpsons Movie", testYear2007.get(0).getTitle());

    }

    @Test
    public void testRecommendationsByYearWithoutRatingsForYear() throws Exception {
        Movie movie1 = new Movie();
        movie1.setTitle("SpiderMan");
        movie1.setYear("2002");
        Movie movie2 = new Movie();
        movie2.setTitle("Star Wars: Episode II");
        movie2.setYear("2002");
        Movie movie3 = new Movie();
        movie3.setTitle("Akira");
        movie3.setYear("2002");
        Movie movie4 = new Movie();
        movie4.setTitle("The Simpsons Movie");
        movie4.setYear("2007");
        Rating rating1 = new Rating("cs", "Kui",movie1, 4);
        Rating rating2 = new Rating("cs", "Alex", movie2, 5);
        Rating rating3 = new Rating("cs", "Natalie", movie3, 2);
        Rating rating4 = new Rating("cm", "Connor", movie4, 4);
        RatingStorage rs = RatingStorage.getInstance();
        rs.addRating(rating1);
        rs.addRating(rating2);
        rs.addRating(rating3);
        rs.addRating(rating4);
        ArrayList<Movie> testYear2016 = (ArrayList<Movie>) Recommend.sortByYear("2016");
        assertEquals(0, testYear2016.size());
    }


}
