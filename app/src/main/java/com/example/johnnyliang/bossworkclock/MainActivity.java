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
    private String inTime;
    private String outTime;
    private String inDate;
    private String outDate;
    private String resultIn;
    private String resultOut;
    private Button addInTime;
    private Button addOutTime;
    private int inYear;
    private int inMonth;
    private int inDay;
    private int inHour;
    private int inMinute;
    private int inSecond = 0;
    private int outYear;
    private int outMonth;
    private int outDay;
    private int outHour;
    private int outMinute;
    private int outSecond = 0;
    static final int DATE_DIALOG_ID_IN = 999;
    static final int TIME_DIALOG_ID_IN = 998;
    static final int DATE_DIALOG_ID_OUT = 997;
    static final int TIME_DIALOG_ID_OUT = 996;


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
                employee.setName(theName);
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
     * Kwok
     * @param view
     */
    public void editPunchIn(View view) {
        Toast.makeText(MainActivity.this, "Edited a Punched In", Toast.LENGTH_SHORT).show();
    }

    /**
     * Kwok
     * @param view
     */
    public void editPunchOut(View view) {
        Toast.makeText(MainActivity.this,"Edited a Punched Out", Toast.LENGTH_SHORT).show();
    }

    public void setCurrentTime(){
        final Calendar c = Calendar.getInstance();
        inHour = c.get(Calendar.HOUR_OF_DAY);
        outHour = c.get(Calendar.HOUR_OF_DAY);
        inMinute = c.get(Calendar.MINUTE);
        outMinute = c.get(Calendar.MINUTE);
        inSecond = c.get(Calendar.SECOND);
        outSecond = c.get(Calendar.SECOND);
    }
    public void setCurrentDate() {

        final Calendar c = Calendar.getInstance();
        inYear = c.get(Calendar.YEAR);
        inMonth = c.get(Calendar.MONTH);
        inDay = c.get(Calendar.DAY_OF_MONTH);
        outYear = c.get(Calendar.YEAR);
        outMonth = c.get(Calendar.MONTH);
        outDay = c.get(Calendar.DAY_OF_MONTH);
    }

    public void addListenerOnButton(){
        addInTime = (Button)findViewById(R.id.button);
        addInTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID_IN);
                showDialog(DATE_DIALOG_ID_IN);
            }
        });

        addOutTime = (Button)findViewById(R.id.button2);
        addOutTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID_OUT);
                showDialog(DATE_DIALOG_ID_OUT);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID_IN:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListenerIn,
                        inYear, inMonth,inDay);
            case TIME_DIALOG_ID_IN:
                return new TimePickerDialog(this, timePickerListenerIn,
                        inHour, inMinute, false);
            case DATE_DIALOG_ID_OUT:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListenerOut,
                        outYear, outMonth,outDay);
            case TIME_DIALOG_ID_OUT:
                return new TimePickerDialog(this, timePickerListenerOut,
                        outHour, outMinute, false);
        }

        return null;
    }

    private TimePickerDialog.OnTimeSetListener timePickerListenerIn
            = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int selectedHour,
                              int selectedMinute) {
            inHour = selectedHour;
            inMinute = selectedMinute;

            // set current time into textview
            inTime = (pad(inHour)) + (":") + (pad(inMinute)) + (":") + (pad(inSecond));
        }
    };

    private TimePickerDialog.OnTimeSetListener timePickerListenerOut
            = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int selectedHour,
                              int selectedMinute) {
            outHour = selectedHour;
            outMinute = selectedMinute;

            // set current time into textview
            outTime = (pad(outHour)) + (":") + (pad(outMinute)) + (":") + (pad(outSecond));
        }
    };

    private DatePickerDialog.OnDateSetListener datePickerListenerIn
            = new DatePickerDialog.OnDateSetListener() {
        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            inYear = selectedYear;
            inMonth = selectedMonth;
            inDay = selectedDay;

            // set selected date into textview
            inDate = (inMonth + 1) +  ("-") + (inDay) + ("-") + (inYear) + (" ");
        }
    };

    private DatePickerDialog.OnDateSetListener datePickerListenerOut
            = new DatePickerDialog.OnDateSetListener() {
        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            outYear = selectedYear;
            outMonth = selectedMonth;
            outDay = selectedDay;

            // set selected date into textview
            outDate = (outMonth + 1) +  ("-") + (outDay) + ("-") + (outYear) + (" ");
        }
    };

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
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
