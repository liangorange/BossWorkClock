package com.example.johnnyliang.bossworkclock;

import java.sql.Time;



/*class GPSCoord extends FragmentActivity implements LocationListener{
    public double longitude;
    public double latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /* Use the LocationManager class to obtain GPS locations
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

    }

    LocationListener mlocListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Toast.makeText(getApplicationContext(), "status changed", Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText(getApplicationContext(), "GPS Enabled", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(getApplicationContext(), "GPS Disabled", Toast.LENGTH_SHORT).show();
        }
    };

    mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mlocListener);
};*/

 //class GPSCoord {
   // public double longitude;
    //public double latitude;
//}
/**
 * Created by Gerald on 6/5/2015.
 */
public class TimeTracker {
    private Time clockInTime;
    private Time clockOutTime;
    private GPSCoord clockInLocation;
    private GPSCoord clockOutLocation;

    // Getters and setters
    public Time getClockInTime(){ return clockInTime; }
    public Time getClockOutTime() { return clockOutTime; }
    public GPSCoord getClockInLocation() { return clockInLocation; }
    public GPSCoord getClockOutLocation() { return clockOutLocation; }
    public void setClockInTime(Time clockInTime) { this.clockInTime = clockInTime; }
    public void setClockOutTime(Time clockOutTime) { this.clockOutTime = clockOutTime; }

    public void setClockInLocation(GPSCoord clockInLocation) {
        this.clockInLocation.latitude = clockInLocation.latitude;
        this.clockInLocation.longitude = clockInLocation.longitude;
    }

    public void setClockOutLocation(GPSCoord clockOutLocation) {
        this.clockOutLocation.latitude = clockOutLocation.latitude;
        this.clockOutLocation.longitude = clockOutLocation.longitude;
    }

    public void clockIn() {
        //keep track of time
    }

    public void clockOut() {

    }

    public void editClockIn() {

    }

    public void editClockOut() {

    }
}
