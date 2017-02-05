package team56.mrurt.activity;

import android.os.Bundle;
import android.view.Menu;
//import android.view.MenuInflater;
import android.view.MenuItem;
import team56.mrurt.R;

/**
 * Home Page of the App
 */
public class HomepageActivity extends MenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_activity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_homepage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.profile_id:
                viewProfile(item);
                return true;
            case R.id.logout_id:
                logout(item);
                return true;
            case R.id.search_id:
                searchMovie(item);
                return true;
            case R.id.recommend_id:
                recommendMovie(item);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}