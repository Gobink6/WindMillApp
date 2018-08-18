package com.robotics.katamaron.windmill;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class Tab_two extends Fragment {
   protected TextView TV_MAIN_ACT_GEN0,TV_MAIN_ACT_GEN1,TV_MAIN_ACT_GEN2,TV_MAIN_ACT_TPROD,TV_MAIN_RECT_GEN0,TV_MAIN_RECT_GEN1,TV_MAIN_RECT_GEN2,TV_MAIN_RECT_TPROD;
   protected TextView TV_SUB_ACT_GEN0,TV_SUB_ACT_GEN1,TV_SUB_ACT_GEN2,TV_SUB_ACT_TPROD,TV_SUB_RECT_GEN0,TV_SUB_RECT_GEN1,TV_SUB_RECT_GEN2,TV_SUB_RECT_TPROD;
   protected TextView TV_TRIP_ACT_GEN0,TV_TRIP_ACT_GEN1,TV_TRIP_ACT_GEN2,TV_TRIP_ACT_TPROD,TV_TRIP_RECT_GEN0,TV_TRIP_RECT_GEN1,TV_TRIP_RECT_GEN2,TV_TRIP_RECT_TPROD;
    public static final String WindName = "WindNameKey";
    public static final String mypreference = "mypref";
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private String windids;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public boolean mStoploop;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Tab_two.OnFragmentInteractionListener mListener;

    public Tab_two() {
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
    public static Tab_two newInstance(String param1, String param2) {
        Tab_two fragment = new Tab_two();
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
      //
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_tab_two, container, false);
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.activity_tab_two, container, false);
       // String getArgument = getArguments().getString("user");
        return view;
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
        //listView = (ListView) view.findViewById(R.id.listView1);
        //Refferance the Variable
       // Bundle bundle = getArguments();
        // String info = bundle.getString("params");

        //main Active
        TV_MAIN_ACT_GEN0 = (TextView) view.findViewById(R.id.pro_act1_id_gen0);
        TV_MAIN_ACT_GEN1 = (TextView) view.findViewById(R.id.pro_act1_id_gen1);
        TV_MAIN_ACT_GEN2 = (TextView) view.findViewById(R.id.pro_act1_id_gen2);
        TV_MAIN_ACT_TPROD = (TextView) view.findViewById(R.id.pro_act1_id_tot);
        //main reactive
        TV_MAIN_RECT_GEN0 = (TextView) view.findViewById(R.id.pro_re1_id_gen0);
        TV_MAIN_RECT_GEN1 = (TextView) view.findViewById(R.id.pro_re1_id_gen1);
        TV_MAIN_RECT_GEN2 = (TextView) view.findViewById(R.id.pro_re1_id_gen2);
        TV_MAIN_RECT_TPROD = (TextView) view.findViewById(R.id.pro_re1_id_tot);
        //sub active
        TV_SUB_ACT_GEN0 = (TextView) view.findViewById(R.id.pro_act2_id_gen0);
        TV_SUB_ACT_GEN1 = (TextView) view.findViewById(R.id.pro_act2_id_gen1);
        TV_SUB_ACT_GEN2 = (TextView) view.findViewById(R.id.pro_act2_id_gen2);
        TV_SUB_ACT_TPROD = (TextView) view.findViewById(R.id.pro_act2_id_tot);
        //sub reactive
        TV_SUB_RECT_GEN0 = (TextView) view.findViewById(R.id.pro_re2_id_gen0);
        TV_SUB_RECT_GEN1 = (TextView) view.findViewById(R.id.pro_re2_id_gen1);
        TV_SUB_RECT_GEN2 = (TextView) view.findViewById(R.id.pro_re2_id_gen2);
        TV_SUB_RECT_TPROD = (TextView) view.findViewById(R.id.pro_re2_id_tot);
        //trip active
        TV_TRIP_ACT_GEN0 = (TextView) view.findViewById(R.id.pro_act3_id_gen0);
        TV_TRIP_ACT_GEN1 = (TextView) view.findViewById(R.id.pro_act3_id_gen1);
        TV_TRIP_ACT_GEN2 = (TextView) view.findViewById(R.id.pro_act3_id_gen2);
        TV_TRIP_ACT_TPROD = (TextView) view.findViewById(R.id.pro_act3_id_tot);
        //trip reactive
        TV_TRIP_RECT_GEN0 = (TextView) view.findViewById(R.id.pro_re3_id_gen0);
        TV_TRIP_RECT_GEN1 = (TextView) view.findViewById(R.id.pro_re3_id_gen1);
        TV_TRIP_RECT_GEN2 = (TextView) view.findViewById(R.id.pro_re3_id_gen2);
        TV_TRIP_RECT_TPROD = (TextView) view.findViewById(R.id.pro_re3_id_tot);

        SharedPreferences sharedpreferences = this.getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        if (sharedpreferences.contains(WindName)) {
            //TV_PITCH.setText(sharedpreferences.getString(WindName, ""));
            windids =  sharedpreferences.getString(WindName, "");
        }
        thread.start();

        //sub active
        TV_SUB_ACT_GEN0.setText("9");
        TV_SUB_ACT_GEN1.setText("0");
        TV_SUB_ACT_GEN2.setText("11");
        TV_SUB_ACT_TPROD.setText("12");
        //sub reactive
        TV_SUB_RECT_GEN0.setText("13");
        TV_SUB_RECT_GEN1.setText("14");
        TV_SUB_RECT_GEN2.setText("15");
        TV_SUB_RECT_TPROD.setText("16");
        //trip active
        TV_TRIP_ACT_GEN0.setText("17");
        TV_TRIP_ACT_GEN1.setText("18");
        TV_TRIP_ACT_GEN2.setText("19");
        TV_TRIP_ACT_TPROD.setText("20");
        //trip reactive
        TV_TRIP_RECT_GEN0.setText("21");
        TV_TRIP_RECT_GEN1.setText("22");
        TV_TRIP_RECT_GEN2.setText("23");
        TV_TRIP_RECT_TPROD.setText("24");

      //  String user = getArguments().getString("user");
        // running();
        // thread.start();
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof Tab_two.OnFragmentInteractionListener) {
//            mListener = (Tab_two.OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

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
                            new AsyncLink().execute(windids);
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
                url = new URL("http://192.168.0.103:3000/getid");

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
                        .appendQueryParameter("windmill_id", params[0]);
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
              //  Toast.makeText( getActivity(), s, Toast.LENGTH_SHORT).show();
                loadIntoListView(s);
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }
    private void loadIntoListView(String json) throws JSONException {
        String gn0 = "",gn1 = "",gn2 ="",tp="",rgn0 = "",rgn1 = "",rgn2 ="",rtp="";
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
                String gen0_react = obj.getString("gen0_react");
                String gen1_react = obj.getString("gen1_react");
                String gen2_react = obj.getString("gen2_react");
                String prod_react = obj.getString("prod_react");
                String gen0_activ = obj.getString("gen0_activ");
                String gen1_activ = obj.getString("gen1_activ");
                String gen2_activ = obj.getString("gen2_activ");
                String prod_activ = obj.getString("prod_activ");
                gn0 = gn0 + (gen0_react);
                gn1 = gn1 + (gen1_react);
                gn2 = gn2 + (gen2_react);
                tp = tp + (prod_react);
                rgn0 = rgn0 + (gen0_activ);
                rgn1 = rgn1 + (gen1_activ);
                rgn2 = rgn2 + (gen2_activ);
                rtp = rtp + (prod_activ);
                //Main active
                TV_MAIN_ACT_GEN0.setText(rgn0);
                TV_MAIN_ACT_GEN1.setText(rgn1);
                TV_MAIN_ACT_GEN2.setText(rgn2);
                TV_MAIN_ACT_TPROD.setText(rtp);
                //Main active
                TV_MAIN_RECT_GEN0.setText(gn0);
                TV_MAIN_RECT_GEN1.setText(gn1);
                TV_MAIN_RECT_GEN2.setText(gn2);
                TV_MAIN_RECT_TPROD.setText(tp);
                }

        }else
        {
            thread.interrupt();
        }

    }
}

