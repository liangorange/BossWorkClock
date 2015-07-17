package comWorkClock.WorkClock.johnnyliang.bossworkclock;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * This is where the employee enters the employer password.
 *
 * This is the first screen that shows up when the app is downloaded. The employee
 * can then enter in a password that was provided by their employer of enter the
 * default password to use the app without storing their data. When a new employers
 * password is added to the app it needs to be updated in this class as well as in the
 * main activity.
 * Created by liangorange on 7/14/15.
 */
public class WelcomeActivity extends Activity {

    public EditText passWord;
    private SharedPreferences setting;
    public static final String fileName = "nameFile";
    public final static String TAG3 = "WELCOME_ACTIVITY";
    private String employerCode;

    public static String enteredPassword;

    /**
     * This meathod sets up the class.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Makes it so that the employee doesn't have to enter in the company code more than once.
        // This is used together with the shared preferences in the MainActivity
        setting = getSharedPreferences(fileName, 0);
        employerCode = setting.getString("EmployerCode", "");
        Log.i(TAG3, "employerCode is : " + employerCode);

        // Checks if employee already entered in employer password
        if (employerCode.equals("")) {
            setContentView(R.layout.welcome_screen);
            // Log in button click handler
            Button loginButton = (Button) findViewById(R.id.button);

            passWord = (EditText) findViewById(R.id.password);

            loginButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Starts an intent of the log in activity

                    enteredPassword = passWord.getText().toString();

                    // Saves if you already entered in a password
                    setting = getSharedPreferences(fileName, 0);
                    SharedPreferences.Editor editor = setting.edit();
                    editor.putString("EmployerCode", enteredPassword);
                    editor.apply();

                    // *****WHEN YOU ADD AN NEW EMPLOYER, PUT THEIR PASSWORD HERE*****
                    if (enteredPassword.equals("0000") || enteredPassword.equals("Gerald") || enteredPassword.equals("Gerald2")) {

                        IntentLauncher launcher = new IntentLauncher();
                        launcher.start();
                    } else
                        Toast.makeText(getApplicationContext(), "Sorry, the company code does not exist =)",
                                Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            IntentLauncher launcher = new IntentLauncher();
            launcher.start();
        }
    }

    /**
     * Launches the app after the pasword is entered.
     */
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
