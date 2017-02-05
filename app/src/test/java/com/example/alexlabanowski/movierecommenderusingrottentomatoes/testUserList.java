package com.example.alexlabanowski.movierecommenderusingrottentomatoes;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import team56.mrurt.model.User;
import team56.mrurt.model.UserStorage;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@RunWith(MockitoJUnitRunner.class)
public class testUserList extends TestCase {
    @Test
    public void testUserListTest() {
        System.out.println("Testing setting the UserList");
        List<User> test = new ArrayList<>();
        List<String> testAfter;
        test.add(new User("test1@m.com", "test1", "Tester1", "CS", "1234"));
        test.add(new User("test2@m.com", "test2", "Tester2", "ME", "1234"));
        test.add(new User("test3@m.com", "test3", "Tester3", "AE", "1234"));
        test.add(new User("test4@m.com", "test4", "Tester4", "IE", "1234"));
        UserStorage.getInstance().updateUserDatabase(test);
        testAfter = Arrays.asList(UserStorage.getInstance().toArray());
        assertEquals(testAfter.size(), 4);

        assertTrue(testAfter.contains("test1"));
        assertTrue(testAfter.contains("test2"));
        assertTrue(testAfter.contains("test2"));
        assertTrue(testAfter.contains("test3"));
    }

}