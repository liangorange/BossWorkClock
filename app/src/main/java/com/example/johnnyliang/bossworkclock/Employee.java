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
    private boolean punchedIn;

    //Getters and setters
    public String getName()             { return name;         }
    public Time getDailyTotal()         { return dailyTotal;   }
    public Time getWeeklyTotal()        { return weeklyTotal;  }
    public Time getMonthlyTotal()       { return monthlyTotal; }
    public TimeTracker getTimeTracker() { return timeTracker;  }
    public boolean getPunchedIn()       { return punchedIn;    }

    public void setName(String name)                    { this.name = name;                 }
    public void setDailyTotal(Time dailyTotal)          { this.dailyTotal = dailyTotal;     }
    public void setWeeklyTotal(Time weeklyTotal)        { this.weeklyTotal = weeklyTotal;   }
    public void setMonthlyTotal(Time monthlyTotal)      { this.monthlyTotal = monthlyTotal; }
    public void setTimeTracker(TimeTracker timeTracker) { this.timeTracker = timeTracker;   }
    public void setPunchedIn(boolean punchedIn)         { this.punchedIn = punchedIn;       }

    public Employee() { name = ""; dailyTotal = new Time(0,0,0); weeklyTotal = new Time(0,0,0);
                        monthlyTotal = new Time(0,0,0); timeTracker = new TimeTracker(); punchedIn = false; }

    public void sendUpdate() {

    }
}
