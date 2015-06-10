package com.example.johnnyliang.bossworkclock;

import android.test.InstrumentationTestCase;

import java.sql.Time;

/**
 * Created by Gerald on 6/10/2015.
 */
public class TimeTrackerTest extends InstrumentationTestCase {
    public void testClockOutTime() {
        TimeTracker timeTracker = new TimeTracker();

        Time time = new Time(1, 3, 10);
        timeTracker.setClockOutTime(time);

        assertEquals(time, timeTracker.getClockOutTime());
    }

    public void testTimeDifference() {
        TimeTracker timeTracker = new TimeTracker();

        Time time1 = new Time(1, 3, 10);
        Time time2 = new Time(2, 10, 50);
        Time difference = new Time(1, 7, 40);

        timeTracker.setClockInTime(time1);
        timeTracker.setClockOutTime(time2);
        Time diffFromClass = timeTracker.getClockOutTime() - timeTracker.getClockInTime();

        assert(difference, diffFromClass);
    }
}

