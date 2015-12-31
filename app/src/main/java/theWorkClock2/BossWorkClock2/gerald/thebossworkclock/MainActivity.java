package theWorkClock2.BossWorkClock2.gerald.thebossworkclock;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
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

/**
 * Main Activity where everything is tied together
 *
 * An employee object is created and its variables are set from various methods.
 * This activity is used to tie all other classes in the app together.
 */
public class MainActivity extends ActionBarActivity {
    public static final String EXTRA = "extra";
    public final static String TAG2 = "MAIN_ACTIVITY";
    private Employee employee;
    private String dateFormat;
    private Date startingDate;
    private TimeCount count = new TimeCount();
    private SharedPreferences setting;
    public static final String fileName = "nameFile";
    public boolean showDialogDone = false;

    // For the options menu
    private boolean twelveHourFormat = true;

    // For the name
    private TextView name;
    private String theName = "";
    boolean goodName = false;

    //For project
    private TextView project;
    private TextView projectName;
    String currentProject;

    // For time table
    private TextView todayHour;
    private TextView weekHour;
    private TextView monthHour;
    private boolean alreadyPunchedIn = false;

    public String totalHourFormat;
    public String totalWeekFormat;
    public String totalMonthFormat;

    // For Parse object to store data online
    private int numOfTimesPunchedInToday;
    //private int dateTest;
    private ParseObject parseTimeObject;
   /* private String hourFormat;
    private double totalHour;*/

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

    /**
     * This is the onCreate method for our MainActivity.
     *
     * Several startup functions are called during this method to set up the app correctly.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG2, "Starting onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //dateTest = 0;

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Initialize SharePreferences
        setting = getSharedPreferences(fileName, 0);

        // Makes it so that the employee doesn't have to enter in the company code more than once.
        // This is used together with the shared preferences in the WelcomeActivity
        String employerPass = setting.getString("EmployerCode", "");
        Log.i(TAG2, "employerPass is : " + employerPass);

        //**************************************************************************************
        // *******     WHEN YOU ADD AN NEW EMPLOYER, PUT THEIR PASSWORD HERE             *******
        // ******* ALSO INCLUDE THEIR PARSE KEYS AND SAVE PASSWORD IN SHARED PREFERENCES *******
        //Checks if employee already entered in employer password
        if (employerPass.equals("") ){
            SharedPreferences.Editor editor = setting.edit();
            if (WelcomeActivity.enteredPassword.equals("Gerald")) {
                editor.putString("EmployerCode", "Gerald");
                System.out.println("Goes to Gerald's parse account");
                Parse.initialize(this, "YuTd2MEdXK9M3hnxDPMXr2o4UAN2P3P1UoAeRVcV", "9TNC9THjdIrh0V1s2WCOY1VrqzqzKunWJlczrs46");
            }
            if (WelcomeActivity.enteredPassword.equals("Gerald2")) {
                editor.putString("EmployerCode", "Gerald2");
                System.out.println("Goes to Gerald's 2nd parse account");
                Parse.initialize(this, "FfgWRQCND4dXxjYufUwZJ0IIpy9D3xwMtswDjt9E", "6ZltYpYHmN82p0BLu5a2r13AjwFGNUEznKgb0ykn");
            }
            if (WelcomeActivity.enteredPassword.equals("stonecreek")) {
                editor.putString("EmployerCode", "stonecreek");
                System.out.println("Goes to stonecreek's account");
                Parse.initialize(this, "aqrcIBAYS6ranB8G3znOYRDOqpkhFzEfl8r8yaFi", "5OL6N6b0RIKZOfLPdBitTZNG35OzWIPUeuyS9jHQ");
            }

            editor.apply();
        }

        // This needs to be here for the parse to work without entering a password every time
        if (employerPass.equals("Gerald")) {
            Parse.initialize(this, "YuTd2MEdXK9M3hnxDPMXr2o4UAN2P3P1UoAeRVcV", "9TNC9THjdIrh0V1s2WCOY1VrqzqzKunWJlczrs46");
        } else if(employerPass.equals("Gerald2")) {
            Parse.initialize(this, "FfgWRQCND4dXxjYufUwZJ0IIpy9D3xwMtswDjt9E", "6ZltYpYHmN82p0BLu5a2r13AjwFGNUEznKgb0ykn");
        } else if (employerPass.equals("stonecreek")) {
            Parse.initialize(this, "aqrcIBAYS6ranB8G3znOYRDOqpkhFzEfl8r8yaFi", "5OL6N6b0RIKZOfLPdBitTZNG35OzWIPUeuyS9jHQ");
        }
        //**************************************************************************************
        //**************************************************************************************
        //**************************************************************************************

        //use the employee class
        employee = new Employee();

        //set up the rest of the app
        setUpName();
        setUpCurrentProject();
        setUpTable();

       // AdBuddiz.setPublisherKey("864b0946-dc65-4847-aace-a0cf9cf15eab");
       // AdBuddiz.cacheAds(this); // this = current Activity

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

        if(theName.equals("")) {
            setName();
        }
    }

    /**
     *  This method is called to set up the project/services display during the onCreate.
     *
     *  If there is not a project set, nothing will be displayed, but if there is a project,
     *  the word "Project: " will be displayed followed by the project name.
     */
    void setUpCurrentProject() {
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
     *  This method sets up the time table during the onCreate.
     *
     *  If there are times that have been saved they will be set here. This method basically
     *  sets up the app with all the employees previous hours.
     */
    void setUpTable() {
        todayHour = (TextView) findViewById(R.id.todaysHours);
        weekHour = (TextView) findViewById(R.id.thisWeeksHours);
        monthHour = (TextView) findViewById(R.id.thisMonthsHours);

        //gets current date
        Date currentDate = new Date();

        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        int currentDay = c.get(Calendar.DAY_OF_MONTH);
        int clockInDay = setting.getInt("CurrentWeek", 0);

        // Is it still the same day that we started keeping track of with hoursWorkedToday
        // if same day keep adding on to hoursWorkedToday
        if (currentDay == clockInDay) {
            employee.setDailyTotal(setting.getFloat("HoursWorkedToday", 0));
            numOfTimesPunchedInToday = setting.getInt("numOfTimesPunchedInToday", 0);
        }
        //otherwise it is a new day so start back at 0
        else {
            employee.setDailyTotal((float)0);
            numOfTimesPunchedInToday = 0;
        }

        // Gets current week
        int currentWeek = c.get(Calendar.WEEK_OF_YEAR);
        int clockInWeek = setting.getInt("CurrentWeek", 0);

        // Is it still the same week
        if (currentWeek == clockInWeek) {
            employee.setWeeklyTotal(setting.getFloat("HoursWorkedThisWeek", 0));
        }
        //otherwise it is a new week so start back at 0
        else {
            employee.setWeeklyTotal((float)0);
        }

        // Gets current month
        int currentMonth = c.get(Calendar.MONTH);
        int clockInMonth = setting.getInt("CurrentMonth", 0);

        //Is it still the same month
        if(currentMonth == clockInMonth) {
            employee.setMonthlyTotal(setting.getFloat("HoursWorkedThisMonth", 0));
        }
        //otherwise it is a new month so start back at 0
        else {
            employee.setMonthlyTotal((float) 0);
        }

        // Is employee still punched in
        employee.setPunchedIn(setting.getBoolean("PunchedIn", false));

        setting.getBoolean("NewClock", false);

        // If employee is still clocked in go to clock in screen
        if (employee.getPunchedIn()) {
            // Get punch in time
            Date clockInTime = new Date(setting.getLong("PunchInTime", 0));
            employee.setClockInTime(clockInTime);

            long timeDiff = currentDate.getTime() - employee.getClockInTime().getTime();

            float hours2 = (float) (timeDiff / (60 * 60 * 1000.0));
            float hoursWorkedToday = hours2 + employee.getDailyTotal();  //hours worked today
            float hoursWorkedThisWeek = (float)hours2 + employee.getWeeklyTotal(); // hours worked this week plus today's hours
            float hoursWorkedThisMonth = (float)hours2 + employee.getMonthlyTotal(); // hours worked this month plus today's hours

            todayHour.setText("Today:         " + String.format("%.2f", hoursWorkedToday));
            weekHour.setText("This Week:   " + String.format("%.2f", hoursWorkedThisWeek));
            monthHour.setText("This Month:  " + String.format("%.2f", hoursWorkedThisMonth));

            //so that we can call punchIn method
            alreadyPunchedIn = true;
            employee.setPunchedIn(false);

            View v = null;
            this.punchIn(v);
        }
        else {
        //I don't know why the Today string needs more spaces to align correctly...
        todayHour.setText("Today:         " + String.format("%.2f", employee.getDailyTotal()));
        weekHour.setText ("This Week:   " + String.format("%.2f", employee.getWeeklyTotal()));
        monthHour.setText("This Month:  " + String.format("%.2f", employee.getMonthlyTotal()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * This is where we can add settings to the options menu.
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

        // Settings from menu are taken care of here.
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
            case R.id.calendarView:
                calendarView();
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

        // I left this here for future reference
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
     * This method displays the employees total hours in a calendar view.
     *
     *  It is not written yet, but is here to be completed in the future.
     */
    void calendarView() {
        Toast.makeText(MainActivity.this, "This feature is not available yet", Toast.LENGTH_SHORT).show();

    }

    /**
     * ADDTIME function
     * This function will add hours to hoursWorkedToday, hoursWorkedThisWeek and MonthlyHours to
     * keep track of their total hours.
     * In additional, when these hours are updated, it also update the value in our
     * remote database Parse.com to keep it update to date.
     * By Johnny Liang
     */
    public void updateParseTimes() {
        // Date format for day and month
        DateFormat dfDate = new SimpleDateFormat("dd");
        DateFormat dfMonth = new SimpleDateFormat("MM");
        String dayTest = dfDate.format(employee.getClockInTime());
        String monthTest = dfMonth.format(employee.getClockInTime());

        Date currentDate = new Date();
        long timeDiff = currentDate.getTime() - employee.getClockInTime().getTime();

        float hours = (float) (timeDiff / (60 * 60 * 1000.0));
        float hoursWorkedToday = hours + employee.getDailyTotal();  //hours worked today
        float hoursWorkedThisWeek = (float)hours + employee.getWeeklyTotal(); // hours worked this week plus today's hours
        float hoursWorkedThisMonth = (float)hours + employee.getMonthlyTotal(); // hours worked this month plus today's hours

        totalHourFormat = String.format("%.2f", hoursWorkedToday);
        totalWeekFormat = String.format("%.2f", hoursWorkedThisWeek);
        totalMonthFormat = String.format("%.2f", hoursWorkedThisMonth);

        System.out.println("Total Hour: " + totalHourFormat);

        final int parseDateTest = Integer.parseInt(dayTest);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("BossTimeTracker");

        query.whereEqualTo("dDate", dayTest);
        query.whereEqualTo("cMonth", monthTest);
        query.whereEqualTo("aName", employee.getName());

        query.getFirstInBackground(new GetCallback<ParseObject>() {

           // MainActivity activity;///////////////???????????

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
                                parseObject.put("fDailyHour", totalHourFormat);
                                parseObject.put("gWeeklyHour", totalWeekFormat);
                                parseObject.put("hMonthlyHour", totalMonthFormat);
                                parseObject.saveInBackground();
                            }
                            else {
                                Log.d("score", "The updateClass request failed");
                            }
                        }
                    });
                }
            }
        });
    }

    /**
     * GETDATESTRING
     * This function will get the user's clocked in time,
     * and formatted to only display the date instead of
     * full information
     * @return
     */
    public String getDateString() {
        DateFormat df = new SimpleDateFormat("dd");
        dateFormat = df.format(startingDate);
        return dateFormat;
    }


    /**
     * This method responds to the Edit Punch In button, and calls the showDialog() functions.
     *
     * Date dialog is left in but commented out for future use.
     * @param view
     */
    public void editPunchIn(View view) {
        if(!employee.getPunchedIn()) {
            // If there isn't already a employee clockInTime
            if (!alreadyPunchedIn) {
                startingDate = new Date();
                employee.setClockInTime(startingDate);
            }

            showDialog(TIME_DIALOG_ID_IN);
            //showDialog(DATE_DIALOG_ID_IN);

            /* Ideally this wouldn't happen until after the above thread is complete,
               Doing this would get rid of the 36 sec lag time after the new time is entered. */
                alreadyPunchedIn = true;
                View v = null;
                this.punchIn(v);
        }
    }

    /**
     * This method responds to the Edit Punch Out button, and calls the showDialog() functions.
     *
     * Date dialog is left in but commented out for future use.
     * @param view
     */
    public void editPunchOut(View view) {
        if(employee.getPunchedIn()) {

            showDialog(TIME_DIALOG_ID_OUT);
            // showDialog(DATE_DIALOG_ID_OUT);

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
     * This method takes the dialog id (999-996) and switch between dialogs to be showed and executed.
     *
     * Date dialog stuff is left in but commented out for future use.
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
                TimePickerDialog tpd = new TimePickerDialog(this, timePickerListenerIn,
                        inHour, inMinute, false);
                tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        Toast.makeText(MainActivity.this, "Cancelled", Toast.LENGTH_LONG).show();
                    }
                });
                return tpd;
            }
           /* case DATE_DIALOG_ID_OUT: {
                return new DatePickerDialog(this, datePickerListenerOut,
                        outYear, outMonth, outDay);
            }*/
            case TIME_DIALOG_ID_OUT: {
                TimePickerDialog tpd = new TimePickerDialog(this, timePickerListenerOut,
                        inHour, inMinute, false);
                tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        Toast.makeText(MainActivity.this, "Cancelled", Toast.LENGTH_LONG).show();
                    }
                });
                return tpd;
            }
        }
        return null;
    }

    /**
     * This function takes the values the user picks from the dialog, and assign them to local time variables.
     * Specifically this is the time part for Punch In. It will then execute the punch in process.
     */
    private TimePickerDialog.OnTimeSetListener timePickerListenerIn = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
            if (selectedHour < 0) {
                Log.e(TAG2, "The selectedHour is not right!!!");
            } else {
                inHour = selectedHour;
            }
            inMinute = selectedMinute;

            // Convert inHour and inMinute to date object
            Calendar cal = Calendar.getInstance();
            cal.setTime(employee.getClockInTime());
            cal.set(Calendar.HOUR_OF_DAY,inHour);
            cal.set(Calendar.MINUTE,inMinute);

            // Sets new startingDate
            Date newStartingDate = cal.getTime();
            employee.setClockInTime(newStartingDate);

            // Updates total hours.
           /* Date currentDate = new Date();
            long timeDiff = currentDate.getTime() - employee.getClockInTime().getTime();
            int timesNumber = (int)timeDiff / 36000;
            timesNumber *= .01;
*/
           // employee.incDailyTotal(timesNumber);
           // employee.incWeeklyTotal(timesNumber);
            //employee.incMonthlyTotal(timesNumber);

            //It can take up to 36 seconds for the times to be updated
            Toast.makeText(MainActivity.this, "Punched In time will update shortly", Toast.LENGTH_LONG).show();
        }
    };

    /**
     * This function takes the values the user picks from the dialog, and assign them to local time variables.
     * Specifically this is the time part for Punch Out. Process will be added soon.
     */
    private TimePickerDialog.OnTimeSetListener timePickerListenerOut = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int selectedHour,
                              int selectedMinute) {
            outHour = selectedHour;
            outMinute = selectedMinute;

            Date oldClockOutTime = new Date();

            // Convert outHour and outMinute to date object
            Calendar cal = Calendar.getInstance();
            cal.setTime(oldClockOutTime);
            cal.set(Calendar.HOUR_OF_DAY,outHour);
            cal.set(Calendar.MINUTE,outMinute);

            // Sets new clockOutDate
            Date newClockOutTime = cal.getTime();

            // Updates total hours.
            long timeDiff = oldClockOutTime.getTime() - newClockOutTime.getTime();
            int timesNumber = (int)timeDiff / 36000;
            timesNumber *= .01;

            Log.i(TAG2, "oldClockOutTime: " + oldClockOutTime);
            Log.i(TAG2, "newClockOutTime: " + newClockOutTime);

            Log.i(TAG2, "******************CLOCKINTIME: " + employee.getClockInTime());
            long newClockInTime = employee.getClockInTime().getTime() + timeDiff;
            Date newInTime = new Date(newClockInTime);

            Log.i(TAG2, "******************newInTime: " + newInTime);

            employee.setClockInTime(newInTime);
            Log.i(TAG2, "new clock in time: " + employee.getClockInTime());



            // Subtracts the need time
            /*employee.incDailyTotal(-timesNumber);
            employee.incWeeklyTotal(-timesNumber);
            employee.incMonthlyTotal(-timesNumber);

            //added this stuff to try to fix it but im not sure if it is correct......
            float hoursWorkedToday = (float)0.01 * timesNumber + setting.getFloat("HoursWorkedToday", 0);
            float hoursWorkedThisWeek = (float) 0.01 * timesNumber + setting.getFloat("HoursWorkedThisWeek", 0);
            float hoursWorkedThisMonth = (float)0.01 * timesNumber + setting.getFloat("HoursWorkedThisMonth", 0);

            employee.setDailyTotal(hoursWorkedToday);
            employee.setWeeklyTotal(hoursWorkedThisWeek);
            employee.setMonthlyTotal(hoursWorkedThisMonth);

            //I don't know why the Today string needs more spaces to align correctly
            todayHour.setText("Today:         " + String.format("%.2f", employee.getDailyTotal()));
            weekHour.setText("This Week:   " + String.format("%.2f", employee.getWeeklyTotal()));
            monthHour.setText("This Month:  " + String.format("%.2f", employee.getMonthlyTotal()));

            // Saves the stuff we just changed
            SharedPreferences.Editor editor = setting.edit();
            editor.putFloat("DayHours", employee.getDailyTotal());
            editor.putFloat("WeekHours", employee.getWeeklyTotal());
            editor.putFloat("MonthHours", employee.getMonthlyTotal());

            editor.apply();*/

        }
    };

    /**
     * This Method will take the values the user picks from the dialog, and assign them to local time variables.
     * Specifically this is the date part for Punch In. The Date part will be selected before the time part,
     * so the main processes can be found in the later time part. This is here for future use.
     */
    /*private DatePickerDialog.OnDateSetListener datePickerListenerIn
            = new DatePickerDialog.OnDateSetListener() {
        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            inYear = selectedYear;
            inMonth = selectedMonth;
            inDay = selectedDay;
        }
    };*/

    /**
     * This function will take the values the user picks from the dialog, and assign them to local time variables.
     * Specifically this is the date part for Punch Out. The Date part will be selected before the time part,
     * so the main processes can be found in the later time part. This is here for future use.
     */
  /*  private DatePickerDialog.OnDateSetListener datePickerListenerOut
            = new DatePickerDialog.OnDateSetListener() {
        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            outYear = selectedYear;
            outMonth = selectedMonth;
            outDay = selectedDay;
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

    /**
     * This method is were the time tracking work is done.
     *
     * Do work method will get the current time, and calculate the time difference
     * between current time and the time when user clocked in.
     * Format the time difference, and convert to hours
     */
    public void doWork() {
        Log.i(TAG2, "doWork started");
        runOnUiThread(new Runnable() {
            public void run() {
                try {

                    TextView statusView = (TextView) findViewById(R.id.status);
                    Date currentTime = new Date();
                    Date clockedInTime;
                    clockedInTime = employee.getClockInTime();

                    long diff = currentTime.getTime() - clockedInTime.getTime();//Time since punch in

                    long seconds = diff / 1000 % 60;
                    long minutes = diff / (60 * 1000) % 60;
                    long hours = diff / (60 * 60 * 1000) % 60;

                    String secondFormat = String.format("%02d", seconds);
                    String minuteFormat = String.format("%02d", minutes);
                    String hourFormat = String.format("%02d", hours);

                    //SecondFormat left intentionally
                    String timeSincePunchIn = hourFormat + ":" + minuteFormat;// + ":" + secondFormat;

                    String status = "                Punched In";
                    String startTimeString = "Work start time: " + getClockInTimeAsString();
                    String timeAlreadyWorkedString = "Time since punch in: " + timeSincePunchIn;

                    String statusDisplay = status + "\n" + startTimeString + "\n" + timeAlreadyWorkedString;
                    statusView.setTextColor(0xff18ff1a);
                    statusView.setText(statusDisplay);

                    float hours2 = (float) (diff / (60 * 60 * 1000.0));
                    float hoursWorkedToday = hours2 + employee.getDailyTotal();  //hours worked today
                    float hoursWorkedThisWeek = hours2 + employee.getWeeklyTotal(); // hours worked this week plus today's hours
                    float hoursWorkedThisMonth = hours2 + employee.getMonthlyTotal(); // hours worked this month plus today's hours

                    // I don't know why the Today string needs more spaces to align correctly
                    todayHour.setText("Today:         " + String.format("%.2f", hoursWorkedToday));
                    weekHour.setText("This Week:   " + String.format("%.2f", hoursWorkedThisWeek));
                    monthHour.setText("This Month:  " + String.format("%.2f", hoursWorkedThisMonth));

                    // Save the stuff
                    SharedPreferences.Editor editor = setting.edit();
                    editor.putFloat("HoursWorkedToday", employee.getDailyTotal());
                    editor.putFloat("HoursWorkedThisWeek", employee.getWeeklyTotal());
                    editor.putFloat("HoursWorkedThisMonth", employee.getMonthlyTotal());

                    // Set SharePreferences variable to true when employee clocked in
                    editor.putBoolean("PunchedIn", employee.getPunchedIn());

                    // Keeps track of which day it is
                    Calendar c = Calendar.getInstance();
                    c.setTime(currentTime);
                    int day = c.get(Calendar.DAY_OF_MONTH);
                    editor.putInt("CurrentDay", day);

                    // Keeps track of which week it is
                    int week = c.get(Calendar.WEEK_OF_YEAR);
                    editor.putInt("CurrentWeek", week);

                    // Keeps track of which month it is
                    int month = c.get(Calendar.MONTH);
                    editor.putInt("CurrentMonth", month);

                    // Keeps track of clockInTime
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
     * This method sets the time format to either 12 hour or 24 hour
     */
    public String getClockInTimeAsString() {
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

            // Gets current time if you weren't already clocked in before
            Date punchInTime = new Date();
            if (!alreadyPunchedIn) {
                employee.setClockInTime(punchInTime);
            }

            SharedPreferences.Editor editor = setting.edit();

            // Create columns for Parse BossTimeTracker table
            if (!alreadyPunchedIn && numOfTimesPunchedInToday == 0) {
                Date date = employee.getClockInTime();
                parseTimeObject = new ParseObject("BossTimeTracker");
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
                SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
                SimpleDateFormat dayFormat = new SimpleDateFormat("dd");

                // Testing format
                String fullDate = dateFormat.format(date);
                String s = yearFormat.format(date);
                String s2 = monthFormat.format(date);
                String s3 = dayFormat.format(date);
                System.out.println("\nThe full date is: " + fullDate + ", the year is: " + s + ", the month is: " + s2 + ", the day is: " + s3);

                //Set parseTimeObject
                parseTimeObject.put("aName", employee.getName());
                parseTimeObject.put("bYear", yearFormat.format(date));
                parseTimeObject.put("cMonth", monthFormat.format(date));
                parseTimeObject.put("dDate", dayFormat.format(date));
                parseTimeObject.put("eProject", currentProject);
                parseTimeObject.put("fDailyHour", "0");
                parseTimeObject.put("gWeeklyHour", "0");
                parseTimeObject.put("hMonthlyHour", "0");
                parseTimeObject.saveInBackground();
            }

            numOfTimesPunchedInToday++;
            editor.putInt("numOfTimesPunchedInToday", numOfTimesPunchedInToday);
            editor.commit();

            // Run thread that keeps track of time
            Thread loadThread = new Thread(count);
            loadThread.start();

            /*Left for future updates*/
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

            // Change view
            String status = "                Punched Out";
            TextView textView = (TextView) findViewById(R.id.status);
            textView.setTextColor(0xffff1410);
            textView.setText(status);

            // Set clock out time
            Date endingDate = new Date();
            employee.setClockOutTime(endingDate);

            // Set total times
            long diff = endingDate.getTime() - employee.getClockInTime().getTime();//Time since punch in
            float hours = diff / (float)(60 * 60 * 1000.0);
            float hoursWorkedToday = hours + employee.getDailyTotal(); // hours worked today
            float hoursWorkedThisWeek = hours + employee.getWeeklyTotal(); // hours worked this week plus today's hours
            float hoursWorkedThisMonth = hours + employee.getMonthlyTotal(); // hours worked this month plus today's hours

            employee.setDailyTotal(hoursWorkedToday);
            employee.setWeeklyTotal(hoursWorkedThisWeek);
            employee.setMonthlyTotal(hoursWorkedThisMonth);

            // Saves the stuff
            SharedPreferences.Editor editor = setting.edit();
            editor.putBoolean("PunchedIn", employee.getPunchedIn());

            editor.putFloat("HoursWorkedToday", employee.getDailyTotal());
            editor.putFloat("HoursWorkedThisWeek", employee.getWeeklyTotal());
            editor.putFloat("HoursWorkedThisMonth", employee.getMonthlyTotal());

            editor.apply();

            /*Left for future updates*/
            //GPSCoord outLocation = new GPSCoord();
            //employee.setClockInLocation(outLocation);
        }
    }
}
