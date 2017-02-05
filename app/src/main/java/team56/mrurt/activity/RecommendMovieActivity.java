package team56.mrurt.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import team56.mrurt.R;
import team56.mrurt.model.Database.DatabaseOperations;
import team56.mrurt.model.Movie;
import team56.mrurt.model.RatingStorage;
import team56.mrurt.model.Recommend;


/*
* Sorts Movies into a list by specific Criteria
 */
public class RecommendMovieActivity extends AppCompatActivity {
    /**
     * context c
     */
    private final Context c = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommend_movie_activity);

        final Button recommendYear = (Button) findViewById(R.id.recommend_year);

        final Button recommendMajor = (Button) findViewById(R.id.submit);

        recommendYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText year = (EditText) findViewById(R.id.username);
                RatingStorage.getInstance().updateRatingDatabase(DatabaseOperations.getHelper(c).getAllRatings());
                final  List<Movie> movie = Recommend.sortByYear(year.getText().toString());
                changeView(movie);
            }
        });

        recommendMajor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RatingStorage.getInstance().updateRatingDatabase(DatabaseOperations.getHelper(c).getAllRatings());
                final  List<Movie> movie = Recommend.sortByMajor();
                changeView(movie);
            }
        });
    }

    @Override
    public void onBackPressed() {
        //Go back to Homepage instead of closing app.
        final Intent searchIntent = new Intent(RecommendMovieActivity.this, HomepageActivity.class);
        startActivity(searchIntent);
        finish();
    }

    /**
     * This method changes to our new list view of the movies, but we have to pass the
     * movie array into the intent so the new screen gets the data.
     *
     * @param movies the list of movie objects we created from the recommendations
     */
    private void changeView(List<Movie> movies) {
        final Intent viewResultsIntent = new Intent(this, RecommendListActivity.class);
        viewResultsIntent.putExtra("movies", (ArrayList<Movie>) movies);
        startActivity(viewResultsIntent);
        finish();
    }
}
