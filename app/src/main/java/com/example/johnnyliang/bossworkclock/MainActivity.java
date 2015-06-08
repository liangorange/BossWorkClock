package com.example.johnnyliang.bossworkclock;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;


public class MainActivity extends ActionBarActivity {
    private Employee employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        employee = new Employee();
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
        String out = " Punched In";
        TextView textView = (TextView) findViewById(R.id.outStatus);
        textView.setTextColor(0xff18ff1a);
        textView.setText(out);
        //String in = " Punched In";
        //TextView textView2 = (TextView) findViewById(R.id.inStatus);
        //textView2.setText(in);

        /*Time t1 = new Time();
        t1 = employee.getTimeTracker().getClockInTime();

        Time t2 = new Time();

        String s = "Work start time: " + t1);
        String s2 = "Time worked today: " + employee.getTimeTracker().getClockinTime();
        String s3 = s + "\n" + s2;
        TextView textView3 = (TextView) findViewById(R.id.inTimeStatus);
        textView3.setText(s3);
*/
        //employee.getTimeTracker().getClockInLocation();
        //employee.getTimeTracker().clockIn();
        Toast.makeText(MainActivity.this,"Punched In", Toast.LENGTH_SHORT).show();
    }

    public void punchOut(View view){
        String out = " Punched Out";
        TextView textView = (TextView) findViewById(R.id.outStatus);
        textView.setText(out);
        String in = "";
        TextView textView2 = (TextView) findViewById(R.id.inStatus);
        textView2.setText(in);

       // employee.getTimeTracker().getClockOutLocation();
        //employee.getTimeTracker().clockOut();
        Toast.makeText(MainActivity.this,"Punched Out", Toast.LENGTH_SHORT).show();
    }
}
