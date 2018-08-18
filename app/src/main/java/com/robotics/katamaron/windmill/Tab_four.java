package com.robotics.katamaron.windmill;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class Tab_four extends Fragment {
    protected TextView TV_POWER,TV_COSPHI,TV_FREQUENCY;
    protected TextView TV_L1V,TV_L1A,TV_L2V,TV_L2A,TV_L3V,TV_L3A;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public boolean mStoploop;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String WindName = "WindNameKey";
    public static final String mypreference = "mypref";
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private String windid_el;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Tab_four() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab1.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab_four newInstance(String param1, String param2) {
        Tab_four fragment = new Tab_four();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_tab_four, container, false);
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Reffernce of variable
        TV_POWER  = (TextView) view.findViewById(R.id.ed_id_pow);
        TV_COSPHI  = (TextView) view.findViewById(R.id.ed_id_cos);
        TV_FREQUENCY  = (TextView) view.findViewById(R.id.ed_id_frq);
        TV_L1V  = (TextView) view.findViewById(R.id.V_L1);
        TV_L1A  = (TextView) view.findViewById(R.id.A_L1);
        TV_L2V  = (TextView) view.findViewById(R.id.V_L2);
        TV_L2A  = (TextView) view.findViewById(R.id.A_L2);
        TV_L3V  = (TextView) view.findViewById(R.id.V_L3);
        TV_L3A  = (TextView) view.findViewById(R.id.A_L3);

        SharedPreferences sharedpreferences = this.getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        if (sharedpreferences.contains(WindName)) {
            //TV_PITCH.setText(sharedpreferences.getString(WindName, ""));
            windid_el =  sharedpreferences.getString(WindName, "");
        }
        thread.start();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        thread.interrupt();
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    Thread thread = new Thread() {
        @Override
        public void run() {
            try {
                mStoploop = true;
                while (mStoploop) {
                    Thread.sleep(5000);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AsyncLink().execute(windid_el);
                            // getJSON("http://192.168.0.115:3000/getwindid");

                            //  Toast.makeText(getActivity(), "Token Generated", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
    public class AsyncLink extends AsyncTask<String,String,String> {

        HttpURLConnection conn;
        URL url = null;
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your ph p file resides
                url = new URL("http://192.168.0.103:3000/electdata");

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
                        .appendQueryParameter("windmillid", params[0]);
                //Log.i("Windform4Activity", Name+ Email+ Phone+ Windid + Role);
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
//            Toast.makeText( getActivity(), s, Toast.LENGTH_SHORT).show();
            try{
                //
                loadIntoListView(s);
            }catch (JSONException e){
                e.printStackTrace();
           }
        }
    }
    private void loadIntoListView(String json) throws JSONException {
        String pw = "",ci = "",fq = "",l1v1 ="",l2v2="",l3v3 = "",l1a1 = "",l2a2 ="",l3a3="";
        if (json != null) {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String power = obj.getString("power");
                String cosphi = obj.getString("cosphi");
                String frequency = obj.getString("frequency");
                String l1v = obj.getString("l1v");
                String l2v = obj.getString("l2v");
                String l3v = obj.getString("l3v");
                String l1a = obj.getString("l1a");
                String l2a = obj.getString("l2a");
                String l3a = obj.getString("l3a");

                pw = pw + (power);
                ci = ci + (cosphi);
                fq = fq + (frequency);
                l1v1 = l1v1 + (l1v);
                l2v2 = l2v2 + (l2v);
                l3v3 = l3v3 + (l3v);
                l1a1 = l1a1 + (l1a);
                l2a2 = l2a2 + (l2a);
                l3a3 = l3a3 + (l3a);
                //settext
                TV_POWER.setText(pw);
                TV_COSPHI.setText(ci);
                TV_FREQUENCY.setText(fq);
                TV_L1V.setText(l1v1);
                TV_L1A.setText(l2v2);
                TV_L2V.setText(l3v3);
                TV_L2A.setText(l1a1);
                TV_L3V.setText(l2a2);
                TV_L3A.setText(l3a3);

            }

        }else
        {
            thread.interrupt();
        }

    }
}


