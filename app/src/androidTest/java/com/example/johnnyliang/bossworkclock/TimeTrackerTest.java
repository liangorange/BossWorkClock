package com.example.johnnyliang.bossworkclock;

import android.test.InstrumentationTestCase;

import java.sql.Time;

/**
 * Created by Gerald on 6/10/2015.
 */
public class TimeTrackerTest extends InstrumentationTestCase {
    public void testClockInTime() {
        TimeTracker tracker = new TimeTracker();
        Time time = new Time(1,35,23);

        tracker.setClockInTime(time);
        assertEquals(time,tracker.getClockInTime() );

    }

    public void testClockInLocation() {
        TimeTracker tracker = new TimeTracker();
        GPSCoord coord = new GPSCoord();
        coord.latitude = 5000;
        coord.longitude = 6000;

        tracker.setClockInLocation(coord);
        assertEquals(coord,tracker.getClockInLocation());
    }
}
