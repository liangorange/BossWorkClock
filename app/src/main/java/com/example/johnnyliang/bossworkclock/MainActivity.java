package com.example.johnnyliang.bossworkclock;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;



//8hrs
public class MainActivity extends ActionBarActivity {
    private Employee employee;

    private String dateFormat;
    private Date startingDate;
    private TimeCount count = new TimeCount();

    private TextView name;
    private String theName;
    private SharedPreferences setting;
    public static final String fileName = "nameFile";

    Handler startHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            getTimeString();
            //displayStart();
            System.out.println("Call handler");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //use the employee class
        employee = new Employee();
        TextView textView = (TextView) findViewById(R.id.status);
        TextView statusView = (TextView) findViewById(R.id.status);

        //for name
        name = (TextView) findViewById(R.id.name);
        setting = getSharedPreferences(fileName, 0);
        theName = setting.getString("Name" , "Enter your name");
        name.setText(theName);
        employee.setName(theName);

        count.setActivity(this);
        count.setEmployeeActivity(employee);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * This is where we can add settings
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //for editing employee's name
        if (id == R.id.setName) {
            Toast.makeText(MainActivity.this,"WHoa!!!", Toast.LENGTH_SHORT).show();

            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            alert.setTitle("Edit Name");
            alert.setMessage("Enter your name");

            // Set an EditText view to get user input
            final EditText input = new EditText(this);
            alert.setView(input);

            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    theName = input.getText().toString();
                    name.setText(theName);

                    SharedPreferences.Editor editor = setting.edit();
                    editor.putString("Name", theName);

                    // Commit edit
                    editor.apply();
                }
            });

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // Canceled.
                }
            });

            alert.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Johnny
     */
    public void displayStartTotal() {
        startHandler.sendEmptyMessage(0);
    }

    /**
     * Johnny
     */
    public void doWork() {
        runOnUiThread(new Runnable() {
            public void run() {
                try {
                    System.out.println("For testing");
                    TextView statusView = (TextView) findViewById(R.id.status);
                    Date dt = new Date();
                    long diff = dt.getTime() - startingDate.getTime();
                    long seconds = diff / 1000 % 60;
                    long minutes = diff / (60 * 1000) % 60;
                    long hours = diff / (60 * 60 * 1000) % 60;

                    String secondFormat = String.format("%02d", seconds);
                    String minuteFormat = String.format("%02d", minutes);
                    String hourFormat = String.format("%02d", hours);

                    String curTime = hourFormat + ":" + minuteFormat + ":" + secondFormat;

                    String status = "                Punched In";
                    String s = "Work start time: " + getTimeString();
                    String s2 = "Time worked today: " + curTime;

                    String s3 = status + "\n" + s + "\n" + s2;
                    statusView.setTextColor(0xff18ff1a);
                    statusView.setText(s3);
                } catch (Exception e) {
                }
            }
        });
    }

    /**
     * Johnny
     */
    public String getTimeString() {
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        dateFormat = df.format(startingDate);
        return dateFormat;
    }

    public void editPunchIn(View view) {
        Toast.makeText(MainActivity.this,"Added a Punched In", Toast.LENGTH_SHORT).show();
    }

    public void editPunchOut(View view) {
        Toast.makeText(MainActivity.this,"Added a Punched Out", Toast.LENGTH_SHORT).show();
    }

    /**
     *
     * @param view
     */
    public void punchIn(View view){

        // Can't punch in if you already are punched in
        if(!employee.getPunchedIn()) {
            employee.setPunchedIn(true);

            startingDate = new Date();

            Thread loadThread = new Thread(count);
            loadThread.start();

            employee.getTimeTracker().getClockInLocation();
            //employee.getTimeTracker().clockIn();
        }
    }

    public void punchOut(View view){
        // Can't punch out if you already are punched out
        if(employee.getPunchedIn()) {
            employee.setPunchedIn(false);

            String status = "                Punched Out";
            TextView textView = (TextView) findViewById(R.id.status);
            textView.setTextColor(0xffff1410);
            textView.setText(status);

            employee.getTimeTracker().getClockOutLocation();
            //employee.getTimeTracker().clockOut();
        }
    }
}
