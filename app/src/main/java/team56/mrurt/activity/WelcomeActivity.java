package team56.mrurt.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import team56.mrurt.R;
import team56.mrurt.model.Database.DatabaseOperations;
import team56.mrurt.model.UserStorage;


public class WelcomeActivity extends AppCompatActivity {
    public Context c = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);
        //Adds the admin stuff, only once! Then updates the userStorage for local storage
        //DatabaseOperations.getHelper(c).putUserInformation(DatabaseOperations.getHelper(c), "admin@admin.com", "admin", "admin", "administrator", "password1", 0, 1);
        UserStorage.getInstance().updateUserDatabase(DatabaseOperations.getHelper(this).getUsers());
    }

    /**
     * Takes the user to a log in page
     * @param v The View
     */
    public void openLogin(View v) {
        final Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        onPause();
    }

    /**
     * Takes the user to a registration page
     * @param v The View
     */
    public void openRegister(View v) {
        final Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        onPause();
    }
}
