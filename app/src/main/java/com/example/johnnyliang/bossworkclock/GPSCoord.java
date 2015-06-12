package com.example.johnnyliang.bossworkclock;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

/**
 * Created by Gerald on 6/12/2015.
 */
public class GPSCoord extends FragmentActivity implements LocationListener {
    public double longitude;
    public double latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Use the LocationManager class to obtain GPS locations */
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        LocationListener mlocListener = new LocationListener() {
            int i = 1;
            public void  mine(Location location) {
                longitude = location.getLongitude();
                latitude = location.getLatitude();
            }
            @Override
            public void onLocationChanged(Location location) {
                while (i == 1) {
                    mine(location);
                    i = 2;
                }
                System.out.println(" should only see this once");
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {  }

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
    }
    //not used but necessary to compile
    @Override
    public void onLocationChanged(Location location) { }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {  }
    @Override
    public void onProviderEnabled(String provider) {  }
    @Override
    public void onProviderDisabled(String provider) { }
}
