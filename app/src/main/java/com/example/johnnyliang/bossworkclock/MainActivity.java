package com.example.johnnyliang.bossworkclock;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.Time;
import android.content.DialogInterface.OnKeyListener;

public class MainActivity extends ActionBarActivity {
    private Employee employee;
  //  private FileOutputStream out;
    private PrintWriter out;
    private PrintStream in;
    private String filename = "name.txt";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        employee = new Employee();
        this.readName();


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
    

    public String getName() {
        //String name="";
      setContentView(R.layout.activity_main);
        //EditText theName = (EditText) findViewById(R.id.name);
        //String name = theName.getText().toString();
        TextView theName = (TextView) findViewById(R.id.name);
        String name = theName.getText().toString();
        theName.setText(name);
        Toast.makeText(MainActivity.this,"here1", Toast.LENGTH_SHORT).show();
        return name;
    }
    /**
     *
     */
    public void saveName(View view) {
         String name = this.getName();
            try {
                BufferedWriter out = new BufferedWriter(new FileWriter(filename));
                Toast.makeText(MainActivity.this,"here2", Toast.LENGTH_SHORT).show();
                out.write(name);
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        this.readName();
    }

    public void readName() {
        TextView textView = (TextView) findViewById(R.id.name);
        String name= "bob";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;


            //takes one line at a time and sets the topic from it
            while ((line = reader.readLine()) != null) {
                name += line;
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        textView.setText(name);
    }

    /**
     *
     * @param view
     */
    public void punchIn(View view){

        // Can't punch in if you already are punched in
        if(!employee.getPunchedIn()) {
            employee.setPunchedIn(true);
            employee.getTimeTracker().getClockInLocation();
            employee.getTimeTracker().clockIn();

            String t1;// = new Time(0, 0, 0);
            t1 = employee.getTimeTracker().getClockInTime();

            Time t2 = new Time(0, 0, 0);

            String status = "                Punched In";
            String s = "Work start time: " + t1;
            String s2 = "Time worked today: ";//+ current time - t1;
            String s3 = status + "\n" + s + "\n" + s2;
            TextView textView = (TextView) findViewById(R.id.status);
            textView.setTextColor(0xff18ff1a);
            textView.setText(s3);

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
