package theWorkClock2.BossWorkClock2.gerald.thebossworkclock;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

/**
 * This class gets GPS coordinates.
 *
 * The mobile devise location is used to find its location. The longitude and
 * latitude are returned which can then be used to mark a spot on a map.
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

        /**
         * Sets the longitude and latitude.
         */
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
            }

            //not used but necessary to compile
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {  }

            /**
             * Enables GPS
             */
            @Override
            public void onProviderEnabled(String provider) {
                Toast.makeText(getApplicationContext(), "GPS Enabled", Toast.LENGTH_SHORT).show();
            }

            /**
             * Disables GPS
             */
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
