package com.example.johnnyliang.bossworkclock;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


//10hrs
public class MainActivity extends ActionBarActivity {
    private Employee employee;
    private String dateFormat;
    private Date startingDate;
    private TimeCount count = new TimeCount();

    // For the name
    private TextView name;
    private String theName;
    private SharedPreferences setting;
    public static final String fileName = "nameFile";

    // TextViews
    TextView todayHour;
    TextView weekHour;
    TextView monthHour;

    //Jonathan's time/datepicker parts
    private String time;
    private String date;
    private String resultIn;
    private String resultOut;
    private Button addInTime;
    private Button addOutTime;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second = 0;
    static final int DATE_DIALOG_ID = 999;
    static final int TIME_DIALOG_ID = 998;


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

        setCurrentTime();
        setCurrentDate();
        addListenerOnButton();

        //use the employee class
        employee = new Employee();

        //for name
        name = (TextView) findViewById(R.id.name);
        setting = getSharedPreferences(fileName, 0);
        theName = setting.getString("Name" , "");
        name.setText(theName);
        employee.setName(theName);
        if(theName.equals("")) {
            setName();
        }

        count.setActivity(this);
        count.setEmployeeActivity(employee);

        todayHour = (TextView) findViewById(R.id.todaysHours);
        weekHour = (TextView) findViewById(R.id.thisWeeksHours);
        monthHour = (TextView) findViewById(R.id.thisMonthsHours);

    }

    public void setCurrentTime(){
        final Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
    }
    public void setCurrentDate() {

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
    }


    public void addListenerOnButton(){
        addInTime = (Button)findViewById(R.id.button);
        addInTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);
                showDialog(DATE_DIALOG_ID);
            }
        });

        addOutTime = (Button)findViewById(R.id.button2);
        addOutTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);
                showDialog(DATE_DIALOG_ID);
            }
        });
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener,
                        year, month,day);
            case TIME_DIALOG_ID:
                return new TimePickerDialog(this, timePickerListener,
                        hour, minute, false);
        }

        return null;
    }

    private TimePickerDialog.OnTimeSetListener timePickerListener
            = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int selectedHour,
                              int selectedMinute) {
            hour = selectedHour;
            minute = selectedMinute;

            // set current time into textview
            time = (pad(hour)) + (":") + (pad(minute)) + (":") + (pad(second));
        }
    };

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {
        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // set selected date into textview
            date = (month + 1) +  ("-") + (day) + ("-") + (year) + (" ");
        }
    };

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
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

        switch (item.getItemId()) {
            case R.id.TwentyFourHour:
                //set bool 24HourTime to false
                return true;
            case R.id.trackServices:
                //trackService()
                return true;
            default:
                return false;
        }
    }

    /**
     * setName
     * This method gets and sets the employees name the first time the
     * app is used.
     */
    public void setName() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Employee Name");
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

    /* //im going to delete this next time....
    public String getDateString() {
        DateFormat df = new SimpleDateFormat("")
    }
    */

    /**
     * Kwok
     * @param view
     */
    public void editPunchIn(View view) {
        Toast.makeText(MainActivity.this, "Added a Punched In", Toast.LENGTH_SHORT).show();
    }

    /**
     * Kwok
     * @param view
     */
    public void editPunchOut(View view) {
        Toast.makeText(MainActivity.this,"Added a Punched Out", Toast.LENGTH_SHORT).show();
    }

    /**
     * punchIn
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

            employee.getClockInLocation();
            employee.clockIn();
        }
    }

    /**
     * puchOut
     *
     * @param view
     */
    public void punchOut(View view){
        // Can't punch out if you already are punched out
        if(employee.getPunchedIn()) {
            employee.setPunchedIn(false);

            String status = "                Punched Out";
            TextView textView = (TextView) findViewById(R.id.status);
            textView.setTextColor(0xffff1410);
            textView.setText(status);

            todayHour.setText("Today:       " + String.format("%.2f", employee.getTotalHour()));
            weekHour.setText("This Week:   " + String.format("%.2f", employee.getTotalHour()));
            monthHour.setText("This Month:  " + String.format("%.2f", employee.getTotalHour()));

            employee.getClockOutLocation();
            employee.clockOut();
        }
    }
}
