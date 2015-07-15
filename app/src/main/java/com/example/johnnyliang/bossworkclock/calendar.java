package com.example.johnnyliang.bossworkclock;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Gerald on 7/14/2015.
 */
public class calendar extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);

        TextView textView = (TextView) findViewById(R.id.printer);
        //CalendarView calView = (CalendarView) findViewById(R.id.calendar);
        Intent intent = getIntent();
        String scripture = intent.getStringExtra(MainActivity.EXTRA);
        textView.setText(scripture);
    }

   /* @Override
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
}   */
}