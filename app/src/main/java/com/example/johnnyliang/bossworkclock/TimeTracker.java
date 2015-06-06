package com.example.johnnyliang.bossworkclock;

import java.sql.Time;

/**
 * Created by Gerald on 6/5/2015.
 */
public class TimeTracker {
    private Time clockInTime;
    private Time clockOutTime;
    private String clockInLocation;
    private String clockOutLocation;

    // Getters and setters
    public Time getClockInTime(){ return clockInTime; }
    public Time getClockOutTime() { return clockOutTime; }
    public String getClockInLocation() { return clockInLocation; }
    public String getClockOutLocation() { return clockOutLocation; }
    public void setClockInTime(Time clockInTime) { this.clockInTime = clockInTime; }
    public void setClockOutTime(Time clockOutTime) { this.clockOutTime = clockOutTime; }
    public void setClockInLocation(String clockInLocation) {
        this.clockInLocation = clockInLocation; }
    public void setClockOutLocation(String clockOutLocation) {
        this.clockOutLocation = clockOutLocation; }

    public void clockIn() {

    }

    public void clockOut() {

    }

    public void addClockIn() {

    }

    public void addClockOut() {

    }
}
