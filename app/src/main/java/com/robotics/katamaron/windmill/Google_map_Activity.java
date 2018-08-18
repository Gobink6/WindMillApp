package com.robotics.katamaron.windmill;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Google_map_Activity extends AppCompatActivity implements OnMapReadyCallback {
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String UserType = "UserType";
    public static final String UserPhone = "UserPhone";
    public static final String WindName = "WindNameKey";
    public static final String Tokens = "Token";

    private GoogleMap mMap;
    private Marker marker;
    LocationManager locationManager;
    private FusedLocationProviderClient mFusedLocationClient;
    private static FragmentManager fragmentManager;
    List<LatLng> latLngList = new ArrayList<LatLng>();
    private  List<String> title = new ArrayList<>();
    private  List<String> statuses = new ArrayList<>();
    //List<String> mill_id = new ArrayList<>();
    ArrayList<String> mill_id = new ArrayList<String>();
    String method = "Run";
    //String check = "First";
    String user = "First";
    String message,phone,getTokens;
    String location = "";
    boolean srhmill = true;
    boolean usmill = true;
    //store user windmill
    String usermills = "";
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private LocationCallback mLocationCallback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map_);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        message = sharedpreferences.getString(UserType, "");
        phone = sharedpreferences.getString(UserPhone, "");
        getTokens = sharedpreferences.getString(Tokens, "");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync((OnMapReadyCallback) Google_map_Activity.this);
        fragmentManager = getSupportFragmentManager();
      //  getJSON("http://192.168.0.115:3000/getlatlong");
//        Intent intent = getIntent();
//        Bundle bundle = getIntent().getExtras();
//        message = bundle.getString("message");
       // phone =bundle.getString("phone");
        excute();



    }
    public void excute() {
        boolean secound = false;
//        if (message.equals("Admin") || message.equals("SuperAdmin")) {
//            getJSON("http://192.168.0.110:3000/getlatlong");
//        } else if (message.equals("User")) {

            Thread thread = new Thread() {
                int num = 0;
                @Override
                public void run() {
                    try {
                        while (num<2) {
                            Thread.sleep(5000);
                            //runOnUiThread(new Runnable() {
                             //   @Override
                              //  public void run() {
                                    if (user.equals("First")){
                                        new AsyncLink().execute(phone,getTokens);
                                    }else{
                                        //String arr[] = usermills.split(" ");
                                        for(int i = 0; i < mill_id.size(); i++){
                                           // System.out.println("arr["+i+"] = " + arr[i].trim());
                                            new Async().execute(mill_id.get(i));
                                        }
                                    }
                            num++;
                        }
                    } catch (InterruptedException e) {
                    }
                }
            };thread.start();

        }
//    }
    //view full screen include notificationBar
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    // Shows the system bars by removing all the flags
// except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
    private void getJSON(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    loadIntoListView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void loadIntoListView (String json) throws JSONException {
        if (json != null) {
            JSONArray jsonArray = new JSONArray(json);
            String[] latitude = new String[jsonArray.length()];
            String[] longitude = new String[jsonArray.length()];
            String[] windmill = new String[jsonArray.length()];
            String[] status = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                windmill[i] = obj.getString("no");
                status[i] = obj.getString("location");
                latitude[i] = obj.getString("latitude");
                longitude[i] = obj.getString("londitude");

                title.add(windmill[i]);
                statuses.add(status[i]);

            }
            //Add latitude and longitude value in array list
            for (int i = 0; i < latitude.length; i++) {
                LatLng point = new LatLng(Double.parseDouble(latitude[i]), Double.parseDouble(longitude[i]));
                latLngList.add(point);
            }
            //Plot the map in given latLngList list
            for (int i = 0; i < latLngList.size(); i++) {
               // Log.i("location: ", String.valueOf(latLngList.get(i)));
                String tittle = "Runmode";
                String tittletwo = "Stopmode";
                createMarker(latLngList.get(i).latitude, latLngList.get(i).longitude, title.get(i), statuses.get(i), tittletwo);

            }
        }else {

        }
    }

    private void loadIntoList (String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        String[] latitude = new String[jsonArray.length()];
        String[] longitude = new String[jsonArray.length()];
        String[] windmill = new String[jsonArray.length()];
        String[] status = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            windmill[i] = obj.getString("no");
            latitude[i] = obj.getString("latitude");
            longitude[i] = obj.getString("londitude");
            title.add(windmill[i]);

        }
        //Add latitude and longitude value in array list
        for(int i=0; i<latitude.length; i++) {
            LatLng point = new LatLng(Double.parseDouble(latitude[i]), Double.parseDouble(longitude[i]));
            latLngList.add(point);
        }
        //Plot the map in given latLngList list

        for(int i = 0 ; i < latLngList.size() ; i++) {
            Log.i("location: ", String.valueOf(latLngList.get(i)));
            String tittle = "Runmode";
            String tittletwo = "Stopmode";
          //  createMarker(latLngList.get(i).latitude, latLngList.get(i).longitude,title.get(i),tittletwo);
            int padding = 0; // offset from edges of the map in pixels
            //CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(latitude, longitude, padding);
           // mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
       // mMap.getUiSettings().setZoomGesturesEnabled(true);
       // mMap.moveCamera(CameraUpdateFactory.zoomTo(6));
        mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(11,77) , 7.0f) );
        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker arg0) {
                String info = arg0.getTitle().trim();

// set Fragmentclass Arguments


                Toast.makeText(Google_map_Activity.this, arg0.getTitle(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Google_map_Activity.this,NavigationActivity.class);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(WindName, info);
                editor.commit();
                startActivity(i);
                finish();
            }
        });
        }

    protected Marker createMarker(double latitude, double longitude, String s, String statusw, String title) {
        if(statusw.equals("Run")) {
            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.wind_run);
            method = "Stop";
            return mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(latitude, longitude))
                    .icon(icon)
                    .title(s));
        }else{
            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.windmill_stop);
            method = "Run";
            return mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(latitude, longitude))
                    .icon(icon)
                    .title(s));
        }

        //.anchor(0.5f, 0.5f));
//
//                .snippet(snippet)
    }
    public class AsyncLink extends AsyncTask<String,String,String> {

        HttpURLConnection conn;
        URL url = null;
        protected void onPreExecute() {
            super.onPreExecute();
            }
        @Override
        protected String doInBackground(String... params) {
            try {
                        // Enter URL address where your rails file resides
                          url = new URL("http://192.168.0.117:3000/getinfo");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "exception";
            }


                try {
                    // Setup HttpURLConnection class to send and receive data from php and mysql
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(READ_TIMEOUT);
                    conn.setConnectTimeout(CONNECTION_TIMEOUT);
                    conn.setRequestMethod("POST");

                    // setDoInput and setDoOutput method depict handling of both send and receive
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    // Append parameters to URL
                    Uri.Builder builder = new Uri.Builder()
                            .appendQueryParameter("userid", params[0])
                            .appendQueryParameter("token", params[1]);
                    // Log.i("Windform4Activity", Name+ Email+ Phone+ Windid + Role);
                    String query = builder.build().getEncodedQuery();

                    // Open connection for sending data
                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));
                    writer.write(query);
                    writer.flush();
                    writer.close();
                    os.close();
                    conn.connect();
                    usmill = false;

                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                    return "exception";
                }




            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);

                    }
                    // Pass data to onPostExecute method
                    return (result.toString());

                } else {

                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            }
            finally {
                conn.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                    loadWindMill(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

        }
    }
    public void loadWindMill(String json) throws JSONException {

        if (json != null) {
            JSONArray jsonArray = new JSONArray(json);
            String[] mi = new String[jsonArray.length()];

            String[] Phone = new String[jsonArray.length()];
            String[] Role = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                 Role[i] = obj.getString("windmill");
                mill_id.add(Role[i]);
              // mill.add(mills[i]);
               // usermills = usermills + (mills);
                srhmill=false;

            }
user = "Second";
        } else{
            // liner();
    }
    }
    public class Async extends AsyncTask<String,String,String> {

        HttpURLConnection conn;
        URL url = null;
        protected void onPreExecute() {
            super.onPreExecute();

        }
        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your ph p file resides
                url = new URL("http://192.168.0.117:3000/getlocation");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "exception";
            }
            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("no", params[0]);
                String query = builder.build().getEncodedQuery();

                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return "exception";
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);

                    }
                    // Pass data to onPostExecute method
                    return (result.toString());

                } else {

                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            }
            finally {
                conn.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                loadIntoListView(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
