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
 * {@link Tab_5.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tab_5#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab_5 extends Fragment {
    protected TextView TV_PRE_AMBI,TV_PRE_CONTROL,TV_PRE_GEN1,TV_PRE_HYDR,TV_PRE_GEN2,TV_PRE_GEAR,TV_PRE_NACEL,TV_PRE_BEAR;
    protected TextView TV_MAX_AMBI,TV_MAX_CONTROL,TV_MAX_GEN1,TV_MAX_HYDR,TV_MAX_GEN2,TV_MAX_GEAR,TV_MAX_NACEL,TV_MAX_BEAR;
    protected TextView TV_MIN_AMBI,TV_MIN_CONTROL,TV_MIN_GEN1,TV_MIN_HYDR,TV_MIN_GEN2,TV_MIN_GEAR,TV_MIN_NACEL,TV_MIN_BEAR;
    public boolean mStoploop;
    public static final String WindName = "WindNameKey";
    public static final String mypreference = "mypref";
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private String windid_tm;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Tab_5() {
        // Required empty public constructor




    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab_5.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab_5 newInstance(String param1, String param2) {
        Tab_5 fragment = new Tab_5();
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
        return inflater.inflate(R.layout.fragment_tab_5, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Reffernce of variable
        //present
        TV_PRE_AMBI  = (TextView) view.findViewById(R.id.tem_pre_id_amb);
        TV_PRE_CONTROL  = (TextView) view.findViewById(R.id.tem_pre_id_con);
        TV_PRE_GEN1  = (TextView) view.findViewById(R.id.tem_pre_id_gen1);
        TV_PRE_HYDR  = (TextView) view.findViewById(R.id.tem_pre_id_hyd);
        TV_PRE_GEN2  = (TextView) view.findViewById(R.id.tem_pre_id_gen2);
        TV_PRE_GEAR  = (TextView) view.findViewById(R.id.tem_pre_id_gear);
        TV_PRE_NACEL  = (TextView) view.findViewById(R.id.tem_pre_id_nac);
        TV_PRE_BEAR  = (TextView) view.findViewById(R.id.tem_pre_id_bear);
        //max
        TV_MAX_AMBI  = (TextView) view.findViewById(R.id.tem_max_id_amb);
        TV_MAX_CONTROL  = (TextView) view.findViewById(R.id.tem_max_id_con);
        TV_MAX_GEN1  = (TextView) view.findViewById(R.id.tem_max_id_gen1);
        TV_MAX_HYDR  = (TextView) view.findViewById(R.id.tem_max_id_hyd);
        TV_MAX_GEN2  = (TextView) view.findViewById(R.id.tem_max_id_gen2);
        TV_MAX_GEAR  = (TextView) view.findViewById(R.id.tem_max_id_gear);
        TV_MAX_NACEL  = (TextView) view.findViewById(R.id.tem_max_id_nac);
        TV_MAX_BEAR  = (TextView) view.findViewById(R.id.tem_max_id_bear);
        //min
        TV_MIN_AMBI  = (TextView) view.findViewById(R.id.tem_min_id_amb);
        TV_MIN_CONTROL  = (TextView) view.findViewById(R.id.tem_min_id_con);
        TV_MIN_GEN1  = (TextView) view.findViewById(R.id.tem_min_id_gen1);
        TV_MIN_HYDR  = (TextView) view.findViewById(R.id.tem_min_id_hyd);
        TV_MIN_GEN2  = (TextView) view.findViewById(R.id.tem_min_id_gen2);
        TV_MIN_GEAR  = (TextView) view.findViewById(R.id.tem_min_id_gear);
        TV_MIN_NACEL  = (TextView) view.findViewById(R.id.tem_min_id_nac);
        TV_MIN_BEAR  = (TextView) view.findViewById(R.id.tem_min_id_bear);

        SharedPreferences sharedpreferences = this.getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        if (sharedpreferences.contains(WindName)) {
            //TV_PITCH.setText(sharedpreferences.getString(WindName, ""));
            windid_tm =  sharedpreferences.getString(WindName, "");
        }
        thread.start();

        //max
        TV_MAX_AMBI.setText("1");
        TV_MAX_CONTROL.setText("2");
        TV_MAX_GEN1.setText("3");
        TV_MAX_HYDR.setText("4");
        TV_MAX_GEN2.setText("5");
        TV_MAX_GEAR.setText("6");
        TV_MAX_NACEL.setText("7");
        TV_MAX_BEAR.setText("8");
        //min
        TV_MIN_AMBI.setText("1");
        TV_MIN_CONTROL.setText("2");
        TV_MIN_GEN1.setText("3");
        TV_MIN_HYDR.setText("4");
        TV_MIN_GEN2.setText("5");
        TV_MIN_GEAR.setText("6");
        TV_MIN_NACEL.setText("7");
        TV_MIN_BEAR.setText("8");
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
                    Thread.sleep(3000);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AsyncLink().execute(windid_tm);
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
                url = new URL("http://192.168.0.103:3000/streamdata");

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
        //    Toast.makeText( getActivity(), s, Toast.LENGTH_SHORT).show();
            try{
                //
                loadIntoListView(s);
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }
    private void loadIntoListView(String json) throws JSONException {
        String ai = "",gt = "",g2 = "",ncl ="",cnt="",hy = "",gr = "",br ="",wsd="";
        if (json != null) {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String ambi = obj.getString("ambi");
                String genonetemp = obj.getString("genonetemp");
                String gentwo = obj.getString("gentwo");
                String nacel = obj.getString("nacel");
                String cntrl = obj.getString("cntrl");
                String hydr = obj.getString("hydr");
                String gear = obj.getString("gear");
                String bear = obj.getString("bear");
                String windspeed = obj.getString("windspeed");

                ai = ai + (ambi);
                gt = gt + (genonetemp);
                g2 = g2 + (gentwo);
                ncl = ncl + (nacel);
                cnt = cnt + (cntrl);
                hy = hy + (hydr);
                gr = gr + (gear);
                br = br + (bear);
                wsd = wsd + (windspeed);
                //settext present
                TV_PRE_AMBI.setText(ai);
                TV_PRE_CONTROL.setText(cnt);
                TV_PRE_GEN1.setText(gt);
                TV_PRE_HYDR.setText(hy);
                TV_PRE_GEN2.setText(g2);
                TV_PRE_GEAR.setText(gr);
                TV_PRE_NACEL.setText(ncl);
                TV_PRE_BEAR.setText(br);

            }

        }else
        {
            thread.interrupt();
        }

    }
}
