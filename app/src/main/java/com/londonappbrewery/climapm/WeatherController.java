package com.londonappbrewery.climapm;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
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

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class WeatherController extends AppCompatActivity {

    //region Constants
    final int REQUET_CODE = 123;
    // Base url for API call to openweathermap
    final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather";
    // App ID to use OpenWeather data
    final static String APP_ID = "66b689aded3dc277d515075f67107cd3";
    // Time between location updates (5000 milliseconds or 5 seconds)
    final long MIN_TIME = 1000;
    // Distance between location updates (1000m or 1km)
    final float MIN_DISTANCE = 1;
    //endregion

    //region Fields
    String LOCATION_PROVIDER = LocationManager.NETWORK_PROVIDER;
    TextView mCityLabel;
    ImageView mWeatherImage;
    TextView mTemperatureLabel;

    LocationManager _locationManager;
    LocationListener _locationListener;
    //endregion

    //region Methods called by operating system
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

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Clima", "OnResume() called");
        if (isLocationServiceEnabled()) {
            Log.d("Clima", "Location service enabled.");
            Toast.makeText(this, "Fetching your weather data", Toast.LENGTH_LONG).show();
            this.getWeatherForCurrentLocation();
        } else Toast.makeText(this, "Enable location service to continue", Toast.LENGTH_LONG).show();

    }
    //endregion

    /**
     * Checks if the location service is enabled or not.
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.P)
    private boolean isLocationServiceEnabled() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isLocationEnabled();
    }

    private void getWeatherForCurrentLocation() {
        this._locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        this._locationListener = new com.londonappbrewery.climapm.LocationListener();
        requestLocation();
    }

    private void requestLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION};
            ActivityCompat.requestPermissions(this, permissions, REQUET_CODE);
        }
        _locationManager.requestLocationUpdates(LOCATION_PROVIDER, MIN_TIME, MIN_DISTANCE, _locationListener);
    }

    public void getDataFromNetwork(RequestParams params){

        Log.d("Clima","Calling API");

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(WEATHER_URL, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("Clima", "Success! JSON: " + response.toString());
//                WeatherDataModel weatherData = WeatherDataModel.fromJson(response);
//                updateUI(weatherData);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                Log.e("Clima", "Fail " + e.toString());
                Toast.makeText(WeatherController.this, "Request Failed", Toast.LENGTH_SHORT).show();

//                Log.d(LOGCAT_TAG, "Status code " + statusCode);
//                Log.d(LOGCAT_TAG, "Here's what we got instead " + response.toString());
            }
        });

        Log.d("Clima","API called");
    }

    //region Callback methods
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUET_CODE){
            if(grantResults.length > 0 || grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d("Clima", "onRequestPermissionResult(): Permission granted");
                getWeatherForCurrentLocation();
            } else Log.d("Clima", "Permission denied");
        }
    }
    //endregion

}
