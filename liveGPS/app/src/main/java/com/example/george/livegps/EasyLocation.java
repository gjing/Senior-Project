package com.example.george.livegps;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * Created by George on 5/5/2015.
 */
public class EasyLocation{

    private LocationManager locationManager;
    private LocationListener locationListener;
    private String locationProvider;
    private Location loc;

    public EasyLocation(LocationManager locMan)
    {

        locationListener = new MyLocationListener();
        locationManager = locMan;

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 35000, 10, this.locationListener);

        locationProvider = LocationManager.NETWORK_PROVIDER;
        loc = locationManager.getLastKnownLocation(locationProvider);
    }

    public double getAltitude()
    {
        return loc.getAltitude();
    }

    public double getLongitude()
    {
        return loc.getLongitude();
    }

    public double getLatitude()
    {
        return loc.getLatitude();
    }

    public long getTime()
    {
        return loc.getTime();
    }

    public void reInit()
    {
        locationProvider = LocationManager.NETWORK_PROVIDER;
        loc = locationManager.getLastKnownLocation(locationProvider);
    }
}

final class MyLocationListener implements LocationListener {

    @Override
    public void onLocationChanged(Location locFromGps) {
        // called when the listener is notified with a location update from the GPS
    }

    @Override
    public void onProviderDisabled(String provider) {
        // called when the GPS provider is turned off (user turning off the GPS on the phone)
    }

    @Override
    public void onProviderEnabled(String provider) {
        // called when the GPS provider is turned on (user turning on the GPS on the phone)
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // called when the status of the GPS provider changes
    }
}
