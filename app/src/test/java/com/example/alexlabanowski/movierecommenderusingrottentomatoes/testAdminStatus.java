package com.example.alexlabanowski.movierecommenderusingrottentomatoes;



import org.junit.Test;


import team56.mrurt.model.Movie;
import team56.mrurt.model.User;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
/**
 * Created by Wyckoff
 */


public class testAdminStatus {

    @Test
    public void adminStatusTest() {
        User u1 = new User("e1", "n1", "u1", "cs", "p1");
        User u2 = new User("e2", "n2", "u2", "ee", "p2");
        User u3 = new User("e3", "n3", "u3", "cm", "p3");

        Movie m = new Movie();
        m.setTitle("Chicken Little");
        m.setYear("1980");


        u1.setAdminStatus(false);
        u2.setAdminStatus(false);
        u3.setAdminStatus(true);


        assertTrue(u1.getAdminStatus() == 0);
        assertTrue(u2.getAdminStatus() == 0);
        assertFalse(u1.getAdminStatus() == 1);
        assertFalse(u2.getAdminStatus() == 1);

        assertTrue(u3.getAdminStatus() == 1);
        assertFalse(u3.getAdminStatus() == 0);
    }
}
