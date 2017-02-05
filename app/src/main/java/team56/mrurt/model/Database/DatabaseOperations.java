package team56.mrurt.model.Database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import team56.mrurt.model.Movie;
import team56.mrurt.model.Rating;
import team56.mrurt.model.User;

/**
 * Created by Haruka
 */

/**
 * A SQLiteOpenHelper class that manages all the database operations
 */
public final class DatabaseOperations extends SQLiteOpenHelper {
    /**
     * Database version int
     */
    private static final int DBVERSION = 1;

    /**
     * string "Database Operations"
     */
    private static final String DATABASEOP = "Database Operations";

    /**
     * Constructor for the class
     * @param context the context
     */
    private DatabaseOperations (Context context) {
        super(context, UserData.TableInfo.DATABASE_NAME, null, DBVERSION);
        Log.d(DATABASEOP, "Database Created");
    }

    /**
     * instance of DataBaseOperations
     */
    private static DatabaseOperations instance;

    /**
     * Gets an instance of DatabaseOperations
     * @param context the application context
     * @return returns the instance of DatabaseOperations
     */
    public static synchronized DatabaseOperations getHelper(Context context) {
        if (instance == null) {
            instance = new DatabaseOperations(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String text = " TEXT,";
        final String createTableUser = "CREATE TABLE IF NOT EXISTS " + UserData.TableInfo.TABLE_USER + "(" +
                UserData.TableInfo.USER_EMAIL + text + UserData.TableInfo.USER_NAME + text + UserData.TableInfo.NAME_USER + text +
                UserData.TableInfo.MAJOR_USER + text + UserData.TableInfo.BANNED_STATUS + " INTEGER," + UserData.TableInfo.ADMIN_STATUS + " INTEGER," + UserData.TableInfo.PASSWORD_USER + " TEXT );";
        db.execSQL(createTableUser);
        final String createTableRatings = "CREATE TABLE IF NOT EXISTS " + UserData.TableInfo.TABLE_MOVIE + "(" +
                UserData.TableInfo.USER_NAME + text + UserData.TableInfo.MAJOR_USER + text +
                UserData.TableInfo.MOVIE_TITLE + text + UserData.TableInfo.MOVIE_YEAR + text + UserData.TableInfo.MOVIE_SYNOPSIS + text + UserData.TableInfo.MOVIE_CRITICS_RATING + text +
                UserData.TableInfo.MOVIE_RATE + text + UserData.TableInfo.MOVIE_ID + " TEXT );";
        db.execSQL(createTableRatings);
        Log.d(DATABASEOP, "Table Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //ON UPGRADE DROP OLDER TABLES
        db.execSQL("DROP TABLE IF EXISTS " + UserData.TableInfo.TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + UserData.TableInfo.TABLE_MOVIE);

        onCreate(db);
    }

    /**
     * put user in table info
     * @param dop the database
     * @param email user email
     * @param username user username
     * @param name user name
     * @param major user major
     * @param password user's password
     */
    public void putUserInformation(DatabaseOperations dop, String email, String username, String name, String major, String password, int banned, int admin) {
        final SQLiteDatabase sQ =  dop.getWritableDatabase();
        final ContentValues cv = new ContentValues();
        cv.put(UserData.TableInfo.USER_EMAIL, email);
        cv.put(UserData.TableInfo.USER_NAME, username);
        cv.put(UserData.TableInfo.NAME_USER, name);
        cv.put(UserData.TableInfo.MAJOR_USER, major);
        cv.put(UserData.TableInfo.PASSWORD_USER, password);
        cv.put(UserData.TableInfo.BANNED_STATUS, banned);
        cv.put(UserData.TableInfo.ADMIN_STATUS, admin);

        //insert rows
        sQ.insert(UserData.TableInfo.TABLE_USER, null, cv);
        Log.d(DATABASEOP, "Information inserted");
    }

    /**
     * put rating into movie table
     * @param db the database
     * @param r the rating being added to the database
     */
    public void addRating(DatabaseOperations db, Rating r) {
        final SQLiteDatabase sQ =  db.getWritableDatabase();
        final ContentValues cv = new ContentValues();

        cv.put(UserData.TableInfo.USER_NAME, r.getUser());
        cv.put(UserData.TableInfo.MOVIE_TITLE, r.getMovie().getTitle());
        cv.put(UserData.TableInfo.MAJOR_USER, r.getMajor());
        cv.put(UserData.TableInfo.MOVIE_YEAR, r.getMovie().getYear());
        cv.put(UserData.TableInfo.MOVIE_SYNOPSIS, r.getMovie().getSynopsis());
        cv.put(UserData.TableInfo.MOVIE_CRITICS_RATING, r.getMovie().getCriticsRating());
        cv.put(UserData.TableInfo.MOVIE_RATE, r.getMovieRating());
        cv.put(UserData.TableInfo.MOVIE_ID, r.getMovie().getId());

        sQ.insert(UserData.TableInfo.TABLE_MOVIE, null, cv);
        Log.d(DATABASEOP, "rRating inserted");
    }

    /**
     * get all the users
     * @return returns a list of all the registered users
     */
    public List<User> getUsers() {
        final List<User> allUsers = new ArrayList<>();
        final String selectQuery = "SELECT  * FROM " + UserData.TableInfo.TABLE_USER;

        final SQLiteDatabase db = this.getReadableDatabase();
        final Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                final String email = c.getString(c.getColumnIndex(UserData.TableInfo.USER_EMAIL));
                final String username = c.getString(c.getColumnIndex(UserData.TableInfo.USER_NAME));
                final String name = c.getString(c.getColumnIndex(UserData.TableInfo.NAME_USER));
                final String major = c.getString(c.getColumnIndex(UserData.TableInfo.MAJOR_USER));
                final String password = c.getString(c.getColumnIndex(UserData.TableInfo.PASSWORD_USER));
                final int banned = c.getInt(c.getColumnIndex(UserData.TableInfo.BANNED_STATUS));
                final int admin = c.getInt(c.getColumnIndex(UserData.TableInfo.ADMIN_STATUS));

                final User u = new User(email, username, name, major, password);
                if(admin == 0) {
                    u.setAdminStatus(false);
                } else {
                    u.setAdminStatus(true);
                }
                if(banned == 0) {
                    u.setBanStatus(false);
                } else {
                    u.setBanStatus(true);
                }

                // adding to allUsers
                allUsers.add(u);
            } while (c.moveToNext());
        }
        c.close();
        return allUsers;
    }

    /**
     * Gets a single user
     * @param username username of the user
     * @return returns a single user
     */
    public User getSingleUser(String username) {
        final SQLiteDatabase db = this.getReadableDatabase();
        final String selectQuery = "SELECT  * FROM " + UserData.TableInfo.TABLE_USER + " WHERE "
                + UserData.TableInfo.USER_NAME + " = '" + username + "'";

        final Cursor c = db.rawQuery(selectQuery, null);

        if (c != null && c.moveToFirst()) {
            c.moveToFirst();
            final String email = c.getString(c.getColumnIndex(UserData.TableInfo.USER_EMAIL));
            final String username1 = c.getString(c.getColumnIndex(UserData.TableInfo.USER_NAME));
            final String name = c.getString(c.getColumnIndex(UserData.TableInfo.NAME_USER));
            final String major = c.getString(c.getColumnIndex(UserData.TableInfo.MAJOR_USER));
            final String password = c.getString(c.getColumnIndex(UserData.TableInfo.PASSWORD_USER));
            final int banned = c.getInt(c.getColumnIndex(UserData.TableInfo.BANNED_STATUS));
            final int admin = c.getInt(c.getColumnIndex(UserData.TableInfo.ADMIN_STATUS));

            final User u = new User(email, username1, name, major, password);

            if(admin == 0) {
                u.setAdminStatus(false);
            } else {
                u.setAdminStatus(true);
            }
            if(banned == 0) {
                u.setBanStatus(false);
            } else {
                u.setBanStatus(true);
            }
            c.close();
            return u;
        } else {
            return new User("","","","","");
        }
    }

    public User getSingleUserEmail(String e) {
        final SQLiteDatabase db = this.getReadableDatabase();
        final String selectQuery = "SELECT  * FROM " + UserData.TableInfo.TABLE_USER + " WHERE "
                + UserData.TableInfo.USER_EMAIL + " = " + "'" + e + "'";
        final Cursor c = db.rawQuery(selectQuery, null);

        if (c != null && c.moveToFirst()) {
            c.moveToFirst();
            final String email = c.getString(c.getColumnIndex(UserData.TableInfo.USER_EMAIL));
            final String username1 = c.getString(c.getColumnIndex(UserData.TableInfo.USER_NAME));
            final String name = c.getString(c.getColumnIndex(UserData.TableInfo.NAME_USER));
            final String major = c.getString(c.getColumnIndex(UserData.TableInfo.MAJOR_USER));
            final String password = c.getString(c.getColumnIndex(UserData.TableInfo.PASSWORD_USER));
            final int banned = c.getInt(c.getColumnIndex(UserData.TableInfo.BANNED_STATUS));
            final int admin = c.getInt(c.getColumnIndex(UserData.TableInfo.ADMIN_STATUS));

            final User u = new User(email, username1, name, major, password);

            if(admin == 0) {
                u.setAdminStatus(false);
            } else {
                u.setAdminStatus(true);
            }
            if(banned == 0) {
                u.setBanStatus(false);
            } else {
                u.setBanStatus(true);
            }
            c.close();
            return u;
        } else {
            return new User("","","","","");
        }
    }

    /**
     * Gets all the ratings
     * @return returns an list of all the Ratings
     */
    public List<Rating> getAllRatings() {
        final SQLiteDatabase db = this.getReadableDatabase();
        final List<Rating> allRatings = new ArrayList<>();

        final String selectQuery = "SELECT  * FROM " + UserData.TableInfo.TABLE_MOVIE;
        final Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                final  String username = c.getString(c.getColumnIndex(UserData.TableInfo.USER_NAME));
                final String title = c.getString(c.getColumnIndex(UserData.TableInfo.MOVIE_TITLE));
                final String major = c.getString(c.getColumnIndex(UserData.TableInfo.MAJOR_USER));
                final String year = c.getString(c.getColumnIndex(UserData.TableInfo.MOVIE_YEAR));
                final String synopsis = c.getString(c.getColumnIndex(UserData.TableInfo.MOVIE_SYNOPSIS));
                final String criticRate = c.getString(c.getColumnIndex(UserData.TableInfo.MOVIE_CRITICS_RATING));
                final double rating = c.getFloat(c.getColumnIndex(UserData.TableInfo.MOVIE_RATE));
                final String id = c.getString(c.getColumnIndex(UserData.TableInfo.MOVIE_ID));

                final  Movie m = new Movie();
                m.setTitle(title);
                m.setYear(year);
                m.setSynopsis(synopsis);
                m.setCriticsRating(criticRate);
                m.setId(id);

                final  Rating r = new Rating(major, username,m,rating);

                // adding to allUsers
                allRatings.add(r);
            } while (c.moveToNext());
        }
        c.close();
        return allRatings;
    }

    /**
     * Updates the user in the database
     * @param d The instance of the database
     * @param u the user we are going to update
     */
    public void updateUser(DatabaseOperations d, User u) {
        final SQLiteDatabase db = d.getWritableDatabase();
        final ContentValues cv = new ContentValues();

        cv.put(UserData.TableInfo.BANNED_STATUS, u.getBanStatus());
        cv.put(UserData.TableInfo.ADMIN_STATUS, u.getAdminStatus());

        db.update(UserData.TableInfo.TABLE_USER, cv, " USER_EMAIL = ?", new String[]{u.getEmail()});
    }

    /**
     * Updates the user's movie rating in the database
     * @param d The instance of the database
     * @param r the rating we are going to update
     */
    public void updateRating(DatabaseOperations d, Rating r) {
        final SQLiteDatabase db = d.getWritableDatabase();
        final ContentValues values = new ContentValues();

        values.put(UserData.TableInfo.MOVIE_RATE, r.getMovieRating());
        db.update(UserData.TableInfo.TABLE_MOVIE, values, " USER_NAME = ?", new String[]{r.getUser()});
    }

    /**
     * Updates the user's movie rating in the database
     * @param d The instance of the database
     * @param newUsername the new user name
     * @param oldUsername the users old username
     */
    public void updateUserRating(DatabaseOperations d, String newUsername, String oldUsername) {
        final SQLiteDatabase db = d.getWritableDatabase();
        final ContentValues values = new ContentValues();

        values.put(UserData.TableInfo.USER_NAME, newUsername);
        db.update(UserData.TableInfo.TABLE_MOVIE, values, " USER_NAME = ?", new String[]{oldUsername});
    }

    /**
     * Deletes User based on their email
     * @param d The instance of the database
     * @param email the user we will delete
     */
    public void deleteUser(DatabaseOperations d, String email) {
        final  SQLiteDatabase db = d.getWritableDatabase();
        db.delete(UserData.TableInfo.TABLE_USER, UserData.TableInfo.USER_EMAIL + " = ?", new String[]{email});
    }
}
