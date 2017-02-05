package team56.mrurt.activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import team56.mrurt.R;
import team56.mrurt.model.Movie;

/**
 * This is the Search Movie Page
 * Here you can search by tile, new Releases and new on DVD
 */
public class SearchMovieActivity extends AppCompatActivity {
    /** this is our volley queue for holding our REST requests.  Volley will create the necessary threading to
     * handle the requests for us in the background.
     */
    private RequestQueue queue;

    /**
     * a string
     */
    private String response;
    /**
     * a SearchView
     */
    private SearchView movieSearchView;

    /**
     * a string "movies"
     */
    private static final String MOVIES = "movies";

    /**
     * a string "error"
     */
    private static final String ERRORS = "error";

    /**
     * a string "JSON error"
     */
    private static final String JSONERROR = "JSON error";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_movie_activity);
        queue = Volley.newRequestQueue(this);

        this.movieSearchView = (SearchView) findViewById(R.id.movieSearchView);
        final  Button movieSearchButton = (Button) findViewById(R.id.movieSearchButton);
        final  Button newReleaseSearchButton = (Button) findViewById(R.id.newReleaseSearch);
        final Button recentDVDSearchButton = (Button) findViewById(R.id.recentDVDSearch);

        movieSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearchRequested();
            }
        });
        newReleaseSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRecentReleases();
            }
        });
        recentDVDSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRecentDVDs();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //Go back to HomePage instead logging out
        final Intent homeIntent = new Intent(SearchMovieActivity.this, HomepageActivity.class);
        startActivity(homeIntent);
        finish();
    }

    @Override
    public boolean onSearchRequested() {
        String url = "http://api.rottentomatoes.com/api/public/v1.0/movies.json?apikey=yedukp76ffytfuy24zsqk7f5";
        final String query = movieSearchView.getQuery().toString().replace(" ", "+");

        if(query.equals("")) return false;

        url += ("&q=" + query + "&page_limit=40");
        final JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject resp) {
                        response = resp.toString();
                        JSONArray array = null;
                        try {
                            array = resp.getJSONArray(MOVIES);
                        } catch (JSONException e) {
                            Log.d(JSONERROR, ERRORS);
                        }
                        assert array != null;

                        final  ArrayList<Movie> movies = new ArrayList<>();
                        for(int i=0; i < array.length(); i++) {
                            try {
                                final  JSONObject jsonObject = array.getJSONObject(i);
                                final JSONObject ratingsJSON = jsonObject.getJSONObject("ratings");
                                final  Movie m = new Movie();
                                m.setTitle(jsonObject.optString("title"));
                                m.setYear(jsonObject.optString("year"));
                                m.setSynopsis(jsonObject.optString("synopsis"));
                                m.setCriticsRating(ratingsJSON.optString("critics_score"));
                                m.setId(jsonObject.optString("id"));
                                movies.add(m);
                            } catch (JSONException e) {
                                Log.d("VolleyApp", "Failed to get JSON object");
                                Log.d(JSONERROR, ERRORS);
                            }
                        }
                        changeView(movies);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        response = "JSon Request Failed!!";
                    }
                });

        queue.add(jsObjRequest);
        return true;
    }

    /**
     * Gets recent releases from RottenTomatoes API and displays them.
     */
    private void getRecentReleases() {
        final String url = "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/opening.json?apikey=yedukp76ffytfuy24zsqk7f5";

        final JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject resp) {
                        response = resp.toString();
                        JSONArray array = null;
                        try {
                            array = resp.getJSONArray(MOVIES);
                        } catch (JSONException e) {
                            Log.d(JSONERROR, ERRORS);
                        }
                        assert array != null;
                        final  ArrayList<Movie> movies = new ArrayList<>();
                        for(int i=0; i < array.length(); i++) {
                            try {
                                final JSONObject jsonObject = array.getJSONObject(i);
                                final JSONObject ratingsJSON = jsonObject.getJSONObject("ratings");
                                final Movie m = new Movie();
                                m.setTitle(jsonObject.optString("title"));
                                m.setYear(jsonObject.optString("year"));
                                m.setSynopsis(jsonObject.optString("synopsis"));
                                m.setCriticsRating(ratingsJSON.optString("critics_score"));
                                movies.add(m);
                            } catch (JSONException e) {
                                Log.d("MovieApp", "Failed to get JSON object");
                                Log.d(JSONERROR, ERRORS);
                            }
                        }
                        changeView(movies);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        response = "JSon Request Failed!!";
                    }
                });
        queue.add(jsObjRequest);
    }

    /**
     * Gets recent DVDs from RottenTomatoes API and displays.
     */
    private void getRecentDVDs() {
        final String url = "http://api.rottentomatoes.com/api/public/v1.0/lists/dvds/new_releases.json?apikey=yedukp76ffytfuy24zsqk7f5";

        final JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject resp) {
                        response = resp.toString();
                        JSONArray array = null;
                        try {
                            array = resp.getJSONArray(MOVIES);
                        } catch (JSONException e) {
                            Log.d(JSONERROR, ERRORS);
                        }
                        assert array != null;
                        final ArrayList<Movie> movies = new ArrayList<>();
                        for(int i=0; i < array.length(); i++) {
                            try {
                                final JSONObject jsonObject = array.getJSONObject(i);
                                final JSONObject ratingsJSON = jsonObject.getJSONObject("ratings");
                                final Movie m = new Movie();
                                m.setTitle(jsonObject.optString("title"));
                                m.setYear(jsonObject.optString("year"));
                                m.setSynopsis(jsonObject.optString("synopsis"));
                                m.setCriticsRating(ratingsJSON.optString("critics_score"));
                                movies.add(m);
                            } catch (JSONException e) {
                                Log.d("MovieApp", "Failed to get JSON object");
                                Log.d(JSONERROR, ERRORS);
                            }
                        }
                        changeView(movies);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        response = "JSon Request Failed!!";
                    }
                });
        queue.add(jsObjRequest);
    }

    /**
     * This method changes to our new list view of the movies, but we have to pass the
     * movie array into the intent so the new screen gets the data.
     *
     * @param movies the list of movie objects we created from the JSon response
     */
    private void changeView(List<Movie> movies) {
        final Intent viewResultsIntent = new Intent(this, MovieListActivity.class);
        viewResultsIntent.putExtra(MOVIES, (ArrayList<Movie>)movies);
        startActivity(viewResultsIntent);
        finish();
    }

}
