package com.example.sam14240619331555.food_app;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.AlertDialogWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sam14240619331555 on 2016/3/14.
 */
public class SlotAsyncTask extends AsyncTask<URL, Integer, Map> {

    // String for LogCat documentation
    private final static String TAG = "Slot-AsyncTask";
    private Activity mParentActivity;

    int target;
    private static final int GUI_OK = 0x101;  //自訂事件ID

    private static int Resultnum ;
    public ImageView image_detail;
    TextView slot_middle,today_eat;
    public Button slot_datail;

    Map map = new HashMap();


    public SlotAsyncTask(Activity parentActivity) {
        // TODO Auto-generated constructor stub
        super();
        Log.i(TAG, "SlotAsyncTask().");
        mParentActivity = parentActivity;

    }

    @Override
    protected Map doInBackground(URL... params) {
        // TODO Auto-generated method stub

        new Thread(new Runnable(){
            @Override
            public void run() {
                // TODO Auto-generated method stub
                while(Resultnum<=10){
                    try{
                        Message msg = new Message();
                        msg.what = GUI_OK;
                        mHandler.sendMessage(msg);
                        Thread.sleep(500);
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();



      return map;
    }

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        Log.i(TAG, "onPreExecute().");
        Resultnum=0;
        target =(int)(Math.random()* SplashScreenAsyncTask.rest_name.size() );
        slot_middle = (TextView)mParentActivity.findViewById(R.id.slot_name);
        today_eat = (TextView)mParentActivity.findViewById(R.id.today_eat);
        image_detail = (ImageView)mParentActivity.findViewById(R.id.image_detail);
        slot_datail =(Button) mParentActivity.findViewById(R.id.slot_detail);

        today_eat.setVisibility(View.GONE);
        image_detail.setVisibility(View.GONE);
        slot_datail.setVisibility(View.GONE);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        // TODO Auto-generated method stub
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Map map) {
        // TODO Auto-generated method stub
        super.onPostExecute(map);
        Log.i(TAG, "onPostExecute().");




    }
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case GUI_OK:
                    if(Resultnum==10){
                        today_eat.setVisibility(View.VISIBLE);
                        slot_middle.setText(SplashScreenAsyncTask.rest_name.get(target));
                        today_eat.setText("今天吃" + SplashScreenAsyncTask.rest_name.get(target) + "!");
                        image_detail.setVisibility(View.VISIBLE);
                        slot_datail.setVisibility(View.VISIBLE);
                        break;
                    }else{
                        slot_middle.setText(SplashScreenAsyncTask.rest_name.get(Resultnum));
                        Resultnum++;
                        break;
                    }

            }
            super.handleMessage(msg);
        }
    };

}

