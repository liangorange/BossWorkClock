package comWorkClock.WorkClock.johnnyliang.bossworkclock;

import android.test.InstrumentationTestCase;

import java.util.Date;

/**
 * Created by YH Jonathan Kwok on 11/6/2015.
 */
public class EmployeeTest extends InstrumentationTestCase {
    Employee myEmployee = new Employee();

    public void testClockInHour() {
        Date date = new Date();
        myEmployee.setClockInTime(date);

        assertEquals(date, myEmployee.getClockInTime());
    }

    public void testDailyTotal() {
        float total= 256;
        myEmployee.setDailyTotal(total);

        assertEquals(256, myEmployee.getDailyTotal());
    }

    public void testMonthlyTotal() {
        float total= 354;
        myEmployee.setMonthlyTotal(total);

        assertEquals(354, myEmployee.getDailyTotal());
    }
}
