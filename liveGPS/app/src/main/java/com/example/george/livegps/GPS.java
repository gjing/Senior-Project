package com.example.george.livegps;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
        RelativeLayout touch = (RelativeLayout) findViewById(R.id.touch);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        personText = (TextView) findViewById(R.id.person);
        locationText = (TextView) findViewById(R.id.quote);
        list = new LinkedList();
        touch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GPSnow.run();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_g, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private Runnable GPSnow = new Runnable() {
        public void run() {
            String locationProvider = LocationManager.NETWORK_PROVIDER;
            EasyLocation loc = new EasyLocation(locationManager);
            timeInMilliseconds = loc.getTime();
            alt = loc.getAltitude();
            longitude = loc.getLongitude();
            latitude = loc.getLatitude();
            locationText.setText(alt + "\n" + longitude + "\n" + latitude);
            personText.setText(Long.toString(timeInMilliseconds));
        }
    };
}
