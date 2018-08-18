package com.robotics.katamaron.windmill;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Tab_three.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tab_three#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab_three extends Fragment {
    TextView TV_TOTAL_TOL,TV_TOTAL_RUN,TV_TOTAL_LINK,TV_TOTAL_GEN1,TV_TOTAL_TURBINO,TV_TOTAL_GEN2,TV_TOTAL_AMBIENT,TV_TOTAL_LINE,TV_TOTAL_YAWING,TV_TOTAL_SERVICE,TV_TOTAL_DISP;
    TextView TV_MONTH_TOL,TV_MONTH_RUN,TV_MONTH_LINE,TV_MONTH_GEN1,TV_MONTH_TURBINE,TV_MONTH_GEN2,TV_MONTH_AMBIENT,TV_MONTH_LINE1,TV_MONTH_YAWING,TV_MONTH_SERVICE,TV_MONTH_DISP;
    TextView TV_TRIP_TOL,TV_TRIP_RUN,TV_TRIP_LINE,TV_TRIP_GEN1,TV_TRIP_TURBINE,TV_TRIP_GEN2,TV_TRIP_AMBIENT,TV_TRIP_LINE1,TV_TRIP_YAWING,TV_TRIP_SERVICE,TV_TRIP_DISP;
    public boolean mStoploop;
    public static final String WindName = "WindNameKey";
    public static final String mypreference = "mypref";
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private String windid_hc;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Tab_three() {
        // Required empty public constructor


      /*  TV_TOTAL_TOL  = (TextView) view.findViewById(R.id.);
        TV_TOTAL_TOL  = (TextView) view.findViewById(R.id.);
        TV_TOTAL_TOL  = (TextView) view.findViewById(R.id.);
        TV_TOTAL_TOL  = (TextView) view.findViewById(R.id.);
        TV_TOTAL_TOL  = (TextView) view.findViewById(R.id.);
        TV_TOTAL_TOL  = (TextView) view.findViewById(R.id.); */



    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab_three.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab_three newInstance(String param1, String param2) {
        Tab_three fragment = new Tab_three();
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

       // thread.start();
        //Refferance the Variable
        //main Active

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //total
        TV_TOTAL_TOL  = (TextView) view.findViewById(R.id.hc_id_tot_tot);
        TV_TOTAL_RUN  = (TextView) view.findViewById(R.id.hc_id_tot_run);
        TV_TOTAL_LINK  = (TextView) view.findViewById(R.id.hc_id_tot_linek);
        TV_TOTAL_GEN1  = (TextView) view.findViewById(R.id.hc_id_tot_gen1);
        TV_TOTAL_TURBINO  = (TextView) view.findViewById(R.id.hc_id_tot_tur);
        TV_TOTAL_GEN2  = (TextView) view.findViewById(R.id.hc_id_tot_gen2);
        TV_TOTAL_AMBIENT  = (TextView) view.findViewById(R.id.hc_id_tot_amb);
        TV_TOTAL_LINE  = (TextView) view.findViewById(R.id.hc_id_tot_line);
        TV_TOTAL_YAWING  = (TextView) view.findViewById(R.id.hc_id_tot_yaw);
        TV_TOTAL_SERVICE  = (TextView) view.findViewById(R.id.hc_id_tot_ser);
        TV_TOTAL_DISP  = (TextView) view.findViewById(R.id.hc_id_tot_disp);
        //MONTH
        TV_MONTH_TOL  = (TextView) view.findViewById(R.id.hc_id_mth_tot);
        TV_MONTH_RUN  = (TextView) view.findViewById(R.id.hc_id_mth_run);
        TV_MONTH_LINE  = (TextView) view.findViewById(R.id.hc_id_mth_linek);
        TV_MONTH_GEN1  = (TextView) view.findViewById(R.id.hc_id_mth_gen1);
        TV_MONTH_TURBINE  = (TextView) view.findViewById(R.id.hc_id_mth_tur);
        TV_MONTH_GEN2  = (TextView) view.findViewById(R.id.hc_id_mth_gen2);
        TV_MONTH_AMBIENT  = (TextView) view.findViewById(R.id.hc_id_mth_amb);
        TV_MONTH_LINE1  = (TextView) view.findViewById(R.id.hc_id_mth_line);
        TV_MONTH_YAWING  = (TextView) view.findViewById(R.id.hc_id_mth_yaw);
        TV_MONTH_SERVICE  = (TextView) view.findViewById(R.id.hc_id_mth_ser);
        TV_MONTH_DISP  = (TextView) view.findViewById(R.id.hc_id_mth_disp);
        //TRIP
        TV_TRIP_TOL  = (TextView) view.findViewById(R.id.hc_id_trip_tot);
        TV_TRIP_RUN  = (TextView) view.findViewById(R.id.hc_id_trip_run);
        TV_TRIP_LINE  = (TextView) view.findViewById(R.id.hc_id_trip_linek);
        TV_TRIP_GEN1  = (TextView) view.findViewById(R.id.hc_id_trip_gen1);
        TV_TRIP_TURBINE  = (TextView) view.findViewById(R.id.hc_id_trip_tur);
        TV_TRIP_GEN2  = (TextView) view.findViewById(R.id.hc_id_trip_gen2);
        TV_TRIP_AMBIENT  = (TextView) view.findViewById(R.id.hc_id_trip_amb);
        TV_TRIP_LINE1  = (TextView) view.findViewById(R.id.hc_id_trip_line);
        TV_TRIP_YAWING  = (TextView) view.findViewById(R.id.hc_id_trip_yaw);
        TV_TRIP_SERVICE  = (TextView) view.findViewById(R.id.hc_id_trip_ser);
        TV_TRIP_DISP  = (TextView) view.findViewById(R.id.hc_id_trip_disp);
        SharedPreferences sharedpreferences = this.getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        if (sharedpreferences.contains(WindName)) {
            //TV_PITCH.setText(sharedpreferences.getString(WindName, ""));
            windid_hc =  sharedpreferences.getString(WindName, "");
        }
        thread.start();


        //MONTH
        TV_MONTH_TOL.setText("12");
        TV_MONTH_RUN.setText("13");
        TV_MONTH_LINE.setText("14");
        TV_MONTH_GEN1.setText("15");
        TV_MONTH_TURBINE.setText("16");
        TV_MONTH_GEN2.setText("17");
        TV_MONTH_AMBIENT.setText("18");
        TV_MONTH_LINE1.setText("19");
        TV_MONTH_YAWING.setText("20");
        TV_MONTH_SERVICE.setText("21");
        TV_MONTH_DISP.setText("22");
        //TRIP
        TV_TRIP_TOL.setText("23");
        TV_TRIP_RUN.setText("24");
        TV_TRIP_LINE.setText("25");
        TV_TRIP_GEN1.setText("26");
        TV_TRIP_TURBINE.setText("27");
        TV_TRIP_GEN2.setText("28");
        TV_TRIP_AMBIENT.setText("29");
        TV_TRIP_LINE1.setText("30");
        TV_TRIP_YAWING.setText("31");
        TV_TRIP_SERVICE.setText("32");
        TV_TRIP_DISP.setText("33");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_tab_three, container, false);

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
        thread.interrupt();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
       // thread.interrupt();
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
 /*  Thread thread = new Thread() {
        @Override
        public void run() {
            try {

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
                 Thread.sleep(5000);
                 getActivity().runOnUiThread(new Runnable() {
                     @Override
                     public void run() {
                         new AsyncLink().execute(windid_hc);
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
                url = new URL("http://192.168.0.103:3000/gethourse");

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
                //Toast.makeText( getActivity(), s, Toast.LENGTH_SHORT).show();
                loadIntoListView(s);
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }
    private void loadIntoListView(String json) throws JSONException {
        String tps = "",li = "",tl = "",tok ="",rn="",gn1 = "",gen2 = "",amt ="",lin="",yaw ="",ser="",ds="";
        if (json != null) {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String timestamp = obj.getString("timestamp");
                String total = obj.getString("total");
                String lineok = obj.getString("lineok");
                String turbineok = obj.getString("turbineok");
                String run = obj.getString("run");
                String genone = obj.getString("genone");
                String gentwo = obj.getString("gentwo");
                String ambient = obj.getString("ambient");
                String line = obj.getString("line");
                String yawing = obj.getString("yawing");
                String service = obj.getString("service");
                String disp = obj.getString("disp");
                tps = tps + (timestamp);
                tl = tl + (total);
                li = li + (lineok);
                tok = tok + (turbineok);
                rn = rn + (run);
                gn1 = gn1 + (genone);
                gen2 = gen2 + (gentwo);
                amt = amt + (ambient);
                lin = lin + (line);
                yaw = yaw + (yawing);
                ser = ser + (service);
                ds = ds + (disp);
                //TOTAL
                TV_TOTAL_TOL.setText(tl);
                TV_TOTAL_RUN.setText(rn);
                TV_TOTAL_LINK.setText(li);
                TV_TOTAL_GEN1.setText(gn1);
                TV_TOTAL_TURBINO.setText(tok);
                TV_TOTAL_GEN2.setText(gen2);
                TV_TOTAL_AMBIENT.setText(amt);
                TV_TOTAL_LINE.setText(lin);
                TV_TOTAL_YAWING.setText(yaw);
                TV_TOTAL_SERVICE.setText(ser);
                TV_TOTAL_DISP.setText(ds);
            }

        }else
        {
            thread.interrupt();
        }

    }
}
