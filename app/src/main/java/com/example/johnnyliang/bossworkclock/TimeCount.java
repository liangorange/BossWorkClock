package com.example.johnnyliang.bossworkclock;

import android.util.Log;

/**
 * This class keeps track of time.
 *
 * Created by JohnnyLiang on 6/15/15.
 */
public class TimeCount implements Runnable {
    MainActivity activity;
    Employee employee;
    public final static String TAG = "TimeCount";

    /**
     * This function assign the MainActivity class to the MainActivity class reference
     * @param myActivity the class object from MainActivity
     */
    public void setActivity(MainActivity myActivity) {
        activity = myActivity;
    }

    /**
     * This function assign the Employee class to the Employee class reference
     * @param myEmployee the class object from Employee
     */
    public void setEmployeeActivity(Employee myEmployee) {
        employee = myEmployee;
    }

    /**
     * This function will run while the background thread is running
     * It increases the dailyTotal, weeklyTotal, monthlyTotal by 0.01 every 36 seconds
     */
    @Override
    public void run() {
       // Toast.makeText(TimeCount.this, "Punched in", Toast.LENGTH_SHORT).show();


        while(!Thread.currentThread().isInterrupted() && employee.getPunchedIn()){
            try {
                activity.doWork();
                activity.addTime(0.01);
                //employee.setDailyTotal();
                Thread.sleep(1000); // Pause of 1 Second
                employee.incDailyTotal(0.01);
                employee.incWeeklyTotal(0.01);
                employee.incMonthlyTotal(0.01);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }catch(Exception e){
                Log.e(TAG,"The background run thread is not working", e);
            }
        }
    }
}
