package com.example.george.livegps;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.logging.Handler;


public class GPS extends Activity {

    private long startTime = 0L;
    private long timeInMilliseconds;
    private double alt;
    private double longitude;
    private double latitude;
    private TextView personText;
    private TextView locationText;
    private LocationManager locationManager;
    private LinkedList list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        /* Use the LocationManager class to obtain GPS locations */
        LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locListener = new MyLocationListener();
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);
    }

    public class MyLocationListener implements LocationListener {
        TextView tv = (TextView) findViewById(R.id.quote);
        TextView time = (TextView) findViewById(R.id.person);

        @Override
        public void onLocationChanged(Location loc) {
            Log.d("tag", "Finding Altitude");
            double alt = loc.getAltitude();
            Log.d("tag", "Alt: " + String.valueOf(alt));
            Log.d("tag", "Finding Velocity");
            double v = loc.getSpeed();
            Log.d("tag", "Speed: " + String.valueOf(v));
            Log.d("tag", "Finding Latitude");
            double lat = loc.getLatitude();
            Log.d("tag", "Lat: " + String.valueOf(lat));
            Log.d("tag", "Finding Longitude");
            double lon = loc.getLongitude();
            Log.d("tag", "Lon: " + String.valueOf(lon));
            Log.d("tag", "Finding Time");
            long t = loc.getTime();
            Log.d("tag", "Time: " + String.valueOf(t));
            String Text = "My current location is: " +
                    "\nLatitude = " + lat +
                    "\nLongitude = " + lon +
                    "\nAltitude = " + alt +
                    "\nVelocity = " + v;
            String count = "Time: "+t;
            // Display location
            tv.setText(Text);
            time.setText(count);
        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(getApplicationContext(), "Gps Disabled", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText(getApplicationContext(), "Gps Enabled", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
    }
}