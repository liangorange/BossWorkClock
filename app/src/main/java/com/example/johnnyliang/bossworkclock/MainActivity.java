package com.example.johnnyliang.bossworkclock;

import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

// import com.parse.ParseException;


/**
 * Main Activity where everything is tied together
 *
 * An employee object is created and its variables are set from various methods.
 * This activity is used to tie all other classes in the app together.
 */
public class MainActivity extends ActionBarActivity {
    public final static String TAG2 = "MAIN_ACTIVITY";
    private Employee employee;
    private String dateFormat;
    private Date startingDate;
    private TimeCount count = new TimeCount();
    private SharedPreferences setting;
    public static final String fileName = "nameFile";

    // For the options menu
    private boolean twelveHourFormat = true;
    //private String curProject;

    // For the name
    private TextView name;
    private String theName = "";
    boolean goodName = false;


    //For project
    private TextView project;
    private TextView projectName;
    String currentProject;

    // For time table
    private int countNumber;                    // For counting Clock in time
    private TextView todayHour;
    private TextView weekHour;
    private TextView monthHour;
    private String todaysDate;
    private int todaysWeek;
    private int todaysMonth;
    private double dayHours;
    private float weekHours;
    private float monthHours;
    private boolean alreadyPunchedIn = false;
    public String totalHourFormat;
    private int dateTest;
    private ParseObject timeTrack;              // For Parse object to store data online
    private String hourFormat;
    private double totalHour;

    //For editPunchIn and editPunchOut
    private String outTime;
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

        dateTest = 0;

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "ovwOZZiEF5hVNnxP2W9UpZtcsPPm4rZJdmelkF3q", "LqEZAeaK4sVkTHOymW7MPKaxG2P3zLxkpqgFVGP4");

        //use the employee class
        employee = new Employee();

        // Initialize SharePreferences
        setting = getSharedPreferences(fileName, 0);

        setUpName();
        setUpProject();
        setUpTable();

        Log.v(TAG2, "Finishing onCreate");
    }

    /**
     *  This method is called to set up the name during the onCreate.
     */
    void setUpName() {
        name = (TextView) findViewById(R.id.name);
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
    }

    /**
     *  This method is called to set up the project/services display during the onCreate.
     */
    void setUpProject() {
        projectName = (TextView) findViewById(R.id.projectName);
        project = (TextView) findViewById(R.id.projectLabel);
        currentProject = setting.getString("ProjectName", "");
        String s1 = setting.getString("Project", "");
        if (currentProject.equals("")) {
            project.setText("");
        }
        else {
            project.setText(s1);
        }
        projectName.setText("                  " + currentProject);

        // Set the reference for MainActivity and Employee for the TimeCount Class
        count.setActivity(this);
        count.setEmployeeActivity(employee);
    }

    /**
     *  This method sets up the table during the onCreate.
     *
     *  If there are times that have been saved they will be set here. This method basically
     *  sets up the app with all the employees previous hours.
     */
    void setUpTable() {
        todayHour = (TextView) findViewById(R.id.todaysHours);
        weekHour = (TextView) findViewById(R.id.thisWeeksHours);
        monthHour = (TextView) findViewById(R.id.thisMonthsHours);

        //gets current date
        Date dayDate = new Date();
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        dateFormat = df.format(dayDate);
        //gets day that we started keeping track of dayHours
        todaysDate = setting.getString("TodaysDate", "");

        // Is it still the same day that we started keeping track of with dayHours
        //if same day keep adding on to dayHours
        if (dateFormat.equals(todaysDate)) {
            dayHours = setting.getFloat("DayHours", 0);
            countNumber = setting.getInt("Count", 0);
        }
        //otherwise it is a new day so start back at 0
        else {
            dayHours = 0;
            countNumber = 0;
        }

        // Declare SharePreferences Editor
        SharedPreferences.Editor editor = setting.edit();

        // Gets current week
        Calendar c = Calendar.getInstance();
        c.setTime(dayDate);
        int week = c.get(Calendar.WEEK_OF_YEAR);
        todaysWeek = setting.getInt("TodaysWeek", 0);

        // Is it still the same week
        if (week == todaysWeek)
            weekHours = setting.getFloat("WeekHours", 0);
        else {
            weekHours = 0;
            editor.putFloat("LastWeek", weekHours);
        }

        // Gets current month
        int month = c.get(Calendar.MONTH);
        todaysMonth = setting.getInt("TodaysMonth" , 0);

        //Is it still the same month
        if(month == todaysMonth)
            monthHours = setting.getFloat("MonthHours", 0);
        else {
            monthHours = 0;
            editor.putFloat("LastMonth", monthHours);
        }

        // Is employee still punched in
        employee.setPunchedIn(setting.getBoolean("PunchedIn", false));

        setting.getBoolean("NewClock", false);

        // If employee is still clocked in go to clock in screen
        if (employee.getPunchedIn() == true) {
            // Get punch in time
            Date myDate = new Date(setting.getLong("PunchInTime", 0));
            employee.setClockInTime(myDate);

            // Get current time when user opens the app again
            Date currentDate = new Date();
            long timeDiff = currentDate.getTime() - setting.getLong("Milliseconds", 0);

            System.out.println("Starting millisecond: " + setting.getLong("Milliseconds", 0));
            System.out.println("Current millisecond: " + currentDate.getTime());
            System.out.println("TimesDiff: " + timeDiff);

            int timesNumber = (int)timeDiff / 1000;
            System.out.println("TimesNumber: " + timesNumber);

            dayHours = (float)timesNumber * 0.01 + setting.getFloat("LastHour", 0);
            weekHours = (float)0.01 * timesNumber + setting.getFloat("LastWeek", 0);
            monthHours = (float)0.01 * timesNumber + setting.getFloat("LastMonth", 0);

            alreadyPunchedIn = true;
            employee.setPunchedIn(false);//so that we can call punchIn method

            View v = null;
            this.punchIn(v);
        }

        employee.setDailyTotal((float)dayHours);
        employee.setWeeklyTotal(weekHours);
        employee.setMonthlyTotal(monthHours);

        //I don't know why the Today string needs more spaces to align correctly...
        todayHour.setText("Today:         " + String.format("%.2f", employee.getDailyTotal()));
        weekHour.setText ("This Week:   " + String.format("%.2f", employee.getWeeklyTotal()));
        monthHour.setText("This Month:  " + String.format("%.2f", employee.getMonthlyTotal()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * This is where we can add settings.
     *
     * Any setting that we want to add on the settings menu are made here.
     *
     * @param item
     * @return boolean
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
                trackService();
                return true;
            default:
                return false;
        }
    }

    /**
     * Sets the name
     *
     * This method gets and sets the employees name the first time the
     * app is used.
     */
    public void setName() {
        Log.i(TAG2, "setName started");

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Employee Name");
        alert.setMessage("Enter your name");

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);

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
    }

    /**
     *  This method allow the user to track their services/projects.
     *
     *  When clicked an alert will pop up and propt the user to enter in the name of their current
     *  project. This name will then be saved and displayed on the screen untill a new project is
     *  assigned.
     */
    void trackService () {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Current Project");
        alert.setMessage("Enter your current project");

        final EditText input = new EditText(this);
        alert.setView(input);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                // Set curProject here and display it to the screen
                String s1 = "Project: ";
                currentProject = input.getText().toString();
                project = (TextView) findViewById(R.id.projectLabel);
                projectName = (TextView) findViewById(R.id.projectName);

                if (currentProject.equals("") ) {
                    project.setText("");
                }
                else {
                    project.setText(s1);
                }
                projectName.setText("                  " + currentProject);

                SharedPreferences.Editor editor = setting.edit();
                editor.putString("Project", s1);
                editor.putString("ProjectName", currentProject);

                editor.apply();
                Toast.makeText(MainActivity.this, "Project set", Toast.LENGTH_SHORT).show();
            }
         });

         alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int whichButton) {
         //Canceled.
          }
        });
        alert.show();
    }

    /**
     *  Who made this???
     * @param timeSecond
     */
    public void addTime(double timeSecond) {

        DateFormat dfMonth = new SimpleDateFormat("MM");

        // Format the currnet month
        String monthTest = dfMonth.format(startingDate);

        dayHours += timeSecond;
        weekHours += timeSecond;
        monthHours += timeSecond;

        totalHourFormat = String.format("%.2f", dayHours);
        System.out.println("Total Hour: " + totalHourFormat);

        int parseDateTest = Integer.parseInt(getDateString());

        ParseQuery<ParseObject> query = ParseQuery.getQuery("BossTimeTracker");

        query.whereEqualTo("aName", theName);
        query.whereEqualTo("dDate", parseDateTest);
        query.whereEqualTo("cMonth", monthTest);

        query.getFirstInBackground(new GetCallback<ParseObject>() {

            MainActivity activity;

            @Override
            public void done(ParseObject parseObject, com.parse.ParseException e) {
                if (parseObject == null) {
                    Log.d("score", "The getFirst request failed");
                } else {
                    String objectID = parseObject.getObjectId().toString();

                    ParseQuery<ParseObject> inQuery = ParseQuery.getQuery("BossTimeTracker");

                    inQuery.getInBackground(objectID, new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject parseObject, com.parse.ParseException e) {
                            if (e == null) {
                                parseObject.put("eTotalHour", totalHourFormat);
                                parseObject.saveInBackground();
                            }
                        }
                    });
                }
            }
        });
    }


    public String getDateString() {
        DateFormat df = new SimpleDateFormat("dd");

        dateFormat = df.format(startingDate);

        return dateFormat;
    }


    /**
     * This function response to the Edit Punch In button, and call the showDialog() functions
     * @param view
     */
    public void editPunchIn(View view) {
        if(!employee.getPunchedIn()) {

            showDialog(TIME_DIALOG_ID_IN);
            //showDialog(DATE_DIALOG_ID_IN);

            alreadyPunchedIn = true;
            View v = null;
            this.punchIn(v);
        }
    }

    /**
     * editPunchOut() is still under construction, right now it acts like punchOut()
     * @param view
     */
    public void editPunchOut(View view) {
        if(employee.getPunchedIn()) {

            showDialog(TIME_DIALOG_ID_OUT);
           // showDialog(DATE_DIALOG_ID_OUT);

            // Ideally this wouldn't happen untill after the above thread is complete
            View v = null;
            this.punchOut(v);
        }
    }

    /**
     * setCurrentTime() should reset the time variables for the timePicker dialogs
     */
    public void setCurrentTime(){
        Calendar c = Calendar.getInstance();
        inHour = c.get(Calendar.HOUR_OF_DAY);
        outHour = c.get(Calendar.HOUR_OF_DAY);
        inMinute = c.get(Calendar.MINUTE);
        outMinute = c.get(Calendar.MINUTE);
        inSecond = c.get(Calendar.SECOND);
        outSecond = c.get(Calendar.SECOND);
    }

    /**
     * setCurrentDate() should reset the time variables for datePicker dialogs
     */
    public void setCurrentDate() {

        Calendar c = Calendar.getInstance();
        inYear = c.get(Calendar.YEAR);
        inMonth = c.get(Calendar.MONTH);
        inDay = c.get(Calendar.DAY_OF_MONTH);
        outYear = c.get(Calendar.YEAR);
        outMonth = c.get(Calendar.MONTH);
        outDay = c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * This function takes the dialog id (999-996) and switch between dialogs to be showed and executed.
     * @param id
     * @return void
     */
    @Override
    protected Dialog onCreateDialog(int id) {
        // set picker as current date and time
        setCurrentDate();
        setCurrentTime();
        switch (id) {
           /* case DATE_DIALOG_ID_IN: {
                return new DatePickerDialog(this, datePickerListenerIn,
                        inYear, inMonth, inDay);
            }*/
            case TIME_DIALOG_ID_IN: {
                return new TimePickerDialog(this, timePickerListenerIn,
                        inHour, inMinute, false);
            }
           /* case DATE_DIALOG_ID_OUT: {
                return new DatePickerDialog(this, datePickerListenerOut,
                        outYear, outMonth, outDay);
            }*/
            case TIME_DIALOG_ID_OUT: {
                return new TimePickerDialog(this, timePickerListenerOut,
                        outHour, outMinute, false);
            }
        }
        return null;
    }

    /**
     * This function takes the values the user picks from the dialog, and assign them to local time variables.
     * Specifically this is the time part for Punch In. It will then execute the punch in process.
     */
    private TimePickerDialog.OnTimeSetListener timePickerListenerIn
            = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
            if (selectedHour < 0) {
                Log.e(TAG2, "The selectedHour is not right!!!");
            } else {
                inHour = selectedHour;
            }
            inMinute = selectedMinute;

            System.out.println("employee millisecond12312123121121211112121212132121232123132123132123123132: " + employee.getClockInTime().getTime());

           // Date oldClockInTime = employee.getClockInTime();


            //convert inHour and inMinute to date object
            Calendar cal = Calendar.getInstance();
            cal.setTime(employee.getClockInTime());
            cal.set(Calendar.HOUR_OF_DAY,inHour);
            cal.set(Calendar.MINUTE,inMinute);

            // Sets new startingDate
            Date newStartingDate = cal.getTime();
            employee.setClockInTime(newStartingDate);


            // Updates total hours.
            Date currentDate = new Date();
            long timeDiff = currentDate.getTime() - employee.getClockInTime().getTime();

            //System.out.println("currentTime millisecond: " + currentDate.getTime());
            //System.out.println("employee millisecond: " + employee.getClockInTime().getTime());
            //System.out.println("oldclockin millisecond: " + oldClockInTime.getTime());
            //System.out.println("TimesDiff: " + timeDiff);

            int timesNumber = (int)timeDiff / 36000;
            //System.out.println("TimesNumber: " + timesNumber);

            timesNumber *= .01;

            employee.incDailyTotal(timesNumber);
            employee.incWeeklyTotal(timesNumber);
            employee.incMonthlyTotal(timesNumber);

            Toast.makeText(MainActivity.this, "Edited Punched In time", Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * This function takes the values the user picks from the dialog, and assign them to local time variables.
     * Specifically this is the time part for Punch Out. Process will be added soon.
     */
    private TimePickerDialog.OnTimeSetListener timePickerListenerOut
            = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int selectedHour,
                              int selectedMinute) {
            outHour = selectedHour;
            outMinute = selectedMinute;

            // set current time into textview
            outTime = (pad(outHour)) + (":") + (pad(outMinute)) + (":") + (pad(outSecond));

            //recalculate hours worked today, this week, and this month....Idk if we need that stuff above.

            Toast.makeText(MainActivity.this, "Edited Punched Out time", Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * This function takes the values the user picks from the dialog, and assign them to local time variables.
     * Specifically this is the date part for Punch In. The Date part will be selected before the time part,
     * so the main processes can be found in the later time part.
     */
    /*private DatePickerDialog.OnDateSetListener datePickerListenerIn
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
    };*/

    /**
     * This function takes the values the user picks from the dialog, and assign them to local time variables.
     * Specifically this is the date part for Punch Out. The Date part will be selected before the time part,
     * so the main processes can be found in the later time part.
     */
  /*  private DatePickerDialog.OnDateSetListener datePickerListenerOut
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
    };*/

    /**
     * pad() convert integers like date, minutes, or seconds which is below 10 to 2 digit,
     * like 2 -> 02
     * @param c
     * @return (2 digit number in String)
     */
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
     *
     * This function will display the total hour format on the text view
     * It will update every 36 seconds
     */
    public void displayStartTotal() {
        startHandler.sendEmptyMessage(0);
    }

    /**
     * Johnny
     *
     * do work function will get the current time, and calculate the time difference
     * between current time and the time when user clocked in.
     * Format the time difference, and convert to hours
     */
    public void doWork() {
        Log.i(TAG2, "doWork started");
        runOnUiThread(new Runnable() {
            public void run() {
                try {

                    TextView statusView = (TextView) findViewById(R.id.status);
                    Date dt = new Date();
                    Date inDate;
                    inDate = employee.getClockInTime();

                    long diff = dt.getTime() - inDate.getTime();//Time since punch in

                    long seconds = diff / 1000 % 60;
                    long minutes = diff / (60 * 1000) % 60;
                    long hours = diff / (60 * 60 * 1000) % 60;

                    String secondFormat = String.format("%02d", seconds);
                    String minuteFormat = String.format("%02d", minutes);
                    String hourFormat = String.format("%02d", hours);

                    String curTime = hourFormat + ":" + minuteFormat;// + ":" + secondFormat;

                  //  Toast.makeText(MainActivity.this, "in do work", Toast.LENGTH_SHORT).show();
                    String status = "                Punched In";
                    String s = "Work start time: " + getTimeString();// employee.getClockInTime(); change to see actual time after app closed
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

                    // Set SharePreferences variable to true when employee clocked in
                    editor.putBoolean("PunchedIn", employee.getPunchedIn());

                    //keeps track of which day it is
                    Date dayDate = new Date();
                    DateFormat dt2 = new SimpleDateFormat("MM/dd/yyyy");
                    dateFormat = dt2.format(dayDate);
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

                    //keeps track of clockInTime
                    long inTime = employee.getClockInTime().getTime();
                    editor.putLong("PunchInTime",inTime);

                    editor.apply();

                } catch (Exception e) {
                    Log.e(TAG2, "the doWork thread is not working", e);
                }
            }
        });
    }

    /**
     * Johnny
     * This function help to format the time correctly for display.
     */
    public String getTimeString() {
        if (twelveHourFormat) {
            DateFormat df = new SimpleDateFormat("hh:mm a");
            dateFormat = df.format(employee.getClockInTime());
        } else {
            DateFormat df = new SimpleDateFormat("HH:mm");
            dateFormat = df.format(employee.getClockInTime());
        }
        return dateFormat;
    }


    /**
     * Method called when Punch In button is pressed.
     *
     * This method is connected to the Punch In button. It sets the employees punchedIn status
     * to true and starts the loadThread which calls the TimeCount run function.
     *
     * @param view
     */
    public void punchIn(View view){
        // Can't punch in if you already are punched in
        if(!employee.getPunchedIn()) {
            employee.setPunchedIn(true);

            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            DateFormat dfYear = new SimpleDateFormat("yyyy");
            DateFormat dfMonth = new SimpleDateFormat("MM");
            startingDate = new Date();

            // Format the current year
            String yearTest = dfYear.format(startingDate);

            // Format the currnet month
            String monthTest = dfMonth.format(startingDate);

            dateTest = Integer.parseInt(getDateString());

            if (todaysDate == "" || todaysDate != df.format(startingDate)) {
                timeTrack = new ParseObject("BossTimeTracker");
            }

            // Shared preference editor
            SharedPreferences.Editor editor = setting.edit();

            if (!setting.getBoolean("NewClock", false)) {

                // Store starting time millisecond
                editor.putLong("Milliseconds", startingDate.getTime());
                editor.putBoolean("NewClock", true);
            }

            todaysDate = df.format(startingDate);
            editor.putString("TodaysDate", todaysDate);

            hourFormat = String.format("%.2f", totalHour);

            // Create columns for Parse BossTimeTracker table
            if (countNumber == 0) {
                timeTrack.put("aName", theName);
                timeTrack.put("bYear", yearTest);
                timeTrack.put("cMonth", monthTest);
                timeTrack.put("dDate", dateTest);
                timeTrack.put("eProject", currentProject);
                timeTrack.put("fDailyHour", "0");
                timeTrack.put("gWeeklyHour", "0");
                timeTrack.put("hMonthlyHour", "0");
                timeTrack.saveInBackground();
            }

            countNumber++;
            editor.putInt("Count", countNumber);
            editor.commit();


            // Gets current time if you weren't already clocked in before
            if (!alreadyPunchedIn) {
                startingDate = new Date();
                employee.setClockInTime(startingDate);
            }

            Thread loadThread = new Thread(count);
            loadThread.start();

            //GPSCoord inLocation = new GPSCoord();
            //employee.setClockInLocation(inLocation);
        }
    }

    /**
     * Method called when Punch Out button is pressed.
     *
     * This method is connected to the Punch Out button. It sets the employees punchedIn status
     * to false and sets the textviews for punched out status.
     *
     * @param view
     */
    public void punchOut(View view){
        // Can't punch out if you already are punched out
        if(employee.getPunchedIn()) {
            employee.setPunchedIn(false);
            alreadyPunchedIn = false;

            String status = "                Punched Out";
            TextView textView = (TextView) findViewById(R.id.status);
            textView.setTextColor(0xffff1410);
            textView.setText(status);

            // Gets current time
            Date endingDate = new Date();

            employee.setClockOutTime(endingDate);

            SharedPreferences.Editor editor = setting.edit();
            editor.putBoolean("PunchedIn", employee.getPunchedIn());

            editor.putFloat("LastHour", (float) dayHours);
            editor.putFloat("LastWeek", weekHours);
            editor.putFloat("LastMonth", monthHours);

            editor.putBoolean("NewClock", false);

            editor.apply();

            //GPSCoord outLocation = new GPSCoord();
            //employee.setClockInLocation(outLocation);
        }
    }
}
