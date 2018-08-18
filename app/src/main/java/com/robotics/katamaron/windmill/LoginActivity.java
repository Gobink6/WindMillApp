package com.robotics.katamaron.windmill;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class LoginActivity extends Activity {
    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;
    private EditText ET_USERNAME, ET_PASSWORD;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String UserType = "UserType";
    public static final String UserPhone = "UserPhone";
    public static final String Tablelayout = "Trues";
    public final static String MESSAGE_KEY ="ganeshannt.senddata.message_key";
    String token_phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent intent = getIntent();
        token_phone = intent.getStringExtra("token");
        // Api level  above or equal to 21 notification bar color change
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorKatomaran));
        }

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        // Get Reference to variables
        ET_USERNAME = (EditText) findViewById(R.id.la_username);
        ET_PASSWORD = (EditText) findViewById(R.id.la_password);


    }

    public void Login(View arg0) {

        // Get text from email and passord field
         String username = ET_USERNAME.getText().toString();
         String password = ET_PASSWORD.getText().toString();

        // Initialize  AsyncLogin() class with email and password
        new AsyncLogin().execute(username,password);
    }
    private class AsyncLogin extends AsyncTask<String, String,String>
    {
        // start progressDialog
        ProgressDialog pdLoading = new ProgressDialog(LoginActivity.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                //  URL address where your Rails file resides
                url = new URL("http://192.168.0.117:3000/check");

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
                        .appendQueryParameter("name", params[0])
                        .appendQueryParameter("pass", params[1]);
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
            String singleParsed = "";
            String phone = "";
            String inu,suc;
            if(s == s){

            }
// successfully received database details
            try {
                JSONArray jsonArray = new JSONArray(s);
                String[] rows = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    String mod = obj.getString("role");
                    String num = obj.getString("phone");
                    singleParsed = singleParsed + (mod);
                    phone = phone + (num);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //this method will be running on UI thread

            pdLoading.dismiss();
            String table = "true";
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(UserType, singleParsed);
            editor.putString(UserPhone, phone);
            editor.putString(Tablelayout, table);
            editor.commit();

            if(singleParsed.equalsIgnoreCase("admin"))
            {
                /* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
                 */
                //start nxt activity
                suc = "";
               // invalid.setText(suc);
                Intent intent = new Intent(LoginActivity.this,Google_map_Activity.class);
                startActivity(intent);
                // display the admin message
                Toast.makeText(LoginActivity.this, "Admin", Toast.LENGTH_LONG).show();
                finish();
            }else if (singleParsed.equalsIgnoreCase("superadmin")){
                 /* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
                 */
                Intent intent1 = new Intent(LoginActivity.this,Google_map_Activity.class);
                startActivity(intent1);
                //Display the superadmin message
                Toast.makeText(LoginActivity.this, "super admin ", Toast.LENGTH_LONG).show();
                finish();

            } else if (singleParsed.equalsIgnoreCase("user")) {
                 /* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
                 */
                Intent intent = new Intent(LoginActivity.this,Google_map_Activity.class);
                startActivity(intent);
                //Display the user massage
                Toast.makeText(LoginActivity.this, "normaluser", Toast.LENGTH_LONG).show();
                finish();
            }else if (s.equalsIgnoreCase("exception") || s.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(LoginActivity.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }else if (s.equalsIgnoreCase("error")) {

                Toast.makeText(LoginActivity.this, "invalied password", Toast.LENGTH_LONG).show();

            }else if (singleParsed.equalsIgnoreCase("inu")) {
                inu = "Invalied username or password";
                //invalid.setText(inu);
                Toast.makeText(LoginActivity.this, "invalied password", Toast.LENGTH_LONG).show();

            }
        }

    }

}
