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
    private long itime;
    private TextView personText;
    private TextView locationText;
    private LocationManager locationManager;
    private double c = 30000.0;

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
        private long t;
        private long elapsed;
        private boolean started = false;
        private double saved;

        @Override
        public void onLocationChanged(Location loc) {
            /*
             * initialize variables during first run
             */
            if (!started) {
                ialt = loc.getAltitude();
                initalt = ialt;
                itime = System.nanoTime();
                t = 0;
                started = true;
                saved = 0;
            }
            /*
             * update variables every cycle
             */
            else {
                double altitude = loc.getAltitude() - initalt;
                long temp_t = System.nanoTime();
                elapsed = temp_t - itime;
                t = temp_t - elapsed;
                time.setText("Time Elapsed: " + elapsed +"ns");
                double cur_lat = loc.getLatitude();
                double cur_alt = loc.getAltitude();
                double xyspeed = loc.getSpeed();
                double bearing = loc.getBearing();
                double v_x = xyspeed*Math.sin(bearing * Math.PI / 180);
                double v_y = xyspeed*Math.cos(bearing*Math.PI/180);
                double v_alt = (cur_alt-ialt)/t;
                ialt = cur_alt;
                double time_e = t/saved(v_x, v_y, v_alt, altitude, cur_lat);
                time_e -= t;
                saved += time_e;
                tv.setText("Velocity in x: "+v_x + "m/s\nVelocity in y: " + v_y + "m/s\nVelocity in z: " + v_alt + "m/s\nAltitude: " + altitude +"m\n" + "Relative time saved: " + saved + "ns");
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

    /**
     * @param vx long velocity
     * @param vy lat velocity
     * @param vz alt velocity
     * @param alt altitude relative to starting point
     * @param lat current latitude
     * @return the ratio between t_phone and t_earth
     */
    double saved(double vx, double vy, double vz, double alt, double lat) {
        double vE = 2*465.1*Math.cos(lat*Math.PI/180);
        double redshift = 9.8*alt/(c*c);
        double vg = Math.sqrt(vx*vx + vy*vy +vz*vz);
        double special = (vg*vg + vx*vE)/(2*c*c);
        return 1 + redshift - special;
    }
}