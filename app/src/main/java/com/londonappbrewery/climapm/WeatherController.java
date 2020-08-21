package com.londonappbrewery.climapm;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import static java.lang.String.*;


public class WeatherController extends AppCompatActivity {

    // Constants:
    final int REQUET_CODE = 123;

    // Base url for API call to openweathermap
    final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather";
    // App ID to use OpenWeather data
    final String APP_ID = "66b689aded3dc277d515075f67107cd3";
    // Time between location updates (5000 milliseconds or 5 seconds)
    final long MIN_TIME = 5000;
    // Distance between location updates (1000m or 1km)
    final float MIN_DISTANCE = 1000;

    // TODO: Set LOCATION_PROVIDER here:
    String LOCATION_PROVIDER = LocationManager.NETWORK_PROVIDER;


    // Member Variables:
    TextView mCityLabel;
    ImageView mWeatherImage;
    TextView mTemperatureLabel;

    // TODO: Declare a LocationManager and a LocationListener here:
    LocationManager mLocationManager;
    LocationListener mLocationListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_controller_layout);

        // Linking the elements in the layout to Java code
        mCityLabel = (TextView) findViewById(R.id.locationTV);
        mWeatherImage = (ImageView) findViewById(R.id.weatherSymbolIV);
        mTemperatureLabel = (TextView) findViewById(R.id.tempTV);
        ImageButton changeCityButton = (ImageButton) findViewById(R.id.changeCityButton);


        // TODO: Add an OnClickListener to the changeCityButton here:

    }


    // TODO: Add onResume() here:

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Clima", "OnResume() called");
        Log.d("Clima", "Getting weather for current location");

        if(isLocationServiceEnabled()){
            Log.d("Clima", "Location service enabled.");
            Toast.makeText(this, "Fetching your weather data", Toast.LENGTH_LONG).show();

            this.getWeatherForCurrentLocation();
        }

        else Toast.makeText(this, "Enable location service to continue", Toast.LENGTH_LONG).show();

    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    private boolean isLocationServiceEnabled() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isLocationEnabled();
    }


    // TODO: Add getWeatherForNewCity(String city) here:

    private void getWeatherForCurrentLocation() {

        mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                Log.d("Clima", "onlocationChanged() call back received");

                String latitude = String.valueOf(location.getLatitude());
                String longitude = String.valueOf(location.getLongitude());

                Log.d("Clima", "Latitude: " + latitude);
                Log.d("Clima", "Longitude: " + longitude);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Log.d("Clima", "onProviderDisabled()");

            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUET_CODE);

            return;
        }
        mLocationManager.requestLocationUpdates(LOCATION_PROVIDER, MIN_TIME, MIN_DISTANCE, mLocationListener);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUET_CODE){

            if(grantResults.length > 0 || grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d("Clima", "onRequestPermissionResult(): Permission granted");
                getWeatherForCurrentLocation();
            }

            else {
                Log.d("Clima", "Permission denied");
            }
        }
    }


// TODO: Add letsDoSomeNetworking(RequestParams params) here:



    // TODO: Add updateUI() here:



    // TODO: Add onPause() here:



}
