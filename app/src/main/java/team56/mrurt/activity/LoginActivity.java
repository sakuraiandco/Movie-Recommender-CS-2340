package team56.mrurt.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;

import team56.mrurt.R;
import team56.mrurt.model.UserStorage;

public class LoginActivity extends AppCompatActivity {

    /**
     * EditText username used to login
     */
    private EditText mUsernameView;
    /**
     * EditText password used to login
     */
    private EditText mPasswordView;
    /**
     * the current user
     */
    private static String currentLoggedInUser;

    /**
     * returns the current user
     * @return logged in user
     */
    public static String getCurrentLoggedInUser() {
        return currentLoggedInUser;
    }

    /**
     * Set the current logged in user
     * @param str current l
     */
    public static void setCurrentLoggedInUser(String str) {
        currentLoggedInUser = str;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        mUsernameView = (EditText) findViewById(R.id.login_username);
        mPasswordView = (EditText) findViewById(R.id.password);

        mUsernameView.setTextColor(Color.BLACK);
        mPasswordView.setTextColor(Color.BLACK);
    }

    /*public EditText getUsernameView() {
        return mUsernameView;
    }

    public EditText getPassWordView() {
        return  mPasswordView;
    }*/

    /**
     * Logs in the user if they input registered username and password
     * @param v The View
     */
    public void login(View v){
        final String username = mUsernameView.getText().toString();
        final String password = mPasswordView.getText().toString();

        if(UserStorage.getInstance().handleLoginRequest(username, password)){
            if(UserStorage.getInstance().findUserByName(username).isAdmin()) {
                final Intent intent = new Intent(this, AdminActivity.class);
                startActivity(intent);
                finish();
            } else if(UserStorage.getInstance().findUserByName(username).isBanned()){
                final Context context = getApplicationContext();
                final int duration = Toast.LENGTH_SHORT;
                final Toast t = Toast.makeText(context, (getString(R.string.isBanned, username)), duration);
                t.show();
            } else {
                final Intent intent = new Intent(this, HomepageActivity.class);
                startActivity(intent);
                finish();
            }
        } else {
            final Context context = getApplicationContext();
            final  int duration = Toast.LENGTH_SHORT;
            final  Toast t = Toast.makeText(context, (getString(R.string.incorrectInfo)), duration);
            t.show();
        }
        currentLoggedInUser = username;
    }

    public void forgotPassword(View v) {
        final Intent intent = new Intent(this, ForgetPasswordActivity.class);
        startActivity(intent);
        finish();
    }
}
