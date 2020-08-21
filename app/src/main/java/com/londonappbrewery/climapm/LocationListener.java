package com.londonappbrewery.climapm;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import static java.lang.String.valueOf;


public class LocationListener implements android.location.LocationListener {
    @Override
    public void onLocationChanged(Location location) {

        Log.d("Clima", "onlocationChanged() call back received");

        String latitude = valueOf(location.getLatitude());
        String longitude = valueOf(location.getLongitude());

        Log.d("Clima", "Latitude: " + latitude);
        Log.d("Clima", "Longitude: " + longitude);

        RequestParams params = new RequestParams();
        params.put("lat", latitude);
        params.put("long", longitude);
        params.put("appid", WeatherController.APP_ID);

        WeatherController weatherController = new WeatherController();
        weatherController.getDataFromNetwork(params);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

}
