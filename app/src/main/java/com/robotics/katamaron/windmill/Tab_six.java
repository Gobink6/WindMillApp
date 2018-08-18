package com.robotics.katamaron.windmill;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Tab_six.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tab_six#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab_six extends Fragment {
//    protected TextView TV_TIME,TV_TIME1,TV_TIME2,TV_TIME3;
//    protected Button BT_ONE,BT_TWO,BT_THREE,BT_FOUR;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Listitem> listitems;
    public boolean mStoploop;
    public static final String WindName = "WindNameKey";
    public static final String mypreference = "mypref";
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private String windid_log;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Tab_six() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab_six.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab_six newInstance(String param1, String param2) {
        Tab_six fragment = new Tab_six();
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
        return inflater.inflate(R.layout.fragment_tab_6, container, false);

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
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

       /* listitems = new ArrayList<>();

        for (int i =0; i<=10; i++){
            Listitem listitem = new Listitem(
              "Log" + (i+1),
                    "date Time"
            );
            listitems.add(listitem);
        } */

        SharedPreferences sharedpreferences = this.getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        if (sharedpreferences.contains(WindName)) {
            //TV_PITCH.setText(sharedpreferences.getString(WindName, ""));
            windid_log =  sharedpreferences.getString(WindName, "");
        }
        new AsyncLink().execute(windid_log);
      //  thread.start();
   /*     TV_TIME  = (TextView) view.findViewById(R.id.Log_text_1);
        TV_TIME1  = (TextView) view.findViewById(R.id.Log_text_2);
        TV_TIME2  = (TextView) view.findViewById(R.id.Log_text_3);
        TV_TIME3  = (TextView) view.findViewById(R.id.Log_text_4);
        BT_ONE = (Button) view.findViewById(R.id.Log_but_1);
        BT_TWO = (Button) view.findViewById(R.id.Log_but_2);
        BT_THREE = (Button) view.findViewById(R.id.Log_but_3);
        BT_FOUR = (Button) view.findViewById(R.id.Log_but_4);
        TV_TIME.setVisibility(View.GONE);
        TV_TIME1.setVisibility(View.GONE);
        TV_TIME2.setVisibility(View.GONE);
        TV_TIME3.setVisibility(View.GONE);
        BT_ONE.setOnClickListener(new View.OnClickListener()
        {
            Boolean view = true;
            @Override
            public void onClick(View v)
            {
                if (view) {
                    TV_TIME.setVisibility(View.VISIBLE);
                    view = false;
                }else{
                    TV_TIME.setVisibility(View.GONE);
                    view = true;
                }
            }
        });
        BT_TWO.setOnClickListener(new View.OnClickListener()
        {
            Boolean view = true;
            @Override
            public void onClick(View v)
            {
                if (view) {
                    TV_TIME1.setVisibility(View.VISIBLE);
                    view = false;
                }else{
                    TV_TIME1.setVisibility(View.GONE);
                    view = true;
                }
            }
        });
        BT_THREE.setOnClickListener(new View.OnClickListener()
        {
            Boolean view = true;
            @Override
            public void onClick(View v)
            {
                if (view) {
                    TV_TIME2.setVisibility(View.VISIBLE);
                    view = false;
                }else{
                    TV_TIME2.setVisibility(View.GONE);
                    view = true;
                }
            }
        });
        BT_FOUR.setOnClickListener(new View.OnClickListener()
        {
            Boolean view = true;
            @Override
            public void onClick(View v)
            {
                if (view) {
                    TV_TIME3.setVisibility(View.VISIBLE);
                    view = false;
                }else{
                    TV_TIME3.setVisibility(View.GONE);
                    view = true;
                }
            }
        });
*/
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
      //  thread.interrupt();
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
                    Thread.sleep(2000);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                           new AsyncLink().execute(windid_log);
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
                url = new URL("http://192.168.0.103:3000/logdata");

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
                //Toast.makeText( getActivity(), s, Toast.LENGTH_SHORT).show();
            try{

                loadIntoListView(s);
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }
    private void loadIntoListView(String json) throws JSONException {
        String ai = "",gt = "",g2 = "",ncl ="",cnt="",hy = "",gr = "",br ="",wsd="";
        if (json != null) {
            listitems = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);

              Listitem item  = new Listitem(
                      obj.getString("logbook"),
                      obj.getString("created_at")
              );
                listitems.add(item);
            }
            adapter  = new RecyclerAdapter(listitems, getContext());
            recyclerView.setAdapter(adapter);

        }else
        {
            thread.interrupt();
        }

    }
}
