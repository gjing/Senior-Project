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
    private double ialt;
    private double initalt;
    private double ilongitude;
    private double ilatitude;
    private long itime;
    private TextView personText;
    private TextView locationText;
    private LocationManager locationManager;
    private LinkedList list;
    private double erad = 6371000.0;
    private double c = 300000000.0;

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
        private double alt;
        private long t;
        private long elapsed = 0;
        private boolean started = false;

        @Override
        public void onLocationChanged(Location loc) {
            if (!started) {
                ialt = loc.getAltitude();
                initalt = loc.getAltitude();
                ilatitude = loc.getLatitude();
                ilongitude = loc.getLongitude();
                itime = loc.getTime();
                t = itime;
                elapsed = itime;
                started = true;
            }
            else {
                alt = loc.getAltitude() - initalt;
                double latitude = loc.getLatitude() - ilatitude;
                ilatitude = ilatitude - latitude;
                double longitude = loc.getLongitude() - ilongitude;
                ilongitude = ilongitude - longitude;
                double altitude = loc.getAltitude() - ialt;
                ialt = ialt - altitude;
                t = loc.getTime() - elapsed;
                elapsed = loc.getTime() - itime;
                time.setText("Time Elapsed: " + elapsed/1000);
                double x = Math.abs(latitude * erad / 360);
                double y = Math.abs(longitude*erad/360);
                double z = Math.abs(altitude);
                double v = Math.sqrt(x*x + y*y + z*z)/t;
                tv.setText("Velocity: "+v + ", Redshift: " + alt);
            }
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