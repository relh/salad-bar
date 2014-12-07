package com.saladbar.houseoftoss;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;


public class SplashActivity extends Activity {

    private static int TIME = 2000;
    private static String TAG = "Salad-Bar";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Initializing", "1");
        setContentView(R.layout.splash);
        Log.d("Initializing", "2");
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        Log.d("Initializing", "3");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Log.i(TAG,
                        "Entered splash page");
                Intent intent = new Intent(SplashActivity.this, OrderActivity.class);
                startActivity(intent);
                finish();
            }
        }, TIME);
    }
}
