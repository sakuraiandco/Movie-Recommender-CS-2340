package team56.mrurt.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import team56.mrurt.R;
import team56.mrurt.model.Database.DatabaseOperations;
import team56.mrurt.model.User;
import team56.mrurt.model.UserStorage;

/**
 * The user's profile page
 */
public class ProfileActivity extends AppCompatActivity {

    /**
     * the editTexts used for changing profile attributes
     */
    private EditText mEmailView, mUsernameView, mNameView, mMajorView, mPasswordView;

    /**
     * the current user logged in to the app
     */
    private final String currentLoggedIn = LoginActivity.getCurrentLoggedInUser();
    /**
     * a user object
     */
    private User user;
    /**
     * context c
     */
    private final Context c = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        UserStorage.getInstance().updateUserDatabase(DatabaseOperations.getHelper(c).getUsers());
        user = UserStorage.getInstance().findUserByName(currentLoggedIn);
        mEmailView = (EditText) findViewById(R.id.userEmail);
        mUsernameView = (EditText) findViewById(R.id.userUsername);
        mNameView = (EditText) findViewById(R.id.userName);
        mMajorView = (EditText) findViewById(R.id.userMajor);
        mPasswordView = (EditText) findViewById(R.id.userPassword);

        mEmailView.setText(user.getEmail(), TextView.BufferType.EDITABLE);
        mUsernameView.setText(user.getUsername(), TextView.BufferType.EDITABLE);
        mNameView.setText(user.getName(), TextView.BufferType.EDITABLE);
        mMajorView.setText(user.getMajor(), TextView.BufferType.EDITABLE);
        mPasswordView.setText(user.getPassword(), TextView.BufferType.EDITABLE);
    }

    @Override
    public void onBackPressed() {
        //Go back to HomePage instead logging out
        final Intent homeIntent = new Intent(ProfileActivity.this, HomepageActivity.class);
        startActivity(homeIntent);
        finish();
    }

    /**
     * Saves the user, if any changes have been made to the profile, the old user
     * data is removed and the new data is added
     * @param view The View
     */
    public void saveChanges(View view){
        final String e1 = mEmailView.getText().toString();
        final String u1 = mUsernameView.getText().toString();
        final String n1 = mNameView.getText().toString();
        final String m1 = mMajorView.getText().toString();
        final String p1 = mPasswordView.getText().toString();

        //Deletes and updates the user's profile info.
        if(currentLoggedIn.equals(u1)) {
            DatabaseOperations.getHelper(c).updateUserRating(DatabaseOperations.getHelper(c), u1, currentLoggedIn);
        }
        DatabaseOperations.getHelper(c).deleteUser(DatabaseOperations.getHelper(c), user.getEmail());
        DatabaseOperations.getHelper(c).putUserInformation(DatabaseOperations.getHelper(c), e1, u1, n1, m1, p1, 0, 0);
        UserStorage.getInstance().updateUserDatabase(DatabaseOperations.getHelper(c).getUsers());

        LoginActivity.setCurrentLoggedInUser(u1);

        final Intent intent = new Intent(this, HomepageActivity.class);
        startActivity(intent);
        finish();
    }
}