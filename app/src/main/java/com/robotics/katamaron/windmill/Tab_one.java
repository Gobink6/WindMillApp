package com.robotics.katamaron.windmill;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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

import pl.droidsonroids.gif.GifImageView;

public class Tab_one extends Fragment {
    ListView listView;
   private TextView TV_SPEED,TV_GEN,TV_ROTOR,TV_WIND,TV_PITCH;
   private Button BN_BUTTON;
    private String message,windid;
    GifImageView gifImageView;
    public boolean mStoploop = true;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String WindName = "WindNameKey";
    public static final String UserType = "UserType";
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    String textd;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Tab_one() {
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
    public static Tab_one newInstance(String param1, String param2) {
        Tab_one fragment = new Tab_one();
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
      //  String user = getArguments().getString("info");
        //String name=this.getArguments().getString("info").toString();
        // listView = (ListView) View.f

     //  thread.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_tab_one, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
         TV_WIND = (TextView) view.findViewById(R.id.view_wind);
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
        //listView = (ListView) view.findViewById(R.id.listView1);
        //Refferance the Variable
        TV_SPEED = (TextView) view.findViewById(R.id.textView);
        TV_GEN = (TextView) view.findViewById(R.id.view_gen);
        TV_ROTOR = (TextView) view.findViewById(R.id.view_rotor);
         TV_PITCH = (TextView) view.findViewById(R.id.view_pitch);
         BN_BUTTON = (Button) view.findViewById(R.id.reset);
        // gifImageView = (GifImageView) view.findViewById(R.id.GifImageView);
        BN_BUTTON.setVisibility(View.GONE);


        SharedPreferences sharedpreferences = this.getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        if (sharedpreferences.contains(WindName)) {
            //TV_PITCH.setText(sharedpreferences.getString(WindName, ""));
           windid =  sharedpreferences.getString(WindName, "");
        }
       message = sharedpreferences.getString(UserType, "");
        if (message.equals("SuperAdmin")){
            BN_BUTTON.setVisibility(View.VISIBLE);
        }
       // running();
         thread.start();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
        mStoploop = false;
        thread.interrupt();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mStoploop = false;
       //thread.interrupt();

    }

    @Override
    public void onStop() {
        super.onStop();

        mStoploop = false;
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

 /*   Thread thread = new Thread() {
        @Override
        public void run() {
            try {
                mStoploop = false;
                while (!thread.isInterrupted()) {
                    Thread.sleep(5000);
                            getJSON("http://192.168.0.115:3000/getwindid");

                            //  Toast.makeText(getActivity(), "Token Generated", Toast.LENGTH_SHORT).show();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }; */

    Thread thread = new Thread() {
        @Override
        public void run() {
            try {
                mStoploop = true;
                while (mStoploop) {
                    Thread.sleep(3000);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AsyncLink().execute(windid);
                           // getJSON("http://192.168.0.111:3000/getwindid");

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
                url = new URL("http://192.168.0.110:3000/statusdata");

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

            try{
               // Toast.makeText( getActivity(), s, Toast.LENGTH_SHORT).show();
                loadIntoListView(s);
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }
    private void loadIntoListView(String json) throws JSONException {
        String st = "",pw = "",gn ="",fq="",rt = "",wd = "",pt = "";
        if (json != null) {
            JSONArray jsonArray = new JSONArray(json);
//              String[] status = new String[jsonArray.length()];
//            String[] power = new String[jsonArray.length()];
//            String[] gen = new String[jsonArray.length()];
//            String[] frequency = new String[jsonArray.length()];
//            String[] rotor = new String[jsonArray.length()];
//            String[] wind = new String[jsonArray.length()];
            String[] pitch = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String status = obj.getString("status");
                String power = obj.getString("power");
                String gen = obj.getString("gen");
                String frequency = obj.getString("frequency");
                String rotor = obj.getString("rotor");
                String wind = obj.getString("wind");
                String pitchs = obj.getString("pitch");
                st = st + (status);
                pw = pw + (power);
                gn = gn + (gen);
                fq = fq + (frequency);
                rt = rt + (rotor);
                wd = wd + (wind);
                pt = pt + (pitchs);
                TV_SPEED.setText(pw);
                TV_GEN.setText(gn);
                TV_ROTOR.setText(rt);
                TV_WIND.setText(wd);
                TV_PITCH.setText(pt);

               // gifImageView.setImageResource(R.drawable.millgif);
//                TV_WIND.setText(wind[i]);
            }

            // listView.setAdapter(new ArrayAdapter<String>(view.getContext(),
            //       android.R.layout.simple_list_item_1 , items));
        }else
        {
            thread.interrupt();
        }

        }
}
