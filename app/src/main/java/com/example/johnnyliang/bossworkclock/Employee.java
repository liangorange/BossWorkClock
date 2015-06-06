package com.example.johnnyliang.bossworkclock;

import java.sql.Time;

/**
 * Created by Gerald on 6/5/2015.
 */
public class Employee {
    private String name;
    private Time dailyTotal;
    private Time weeklyTotal;
    private Time monthlyTotal;
    private TimeTracker timeTracker;

    //Getters and setters
    public String getName()             { return name;         }
    public Time getDailyTotal()         { return dailyTotal;   }
    public Time getWeeklyTotal()        { return weeklyTotal;  }
    public Time getMonthlyTotal()       { return monthlyTotal; }
    public TimeTracker getTimeTracker() { return timeTracker;  }

    public void setName(String name)                    { this.name = name;                 }
    public void setDailyTotal(Time dailyTotal)          { this.dailyTotal = dailyTotal;     }
    public void setWeeklyTotal(Time weeklyTotal)        { this.weeklyTotal = weeklyTotal;   }
    public void setMonthlyTotal(Time monthlyTotal)      { this.monthlyTotal = monthlyTotal; }
    public void setTimeTracker(TimeTracker timeTracker) { this.timeTracker = timeTracker;   }

    //public Employee() { name = ""; dailyTotal = new Time(); weeklyTotal = new Time();
     //                   monthlyTotal = new Time(); timeTracker = new TimeTracker(); }
    public void sendUpdate() {

    }
}
