package com.example.johnnyliang.bossworkclock;

import java.util.Date;


/**
 * This class represents an employee.
 *
 * This is where we can store all the information about the employee. It is used together
 * with the MainActivity to keep track of the employees work information.
 *
 * Created by Gerald on 6/5/2015.
 */
public class Employee {
    private String name;
    private float dailyTotal = 0;
    private float weeklyTotal = 0;
    private float monthlyTotal = 0;

    private boolean punchedIn;
    private String clockInTime;
    private String clockOutTime;
    private GPSCoord clockInLocation;
    private GPSCoord clockOutLocation;
    private Date editInDate;

    //Getters and setters

    public float getDailyTotal()         { return dailyTotal;       }
    public float getWeeklyTotal()        { return weeklyTotal;      }
    public float getMonthlyTotal()       { return monthlyTotal;     }

    public boolean getPunchedIn()         { return punchedIn;        }
    public String getClockInTime()        { return clockInTime;      }
    public String getClockOutTime()       { return clockOutTime;     }
    public GPSCoord getClockInLocation()  { return clockInLocation;  }
    public GPSCoord getClockOutLocation() { return clockOutLocation; }
    public Date getEditInDate()           { return  editInDate;      }


    public void setName(String name)        { this.name = name;     }


    /**
     * This function will add the daily total hours by the hour passed as parameter
     * @param hour
     */
    public void incDailyTotal(double hour)   { dailyTotal += hour;   }

    /**
     * This function will add the weekly total hours by the hour passed as parameter
     * @param hour
     */
    public void incWeeklyTotal(double hour)  { weeklyTotal += hour;  }

    /**
     * This function will add the monthly total hours by the hour passed as parameter
     * @param hour
     */
    public void incMonthlyTotal(double hour) { monthlyTotal += hour; }

    public void setDailyTotal(double hour)   { dailyTotal += hour;   }
    public void setWeeklyTotal(double hour)  { weeklyTotal += hour;  }
    public void setMonthlyTotal(double hour) { monthlyTotal += hour; }


    public void setPunchedIn(boolean punchedIn)      { this.punchedIn = punchedIn;       }
    public void setClockInTime(String clockInTime)   { this.clockInTime = clockInTime;   }
    public void setClockOutTime(String clockOutTime) { this.clockOutTime = clockOutTime; }
    public void setEditInDate(Date in)               { this.editInDate = in;               }

    public void setClockInLocation(GPSCoord clockInLocation) {
        this.clockInLocation.latitude = clockInLocation.latitude;
        this.clockInLocation.longitude = clockInLocation.longitude;
    }

    public void setClockOutLocation(GPSCoord clockOutLocation) {
        this.clockOutLocation.latitude = clockOutLocation.latitude;
        this.clockOutLocation.longitude = clockOutLocation.longitude;
    }

    public String getName() {

        //setContentView(R.layout.activity_main);
        //EditText theName = (EditText) findViewById(R.id.name);
      //  String name = theName.getText().toString();
        return name;
    }

    public void editClockOut() {

    }

    public void sendUpdate() {

    }
}
