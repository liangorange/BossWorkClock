package com.example.johnnyliang.bossworkclock;

/**
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

    //Getters and setters
    public float getDailyTotal()         { return dailyTotal;       }
    public float getWeeklyTotal()        { return weeklyTotal;      }
    public float getMonthlyTotal()       { return monthlyTotal;     }

    public boolean getPunchedIn()        { return punchedIn;        }
    public String getClockInTime()        { return clockInTime;      }
    public String getClockOutTime()       { return clockOutTime;     }
    public GPSCoord getClockInLocation()  { return clockInLocation;  }
    public GPSCoord getClockOutLocation() { return clockOutLocation; }


    public void setName(String name)        { this.name = name;     }


    public void incDailyTotal(double hour)   { dailyTotal += hour;   }
    public void incWeeklyTotal(double hour)  { weeklyTotal += hour;  }
    public void incMonthlyTotal(double hour) { monthlyTotal += hour; }

    public void setDailyTotal(double hour)   { dailyTotal += hour;   }
    public void setWeeklyTotal(double hour)  { weeklyTotal += hour;  }
    public void setMonthlyTotal(double hour) { monthlyTotal += hour; }


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



    public String getName() {

        //setContentView(R.layout.activity_main);
        //EditText theName = (EditText) findViewById(R.id.name);
      //  String name = theName.getText().toString();
        return name;
    }

    public void clockIn() {

        //keep track of time
      //  DateFormat dateFormat = new SimpleDateFormat("HH:mm");
       // Date date = new Date();
        //setClockInTime(dateFormat.format(date)); //15:59
        // System.out.println(dateFormat.format(date));

    }

    public void clockOut() {
      //  DateFormat dateFormat = new SimpleDateFormat("HH:mm");
       // Date date = new Date();
        //setClockOutTime(dateFormat.format(date));//15:59
    }

    public void editClockIn() {

    }

    public void editClockOut() {

    }

    public void sendUpdate() {

    }
}
