package team56.mrurt.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Button;

import android.content.Context;
import android.widget.Toast;
import android.view.View;

import team56.mrurt.R;
import team56.mrurt.model.Database.DatabaseOperations;
import team56.mrurt.model.User;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import android.app.ProgressDialog;
import android.os.AsyncTask;

public class ForgetPasswordActivity extends AppCompatActivity {

    private EditText emailView, usernameView;
    private User user;

    private Context c = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password_activity);
        emailView = (EditText) findViewById(R.id.email);
        usernameView = (EditText) findViewById(R.id.username);
        Button sendButton = (Button) findViewById(R.id.submit);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();
            }
        });

    }

    public void sendEmail() {
        if(emailView.getText().toString().equals("") && !usernameView.getText().toString().equals("")) {
            user = DatabaseOperations.getHelper(c).getSingleUser(usernameView.getText().toString());
        } else if(!emailView.getText().toString().equals("") && usernameView.getText().toString().equals("")) {
            user = DatabaseOperations.getHelper(c).getSingleUserEmail(emailView.getText().toString());
        }
        if (emailView.getText().toString().equals("") && usernameView.getText().toString().equals("")) {
            final int duration = Toast.LENGTH_LONG;
            final Toast t = Toast.makeText(c, (getString(R.string.incorrectInfo)), duration);
            t.show();
        } else if(user.getUsername().equals("") || user.getEmail().equals("")){
            final int duration = Toast.LENGTH_LONG;
            final Toast t = Toast.makeText(c, (getString(R.string.incorrectInfo)), duration);
            t.show();
        } else {
            String email = user.getEmail();
            String subject = "Password Recovery";
            String message = "Username: " + user.getUsername() + "\nPassword: " + user.getPassword();
            Session session = createSessionObject();

            try {
                Message m = createMessage(email, subject, message, session);
                new SendMailTask().execute(m);
            } catch (AddressException e) {
                e.printStackTrace();
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

    }

    private Message createMessage(String email, String subject, String messageBody, Session session) throws MessagingException, UnsupportedEncodingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("kst7783@gmail.com", "Movie Recommender Admin"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email, email));
        message.setSubject(subject);
        message.setText(messageBody);
        return message;
    }

    private Session createSessionObject() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        return Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("kst7783@gmail.com", "lovetoyou1");
            }
        });
    }

    private class SendMailTask extends AsyncTask<Message, Void, Void> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(ForgetPasswordActivity.this, "Please wait", "Sending mail", true, false);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
        }

        @Override
        protected Void doInBackground(Message... messages) {
            try {
                Transport.send(messages[0]);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}


