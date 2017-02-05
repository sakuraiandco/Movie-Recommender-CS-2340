package team56.mrurt.model.Database;

import android.provider.BaseColumns;

/**
 * Keeps track of all the column names for the tables
 */
class UserData {
    /**
     * Constructor
     */
    public UserData(){
    }

    /**
     * Keeps track of all the column names for the tables
     */
    public abstract static class TableInfo implements BaseColumns{
        //USER TABLE
        /**
         * user email for the table
         */
        public static final String USER_EMAIL = "USER_EMAIL"; //KEY
        /**
         * user username for the table
         */
        public static final String USER_NAME = "USER_NAME";
        /**
         *  user name for the table
         */
        public static final String NAME_USER = "NAME";
        /**
         * user major for the table
         */
        public static final String MAJOR_USER = "MAJOR";
        /**
         * user password for the table
         */
        public static final String PASSWORD_USER = "PASSWORD";
        /**
         * database name for the table
         */
        public static final String DATABASE_NAME = "USER_INFO";
        /**
         * user info for the table
         */
        public static final String TABLE_USER = "USER_INFO";
        /**
         * banned or not status for the table
         */
        public static final String BANNED_STATUS = "BANNED_STATUS";
        /**
         * admin or not status for the table
         */
        public static final String ADMIN_STATUS = "ADMIN_STATUS";
        //RATING DATABASE
        /**
         * title column for the database
         */
        public static final String MOVIE_TITLE = "TITLE";
        /**
         * title year for the database
         */
        public static final String MOVIE_YEAR = "YEAR";
        /**
         * synopsis column for the database
         */
        public static final String MOVIE_SYNOPSIS = "SYNOPSIS";
        /**
         * critics rating column for the database
         */
        public static final String MOVIE_CRITICS_RATING = "CRITICS_RATING";
        /**
         * user rating column for the database
         */
        public static final String MOVIE_RATE = "USER_RATING";
        /**
         * movie id column for the database
         */
        public static final String MOVIE_ID = "MOVIE_ID";
        /**
         * movie info column for the database
         */
        public static final String TABLE_MOVIE = "MOVIE_INFO";
    }
}
