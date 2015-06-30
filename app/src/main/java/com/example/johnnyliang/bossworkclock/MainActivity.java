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
import android.util.Log;
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


//1 hrs
public class MainActivity extends ActionBarActivity {
    public final static String TAG2 = "MAIN_ACTIVITY";
    private Employee employee;
    private String dateFormat;
    private Date startingDate;
    private TimeCount count = new TimeCount();
    private boolean twelveHourFormat = false;

    // For the name
    private TextView name;
    private String theName = "";
    boolean goodName = false;
    private SharedPreferences setting;
    public static final String fileName = "nameFile";

    // For time table
    private TextView todayHour;
    private TextView weekHour;
    private TextView monthHour;
    private String todaysDate;
    private int todaysWeek;
    private int todaysMonth;
    private float dayHours;
    private float weekHours;
    private float monthHours;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG2, "Starting onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //use the employee class
        employee = new Employee();

        //for name**********************************************
        name = (TextView) findViewById(R.id.name);
        setting = getSharedPreferences(fileName, 0);
        theName = setting.getString("Name" , "");
        name.setText(theName);
        employee.setName(theName);
       // int nameCount = 0;
        //do {
       if(theName.equals("")) {
        //    nameCount++;
            System.out.println("here");
            setName();
            System.out.println("here2");
        }
       // }while(nameCount < 3 || theName.equals(""));  //it says this is always true but it should depend on user input...?

        count.setActivity(this);
        count.setEmployeeActivity(employee);
        //end of name stuff****************************************


        //Start of setting time table--------------------------------------------------------
        todayHour = (TextView) findViewById(R.id.todaysHours);
        weekHour = (TextView) findViewById(R.id.thisWeeksHours);
        monthHour = (TextView) findViewById(R.id.thisMonthsHours);

        //gets current date
        Date dayDate = new Date();
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        dateFormat = df.format(dayDate);
        //gets day that we started keeping track of dayHours
        todaysDate = setting.getString("TodaysDate", "");

       // System.out.println("date formate: " + dateFormat + " todays date"  + todaysDate );
       // is it still the same day that we started keeping track of dayHours
        if (dateFormat.equals(todaysDate))//if same day keep adding on to dayHours
            dayHours = setting.getFloat("DayHours", 0);
        else//otherwise it is a new day so start back at 0
            dayHours = 0;

        //gets current week
        Calendar c = Calendar.getInstance();
        c.setTime(dayDate);
        int week = c.get(Calendar.WEEK_OF_YEAR);
        todaysWeek = setting.getInt("TodaysWeek", 0);

        if (week == todaysWeek)
            weekHours = setting.getFloat("WeekHours", 0);
        else
            weekHours = 0;

        //gets current month
        int month = c.get(Calendar.MONTH);
        todaysMonth = setting.getInt("TodaysMonth" , 0);

        if(month == todaysMonth)
            monthHours = setting.getFloat("MonthHours", 0);
        else
            monthHours = 0;

        employee.setDailyTotal(dayHours);
        employee.setWeeklyTotal(weekHours);
        employee.setMonthlyTotal(monthHours);

        //I don't know why the Today string needs more spaces to align correctly
        todayHour.setText("Today:         " + String.format("%.2f", employee.getDailyTotal()));
        weekHour.setText ("This Week:   " + String.format("%.2f", employee.getWeeklyTotal()));
        monthHour.setText("This Month:  " + String.format("%.2f", employee.getMonthlyTotal()));
        //End of setting time table--------------------------------------------------------------

        Log.v(TAG2, "Finishing onCreate");
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
            case R.id.TwelveHour:
                if(item.isChecked()){
                    item.setChecked(false);
                    twelveHourFormat = false;
                }else{
                    item.setChecked(true);
                    twelveHourFormat = true;
                }
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
        Log.i(TAG2, "setName started");
        //Makes sure a name is entered
        //final boolean goodName = false;
        //do {
        //final boolean goodName = false;
        int i = 0;
       // do {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            alert.setTitle("Employee Name");
            alert.setMessage("Enter your name");
       // do {
            // Set an EditText view to get user input
            final EditText input = new EditText(this);
            alert.setView(input);

                i++;
           // if (alert.isShowing())
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Log.i(TAG2, "Start processing OK button");

                        theName = input.getText().toString();
                        if (theName.equals("")) {
                            Log.i(TAG2, "name = \"\" ");
                            name.setText("I don't have a name");
                            Toast.makeText(MainActivity.this, "Must enter a name", Toast.LENGTH_SHORT).show();
                        } else {
                            goodName = true;
                            name.setText(theName);
                            Log.i(TAG2, " name = " + theName);
                        }
                            SharedPreferences.Editor editor = setting.edit();
                            editor.putString("Name", theName);

                            // Commit edit
                            editor.apply();
                            employee.setName(theName);

                        Log.i(TAG2, "End of Ok button");
                    }

                });


            // alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            //  public void onClick(DialogInterface dialog, int whichButton) {
            // Canceled.
            //  }
            //});
            alert.show();
       // }while (!theName.equals(""));
        System.out.println("here9");
        //}while(theName.equals(""));
    }



    /**
     * Kwok
     * @param view
     */
    public void editPunchIn(View view) {
        if(!employee.getPunchedIn()) {
            employee.setPunchedIn(true);
            Toast.makeText(MainActivity.this, "Editing a Punched In", Toast.LENGTH_SHORT).show();
            setCurrentTime();
            setCurrentDate();
            showDialog(TIME_DIALOG_ID_IN);
            showDialog(DATE_DIALOG_ID_IN);

            //below are to be fixed
            System.out.println(inYear + ':' + inMonth + ':' + inDay + ':' + inHour + ':' + inMinute + ':' + inSecond);
            startingDate = new Date(inYear, inMonth, inDay, inHour, inMinute, inSecond);
            employee.setEditInDate(startingDate);
            Thread loadThread = new Thread(count);
            loadThread.start();

            employee.getClockInLocation();
            employee.editClockIn();
        }
    }

    /**
     * Kwok
     * @param view
     */
    public void editPunchOut(View view) {
        if(employee.getPunchedIn()) {
            employee.setPunchedIn(false);
            Toast.makeText(MainActivity.this, "Editing a Punched Out", Toast.LENGTH_SHORT).show();
            setCurrentTime();
            setCurrentDate();
            showDialog(TIME_DIALOG_ID_OUT);
            showDialog(DATE_DIALOG_ID_OUT);

            //below are to be fixed
            String status = "                Punched Out";
            TextView textView = (TextView) findViewById(R.id.status);
            textView.setTextColor(0xffff1410);
            textView.setText(status);

            todayHour.setText("Today:       " + String.format("%.2f", employee.getDailyTotal()));
            weekHour.setText("This Week:   " + String.format("%.2f", employee.getWeeklyTotal()));
            monthHour.setText("This Month:  " + String.format("%.2f", employee.getMonthlyTotal()));

            employee.getClockOutLocation();
        }
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
        public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
            if (selectedHour < 0) {
                Log.e(TAG2, "The selectedHour is not right!!!");
            } else {
                inHour = selectedHour;
            }
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



    Handler startHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            getTimeString();
            //displayStart();
            System.out.println("Call handler");
        }
    };

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
        Log.i(TAG2, "doWork started");
        runOnUiThread(new Runnable() {
            public void run() {
                try {
                    TextView statusView = (TextView) findViewById(R.id.status);
                    Date dt = new Date();
                    long diff = dt.getTime() - startingDate.getTime();

                    long seconds = diff / 1000 % 60;
                    long minutes = diff / (60 * 1000) % 60;
                    long hours = diff / (60 * 60 * 1000) % 60;

                   // String secondFormat = String.format("%02d", seconds);
                    String minuteFormat = String.format("%02d", minutes);
                    String hourFormat = String.format("%02d", hours);

                    String curTime = hourFormat + ":" + minuteFormat;// + ":" + secondFormat;

                    String status = "                Punched In";
                    String s = "Work start time: " + getTimeString();
                    String s2 = "Time since punch in: " + curTime;

                    String s3 = status + "\n" + s + "\n" + s2;
                    statusView.setTextColor(0xff18ff1a);
                    statusView.setText(s3);

                    //I don't know why the Today string needs more spaces to align correctly
                    todayHour.setText("Today:         " + String.format("%.2f", employee.getDailyTotal()));
                    weekHour.setText("This Week:   " + String.format("%.2f", employee.getWeeklyTotal()));
                    monthHour.setText("This Month:  " + String.format("%.2f", employee.getMonthlyTotal()));

                    //Save the stuff
                    SharedPreferences.Editor editor = setting.edit();
                    editor.putFloat("DayHours", employee.getDailyTotal());
                    editor.putFloat("WeekHours", employee.getWeeklyTotal());
                    editor.putFloat("MonthHours", employee.getMonthlyTotal());

                    //keeps track of which day it is
                    Date dayDate = new Date();
                    DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                    dateFormat = df.format(dayDate);
                    editor.putString("TodaysDate", dateFormat);
                   //System.out.println("date formate22222: " + dateFormat );

                    //keeps track of which week it is
                    Calendar c = Calendar.getInstance();
                    c.setTime(dayDate);
                    int week = c.get(Calendar.WEEK_OF_YEAR);
                    editor.putInt("TodaysWeek", week);

                    //keeps track of which month it is
                    int month = c.get(Calendar.MONTH);
                    editor.putInt("TodaysMonth", month);

                    editor.apply();

                } catch (Exception e) {
                    Log.e(TAG2, "the doWork thread is not working", e);
                }
            }
        });
    }

    /**
     * Johnny
     */
    public String getTimeString() {
        if (twelveHourFormat) {
            DateFormat df = new SimpleDateFormat("K:mm");
            dateFormat = df.format(startingDate);
        } else {
            DateFormat df = new SimpleDateFormat("HH:mm");
            dateFormat = df.format(startingDate);
        }
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

           // employee.setClockInLocation();
            employee.getClockInLocation();
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


            employee.getClockOutLocation();
        }
    }
}
