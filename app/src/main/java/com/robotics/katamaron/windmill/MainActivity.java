package com.robotics.katamaron.windmill;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import static android.support.animation.SpringForce.DAMPING_RATIO_LOW_BOUNCY;

public class MainActivity extends Activity {
private TextView TV_ONE, TV_TWO;
private ImageView IM_SPLASH;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String UserType = "UserType";
    public static final String UserPhone = "UserPhone";
    public static final String Tokens = "Token";
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String token = FirebaseInstanceId.getInstance().getToken();
        //Log.d("Token",  token);
        FirebaseMessaging.getInstance().subscribeToTopic("USER");
       // FirebaseMessaging.getInstance().subscribeToTopic(token);
      //  TV_ONE = findViewById(R.id.tv_splash);
       // IM_SPLASH = findViewById(R.id.iv_splash);
        Animation anim = AnimationUtils.loadAnimation(this,R.anim.splash_transition);
        Animation uptodown = AnimationUtils.loadAnimation(this,R.anim.uptodowm);
        Animation jumptext = AnimationUtils.loadAnimation(this,R.anim.jumptext);
     //   TV_ONE.startAnimation(anim);
       // IM_SPLASH.startAnimation(anim);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
              /*  if (sharedpreferences != null) {
                    Intent i = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(i);
                    // close this activity
                    finish();
                }else { */
                if (sharedpreferences.contains(UserType) ||  sharedpreferences.contains(UserPhone)) {
                    Intent i = new Intent(MainActivity.this, Google_map_Activity.class);
                    startActivity(i);
                    finish();
                }else {
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(Tokens, token  );
                    editor.commit();
                    startActivity(i);
                    // close this activity
                    finish();
                }

            }
        }, SPLASH_TIME_OUT);
        // Api level  above or equal to 21 notification bar color change
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorKatomaran));
        }else{

        }
    }
/*
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        View decorView = getWindow().getDecorView();
        if (hasFocus) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    } */
    }

