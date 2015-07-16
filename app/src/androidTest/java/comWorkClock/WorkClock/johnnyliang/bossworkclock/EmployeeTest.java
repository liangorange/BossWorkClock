package comWorkClock.WorkClock.johnnyliang.bossworkclock;

import android.test.InstrumentationTestCase;

import java.util.Date;

/**
 * Created by YH Jonathan Kwok on 11/6/2015.
 */
public class EmployeeTest extends InstrumentationTestCase {
    public void nameTester() {
        Employee misterAwesome = new Employee();
        misterAwesome.setName("Awesome");

        assertEquals("Awesome", misterAwesome.getName());
    }

    public void testClockInHour() {
        Date date;
        Employee myEmployee = new Employee();
       // myEmployee.setClockInTime();

    }

}
