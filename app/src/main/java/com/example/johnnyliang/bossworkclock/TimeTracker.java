package com.example.johnnyliang.bossworkclock;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by Gerald on 6/5/2015.
 */
public class TimeTracker {
    private String clockInTime;
    private String clockOutTime;
    private GPSCoord clockInLocation;
    private GPSCoord clockOutLocation;

    // Getters and setters
    public String getClockInTime(){ return clockInTime; }
    public String getClockOutTime() { return clockOutTime; }
    public GPSCoord getClockInLocation() { return clockInLocation; }
    public GPSCoord getClockOutLocation() { return clockOutLocation; }
    public void setClockInTime(String clockInTime) { this.clockInTime = clockInTime; }
    public void setClockOutTime(String clockOutTime) { this.clockOutTime = clockOutTime; }

    public void setClockInLocation(GPSCoord clockInLocation) {
        this.clockInLocation.latitude = clockInLocation.latitude;
        this.clockInLocation.longitude = clockInLocation.longitude;
    }

    public void setClockOutLocation(GPSCoord clockOutLocation) {
        this.clockOutLocation.latitude = clockOutLocation.latitude;
        this.clockOutLocation.longitude = clockOutLocation.longitude;
    }

    public void clockIn() {
        //keep track of time
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        setClockInTime(dateFormat.format(date)); //15:59
       // System.out.println(dateFormat.format(date));

    }

    public void clockOut() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        setClockOutTime(dateFormat.format(date));//15:59
    }

    public void editClockIn() {

    }

    public void editClockOut() {

    }
}
