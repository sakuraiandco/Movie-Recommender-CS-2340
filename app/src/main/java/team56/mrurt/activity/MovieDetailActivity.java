package team56.mrurt.activity;

/**
 * Created by Haruka
 */

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.app.AlertDialog;

import team56.mrurt.R;
import team56.mrurt.model.Database.DatabaseOperations;
import team56.mrurt.model.Movie;
import team56.mrurt.model.Rating;
import team56.mrurt.model.RatingStorage;
import team56.mrurt.model.User;
import team56.mrurt.model.UserStorage;
import team56.mrurt.presenters.MovieDetailFragment;


/**
 * An activity representing a single Movie detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link MovieListActivity}.
 */
public class MovieDetailActivity extends AppCompatActivity {
    /**
     * current user on the app
     */
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail_activity);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        final String currentLoggedIn = LoginActivity.getCurrentLoggedInUser();
        currentUser = UserStorage.getInstance().findUserByName(currentLoggedIn);

        final  ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }

        if (savedInstanceState == null) {
            final  Bundle arguments = new Bundle();
            arguments.putString(MovieDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(MovieDetailFragment.ARG_ITEM_ID));
            final MovieDetailFragment fragment = new MovieDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_detail_container, fragment)
                    .commit();
            //this.movie_id = MovieDetailFragment.ARG_ITEM_ID;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movie_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpTo(this, new Intent(this, MovieListActivity.class));
                return true;
            case R.id.rate_id:
                rateMovie();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /**
     * Opens an Alert Dialog allowing the user to rate a movie
     */
    private void rateMovie() {
        final AlertDialog.Builder movieRate = new AlertDialog.Builder(MovieDetailActivity.this);
        final Movie ratedMovie = (Movie) getIntent().getSerializableExtra("Movie Object");
        final RatingBar ratingBar1 = new RatingBar(MovieDetailActivity.this);
        ratingBar1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        final int five = 5;
        ratingBar1.setNumStars(five);
        ratingBar1.setRating(1.0f);
        ratingBar1.setStepSize(1);
        ratingBar1.setMax(five);

        final String m = getString(R.string.rateMovie1to5);
        movieRate.setMessage(m)
                .setTitle(getString(R.string.rateThisMovie)).setView(ratingBar1).setPositiveButton(getString(R.string.rate), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final Rating newRating = new Rating(MovieDetailActivity.this.currentUser.getMajor(), MovieDetailActivity.this.currentUser.getUsername(), ratedMovie, ratingBar1.getRating());
                        if (!RatingStorage.getInstance().getRatings().contains(newRating)) {
                            final Context c = getApplicationContext();
                            DatabaseOperations.getHelper(c).addRating(DatabaseOperations.getHelper(c),newRating);
                            RatingStorage.getInstance().setRatings(DatabaseOperations.getHelper(c).getAllRatings());
                            RatingStorage.getInstance().addRating(newRating);
                            MovieDetailActivity.this.currentUser.addRating(newRating);
                        } else {
                            final  Context c = getApplicationContext();
                            DatabaseOperations.getHelper(c).updateRating(DatabaseOperations.getHelper(c), newRating);
                            RatingStorage.getInstance().updateRatingDatabase(DatabaseOperations.getHelper(c).getAllRatings());
                            MovieDetailActivity.this.currentUser.removeRating(newRating);
                            MovieDetailActivity.this.currentUser.addRating(newRating);
                        }
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        final  AlertDialog ratingDialog = movieRate.create();
        ratingDialog.show();
        ratingBar1.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
    }
}
