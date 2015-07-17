package comWorkClock.WorkClock.johnnyliang.bossworkclock;

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
    private Date clockInTime;
    private Date clockOutTime;
    private GPSCoord clockInLocation;
    private GPSCoord clockOutLocation;
    private Date editInDate;

    //Getters and setters
    public float getDailyTotal()          { return dailyTotal;       }
    public float getWeeklyTotal()         { return weeklyTotal;      }
    public float getMonthlyTotal()        { return monthlyTotal;     }
    public boolean getPunchedIn()         { return punchedIn;        }
    public Date getClockInTime()          { return clockInTime;      }
    public Date getClockOutTime()         { return clockOutTime;     }
    public GPSCoord getClockInLocation()  { return clockInLocation;  }
    public GPSCoord getClockOutLocation() { return clockOutLocation; }
    public Date getEditInDate()           { return  editInDate;      }

    public void setDailyTotal(float hour)          { dailyTotal = hour;   }
    public void setWeeklyTotal(float hour)         { weeklyTotal = hour;  }
    public void setMonthlyTotal(float hour)        { monthlyTotal = hour; }
    public void setName(String name)               { this.name = name;    }
    public void setPunchedIn(boolean punchedIn)    { this.punchedIn = punchedIn;       }
    public void setClockInTime(Date clockInTime)   { this.clockInTime = clockInTime;   }
    public void setClockOutTime(Date clockOutTime) { this.clockOutTime = clockOutTime; }
    public void setEditInDate(Date in)             { this.editInDate = in;             }

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

    public void setClockInLocation(GPSCoord clockInLocation) {
        this.clockInLocation.latitude = clockInLocation.latitude;
        this.clockInLocation.longitude = clockInLocation.longitude;
    }

    public void setClockOutLocation(GPSCoord clockOutLocation) {
        this.clockOutLocation.latitude = clockOutLocation.latitude;
        this.clockOutLocation.longitude = clockOutLocation.longitude;
    }

    public String getName() {
        return name;
    }

    public void sendUpdate() {

    }
}
