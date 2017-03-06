package com.example.sam14240619331555.food_app;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

import com.afollestad.materialdialogs.AlertDialogWrapper;

import java.net.URL;

/**
 * Created by sam14240619331555 on 2016/1/28.
 */
public class SplashScreenActivity extends Activity {

    // String for LogCat documentation
    private final static String TAG = "SplashScreen-SplashScreenActivity";
    private ProgressBar spinbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        spinbar = (ProgressBar) findViewById(R.id.progressBar1);
        spinbar.setVisibility(View.GONE);
        Log.v("splah","start");
        new SplashScreenAsyncTask(this, spinbar).execute();

    }


}
