package com.example.johnnyliang.bossworkclock;

import android.test.InstrumentationTestCase;

/**
 * Created by Gerald on 6/10/2015.
 */
public class TimeTrackerTest extends InstrumentationTestCase {
    public void testClockOutLocation() {
        TimeTracker tt = new TimeTracker();

        tt.setClockOutLocation("Here");
        assertEquals("Here", tt.getClockOutLocation());

        tt.setClockOutLocation("There");
        assertEquals("There", tt.getClockOutLocation());

        tt.setClockOutLocation("No where");
        assertEquals("No where", tt.getClockOutLocation());
    }

    
}
