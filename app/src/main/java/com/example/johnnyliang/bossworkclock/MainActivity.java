package com.example.johnnyliang.bossworkclock;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends ActionBarActivity {
    private Employee employee;

    private String dateFormat;

    private Date startingDate;

    private TimeCount count = new TimeCount();


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

        employee = new Employee();

        count.setActivity(this);
    }


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

    public String getTimeString() {
        Date date = new Date();

        DateFormat df = new SimpleDateFormat("HH:mm:ss");

        Calendar cal = Calendar.getInstance();

        dateFormat = df.format(startingDate);

        return dateFormat;
    }

    public void displayStartTotal() {
        startHandler.sendEmptyMessage(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

        startingDate = new Date();

        Thread loadThread = new Thread(count);
        loadThread.start();


        // Can't punch in if you already are punched in
        if(!employee.getPunchedIn()) {
            employee.setPunchedIn(true);
            employee.getTimeTracker().getClockInLocation();
            employee.getTimeTracker().clockIn();
            
            Toast.makeText(MainActivity.this,"Punched in", Toast.LENGTH_SHORT).show();

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

            Toast.makeText(MainActivity.this,"Punched out", Toast.LENGTH_SHORT).show();
            employee.getTimeTracker().getClockOutLocation();
            employee.getTimeTracker().clockOut();
        }
    }
}
