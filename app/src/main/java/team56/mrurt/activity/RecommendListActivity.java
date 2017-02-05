package team56.mrurt.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import team56.mrurt.R;
import team56.mrurt.model.Movie;
import team56.mrurt.model.Movies;
import team56.mrurt.presenters.MovieDetailFragment;

/**
 * Displays the movie list from recommendations
 */
public class RecommendListActivity extends AppCompatActivity{
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_list_activity);

        final  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        final View recyclerView = findViewById(R.id.movie_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.movie_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        //Here we extract the objects out of the intent
        //Note that to pass them, they have to be serializable
        /*
      List of Movie objects
     */
        @SuppressWarnings("unchecked")
        final List<Movie> movies = (List<Movie>) getIntent().getSerializableExtra("movies");
        for (final Movie s : movies) {
            Movies.addItem(s);
        }
    }

    @Override
    public void onBackPressed() {
        //Go back to SearchMovieActivity instead of closing app.
        Movies.clear();
        final Intent searchIntent = new Intent(RecommendListActivity.this, RecommendMovieActivity.class);
        startActivity(searchIntent);
        finish();
    }


    /**
     * Sets up RecyclerView scrolling list.
     * @param recyclerView view to set up with correct items from list
     */
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter());
    }

    /**
     * This is an Adapter for RecyclerView that can show objects in a scrolling list. This sets up
     * the View with the correct objects.
     */
    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        /**
         * a list of Movies
         */
        private final List<Movie> mValues;

        /**
         * constructor
         */
        public SimpleItemRecyclerViewAdapter() {
            mValues = Movies.ITEMS;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.movie_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).toString());
            holder.mContentView.setText("");
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        final Bundle arguments = new Bundle();
                        arguments.putString(MovieDetailFragment.ARG_ITEM_ID, holder.mItem.getTitle());
                        final MovieDetailFragment fragment = new MovieDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.movie_detail_container, fragment)
                                .commit();
                    } else {
                        final Context context = v.getContext();
                        final Intent intent = new Intent(context, MovieDetailActivity.class);
                        intent.putExtra(MovieDetailFragment.ARG_ITEM_ID, holder.mItem.toString());
                        intent.putExtra("Movie Object", holder.mItem);
                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        /**
         * Container for a View in RecyclerView.
         */
        public class ViewHolder extends RecyclerView.ViewHolder {
            /**
             * a View object
             */
            private final View mView;
            /**
             * a Text view used for id
             */
            private final TextView mIdView;
            /**
             * a text view used for content
             */
            private final TextView mContentView;
            /**
             * a movie object
             */
            private Movie mItem;

            /**
             * constructor
             * @param view a view object
             */
            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }

}
