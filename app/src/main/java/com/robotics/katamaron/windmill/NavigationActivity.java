package com.robotics.katamaron.windmill;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

public class NavigationActivity extends AppCompatActivity implements Tab_one.OnFragmentInteractionListener, Tab_two.OnFragmentInteractionListener, Tab_three.OnFragmentInteractionListener, Tab_four.OnFragmentInteractionListener, Tab_5.OnFragmentInteractionListener, Tab_six.OnFragmentInteractionListener, Tab_seven.OnFragmentInteractionListener {
String message,Phone,info,tablelayout;

    private TextView TV_USERNAME_ID,TV_EMAIL_ID;
    SharedPreferences sharedpreferences;
    private static FragmentManager fragmentManager;
    public static final String mypreference = "mypref";
    public static final String UserType = "UserType";
    public static final String Tablelayout = "Trues";
    public static final String UserPhone = "UserPhone";
    boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);


        //

        //Fetch data from sharedpreferences
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        message = sharedpreferences.getString(UserType, "");
        tablelayout = sharedpreferences.getString(Tablelayout, "");
        Phone = sharedpreferences.getString(UserPhone, "");
        //find Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //set toolbar titile
        getSupportActionBar().setTitle("KIoT");

        fragmentManager = getSupportFragmentManager();
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorKatomaran));
        }
        if (sharedpreferences.contains(UserType) ||  sharedpreferences.contains(UserPhone)) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //Get Reference to variable
        Menu nav_Menu = navigationView.getMenu();
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.username_id);
        TextView navUserphone = (TextView) headerView.findViewById(R.id.email_id);
        navUsername.setText(message);
        navUserphone.setText(Phone);
        //navigationView.setNavigationItemSelectedListener(this);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressWarnings("StatementWithEmptyBody")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle navigation view item clicks here.

                int id = item.getItemId();

                if (id == R.id.nav_windmill) {
                    Intent intent = new Intent(NavigationActivity.this, Google_map_Activity.class);
                    startActivity(intent);
                    finish();
                    // Handle the camera action
                } else if (id == R.id.nav_production) {
                        String tables = "exit";
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(Tablelayout, tables);
                        editor.commit();
                        Intent intent = new Intent(NavigationActivity.this,NavigationActivity.class);
                        startActivity(intent);
                        finish();
                }  else if (id == R.id.nav_overviews) {
                    String tabless = "true";
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(Tablelayout, tabless);
                    editor.commit();
                    Intent intent = new Intent(NavigationActivity.this,NavigationActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if (id == R.id.nav_logout) {
                    Intent intent = new Intent(NavigationActivity.this, LoginActivity.class);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.clear();
                    editor.commit();
                    startActivity(intent);
                    finish();
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);

                return true;
            }
        });

        if (tablelayout.equals("true")) {
            // hide Overview button
            nav_Menu.findItem(R.id.nav_overviews).setVisible(false);
            TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);

            tabLayout.addTab(tabLayout.newTab().setText("Overview"));
            tabLayout.addTab(tabLayout.newTab().setText("Production"));
            tabLayout.addTab(tabLayout.newTab().setText("Hour Counter"));
            tabLayout.addTab(tabLayout.newTab().setText("Electrical Data"));
            tabLayout.addTab(tabLayout.newTab().setText("Temperature"));
            tabLayout.addTab(tabLayout.newTab().setText("Log"));
            tabLayout.addTab(tabLayout.newTab().setText("Alarm Log"));
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
            tabLayout.setTabMode(TabLayout.GRAVITY_CENTER);
            tabLayout.setTabMode(TabLayout.FOCUSABLE);
            if (message.equals("Admin") || message.equals("SuperAdmin")) {
                tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
            }
            //tabLayout.removeAllTabs();
            List<Fragment> fragments = new ArrayList<>();
            List<String> strings = new ArrayList<>();
            fragments.add(new Tab_one());
            fragments.add(new Tab_two());
            fragments.add(new Tab_three());
            fragments.add(new Tab_four());
            fragments.add(new Tab_5());
            fragments.add(new Tab_six());
            fragments.add(new Tab_seven());
            Log.i("fragmentSize ", String.valueOf(fragments.size()));
            //Hide Fragments and Tablayout
            if (message.equals("User")) {
                fragments.remove(6);
                fragments.remove(5);
                fragments.remove(4);
                fragments.remove(3);
                tabLayout.removeTabAt(tabLayout.getTabCount() - 4);
                tabLayout.removeTabAt(tabLayout.getTabCount() - 3);
                tabLayout.removeTabAt(tabLayout.getTabCount() - 2);
                tabLayout.removeTabAt(tabLayout.getTabCount() - 1);
            }
            //Hide Fragments and Tablayout
            if (message.equals("Admin")) {
                fragments.remove(3);
                fragments.remove(2);
                fragments.remove(1);
                tabLayout.removeTabAt(tabLayout.getTabCount() - 5);
                tabLayout.removeTabAt(tabLayout.getTabCount() - 4);
                tabLayout.removeTabAt(tabLayout.getTabCount() - 3);
            }

            final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
            PageAdapter adapter = new PageAdapter(getSupportFragmentManager(), fragments, strings);
            viewPager.setAdapter(adapter);

            viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {

                    viewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });


        }else {
            nav_Menu.findItem(R.id.nav_production).setVisible(false);
            TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);

            tabLayout.addTab(tabLayout.newTab().setText("Production"));
            tabLayout.addTab(tabLayout.newTab().setText("Hour Counter"));
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        }
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else
            {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
        }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "press again to close kIoT", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
       // TV_USERNAME_ID.setText(message);
       // TV_EMAIL_ID.setText(Phone);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {


        }

        return super.onOptionsItemSelected(item);
    }


}
