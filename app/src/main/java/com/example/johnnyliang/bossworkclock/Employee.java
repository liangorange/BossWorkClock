package com.example.johnnyliang.bossworkclock;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Gerald on 6/5/2015.
 */
public class Employee {
    private String name;
    private double dailyTotal;
    private double weeklyTotal;
    private double monthlyTotal;
    private boolean punchedIn;
    private double totalHour = 0;
    private String clockInTime;
    private String clockOutTime;
    private GPSCoord clockInLocation;
    private GPSCoord clockOutLocation;

    //Getters and setters
    public double getDailyTotal()         { return dailyTotal;       }
    public double getWeeklyTotal()        { return weeklyTotal;      }
    public double getMonthlyTotal()       { return monthlyTotal;     }
    public boolean getPunchedIn()         { return punchedIn;        }
    public double getTotalHour()          { return totalHour;        }
    public String getClockInTime()        { return clockInTime;      }
    public String getClockOutTime()       { return clockOutTime;     }
    public GPSCoord getClockInLocation()  { return clockInLocation;  }
    public GPSCoord getClockOutLocation() { return clockOutLocation; }


    public void setName(String name)                 { this.name = name;                 }
    public void setDailyTotal(double dailyTotal)     { this.dailyTotal = dailyTotal;     }
    public void setWeeklyTotal(double weeklyTotal)   { this.weeklyTotal = weeklyTotal;   }
    public void setMonthlyTotal(double monthlyTotal) { this.monthlyTotal = monthlyTotal; }
    public void setPunchedIn(boolean punchedIn)      { this.punchedIn = punchedIn;       }
    public void setClockInTime(String clockInTime)   { this.clockInTime = clockInTime;   }
    public void setClockOutTime(String clockOutTime) { this.clockOutTime = clockOutTime; }

    public void setClockInLocation(GPSCoord clockInLocation) {
        this.clockInLocation.latitude = clockInLocation.latitude;
        this.clockInLocation.longitude = clockInLocation.longitude;
    }

    public void setClockOutLocation(GPSCoord clockOutLocation) {
        this.clockOutLocation.latitude = clockOutLocation.latitude;
        this.clockOutLocation.longitude = clockOutLocation.longitude;
    }

    public void setTotalHour(double hour) {
        totalHour += hour;
    }

    public String getName() {

        //setContentView(R.layout.activity_main);
        //EditText theName = (EditText) findViewById(R.id.name);
      //  String name = theName.getText().toString();
        return name;
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

    public void sendUpdate() {

    }
}
