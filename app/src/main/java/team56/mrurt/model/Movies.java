package team56.mrurt.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Movies {

    /**
     * Constructor for Movies
     */
    private Movies(){

    }

    /**
     * List of Movie objects.
     */
    public static final List<Movie> ITEMS = new ArrayList<>();

    /**
     * Map of Movie objects with title and year.
     */
    public static final Map<String, Movie> ITEM_MAP = new HashMap<>();

    /**
     * Adds Movie to List and Map.
     * @param item is Movie to add.
     */
    public static void addItem(Movie item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.toString(), item);
    }


    /**
     * Clears the static list/map of Movie objects (used to reset for a new search).
     */
    public static void clear() {
        ITEMS.clear();
        ITEM_MAP.clear();
    }
}

