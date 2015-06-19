package com.example.johnnyliang.bossworkclock;

/**
 * Created by JohnnyLiang on 6/15/15.
 */
public class TimeCount implements Runnable {
    MainActivity activity;
    Employee employee;

    public void setActivity(MainActivity myActivity) {
        activity = myActivity;
    }

    public void setEmployeeActivity(Employee myEmployee) {
        employee = myEmployee;
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted() && employee.getPunchedIn()){
            try {
                activity.doWork();
                employee.setTotalHour(0.01);
                Thread.sleep(1000); // Pause of 1 Second
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }catch(Exception e){
            }
        }
    }  
}
