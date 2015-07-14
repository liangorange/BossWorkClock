package com.example.johnnyliang.bossworkclock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by liangorange on 7/14/15.
 */
public class WelcomeActivity extends Activity {

    // EditText passWord = (EditText)findViewById(R.id.password);
    public EditText passWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_screen);

        // Log in button click handler
        Button loginButton = (Button) findViewById(R.id.button);

        passWord = (EditText)findViewById(R.id.password);




        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Starts an intent of the log in activity

                if (passWord.getText().toString().equals("5678")) {
                    // startActivity(new Intent(WelcomeActivity.this, MyActivity.class));

                    IntentLauncher launcher = new IntentLauncher();
                    launcher.start();
                }
                else
                    Toast.makeText(getApplicationContext(), "Sorry, the company code does not exist =)",
                            Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class IntentLauncher extends Thread {
        @Override
        /**
         * Sleep for some time and than start new activity.
         */
        public void run() {
            // Start main activity
            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
            WelcomeActivity.this.startActivity(intent);
            WelcomeActivity.this.finish();
        }
    }
}
